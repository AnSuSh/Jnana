package `in`.co.jnana.ui.profile

import `in`.co.jnana.R
import `in`.co.jnana.database.JnanaDatabase
import `in`.co.jnana.databinding.FragmentHomeBinding
import `in`.co.jnana.ui.course.CourseClickListener
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.*

class ProfileFragment : Fragment() {

    private lateinit var profileViewModel: ProfileViewModel
    private lateinit var arg: ProfileFragmentArgs
    private lateinit var binding: FragmentHomeBinding
    private lateinit var adapter: EnrolledCourseAdapter
    private lateinit var preferences: SharedPreferences

    @InternalCoroutinesApi
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        arg = ProfileFragmentArgs.fromBundle(requireArguments())

        val application = requireActivity().application
        val dataSource = JnanaDatabase.getInstance(application).courseStudentDAO

        val profileViewModelFactory = ProfileViewModelFactory(dataSource)
        profileViewModel =
            ViewModelProvider(this, profileViewModelFactory).get(ProfileViewModel::class.java)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)

        profileViewModel.text.observe(viewLifecycleOwner, {
            binding.textHome.text = it
        })

        setHasOptionsMenu(true)

        val actionBar = this.activity?.actionBar
        actionBar?.setDisplayShowHomeEnabled(false)
        actionBar?.setHomeButtonEnabled(false)
        actionBar?.setDisplayHomeAsUpEnabled(false)

        preferences = requireActivity().getPreferences(Context.MODE_PRIVATE)

        binding.profileVM = profileViewModel
        binding.lifecycleOwner = viewLifecycleOwner

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.profile_page_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onStart() {
        super.onStart()

//        CoroutineScope(Dispatchers.Main).launch {
//            withContext(Dispatchers.Main) {
        if (checkLoginState()) {
            adapter = EnrolledCourseAdapter(CourseClickListener {
                profileViewModel.onCourseItemClicked(it)
            })

            profileViewModel.loadDataSet()
            binding.enrolledCourseList.adapter = adapter
            Log.d("Log....", "Comlpeted recyclerView Work")
            setListeners()
        }
//    }
//}
    }

    private fun checkLoginState(): Boolean {
        val defaultValue = 0
        val userAuthKey = preferences.getInt("user_key", defaultValue)
        if (userAuthKey == 0) {  // No user data exists
            if (arg.loginState == 0) {
                findNavController().navigate(R.id.action_navigation_profile_to_userAuth)
                return false
            } else {
                val prefers = requireActivity().getPreferences(Context.MODE_PRIVATE)
                with(prefers.edit()) {
                    putInt("user_key", 1)
                    putString("user_name", arg.username)
                    apply()
                }
                alright(prefers)
            }
        } else {
            alright(preferences)
        }
        return true
    }

    private fun setListeners() {
        profileViewModel.navigateToCourseDetail.observe(viewLifecycleOwner, {
            it?.let {
                findNavController().navigate(
                    ProfileFragmentDirections.actionNavigationProfileToCourseDetail(it, true)
                )
                profileViewModel.onCourseDetailNavigated()
            }
        })
        profileViewModel.dataset.observe(viewLifecycleOwner, {
            it?.let {
                // For the listAdapter
                val newList = it.filter { entity ->
                    entity.student.userName == preferences.getString("user_name", "")
                }
                val listCourses = newList[0].courses
                if (listCourses.isNotEmpty()){
                    binding.enrolledCourseList.visibility = View.VISIBLE
                    binding.emptyTV.visibility = View.GONE
                    adapter.submitList(listCourses)
                }else{
                    binding.emptyTV.visibility = View.VISIBLE
                }
            }
        })
        Log.d("Log....", "Completed listening work")
    }

    private fun alright(preferences: SharedPreferences) {
//        if (profileViewModel.toastShowCount > 0) {
//            Toast.makeText(
//                this.activity,
//                "User Authentication successful..!!",
//                Toast.LENGTH_SHORT
//            ).show()
//            profileViewModel.toastShowCount = 0
//        }
        preferences.getString("user_name", "")?.let { profileViewModel.greetUser(it) }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.logout) {
            with(preferences.edit()) {
                putInt("user_key", 0)
                putString("user_name", null)
                apply()
            }
            findNavController().navigate(R.id.action_navigation_profile_to_userAuth)
        }
        return true
    }

    override fun onResume() {
        super.onResume()
        if (arg.loginState != 0) {
            CoroutineScope(Dispatchers.Main).launch {
                withContext(Dispatchers.IO) {
                    profileViewModel.loadDataSet()
                }
            }
        }
    }
}