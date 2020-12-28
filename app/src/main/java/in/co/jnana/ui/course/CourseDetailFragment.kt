package `in`.co.jnana.ui.course

import `in`.co.jnana.R
import `in`.co.jnana.database.JnanaDatabase
import `in`.co.jnana.databinding.CourseDetailFragmentBinding
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.InternalCoroutinesApi

class CourseDetailFragment : Fragment() {

    private lateinit var courseDetailViewModel: CourseDetailViewModel
    private lateinit var courseDataBinding: CourseDetailFragmentBinding
    private lateinit var userAuthName: String
    private lateinit var args: CourseDetailFragmentArgs

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val prefs = requireActivity().getPreferences(Context.MODE_PRIVATE)
        userAuthName = prefs.getString("user_name", null).toString()

        courseDataBinding =
            DataBindingUtil.inflate(inflater, R.layout.course_detail_fragment, container, false)
        courseDataBinding.lifecycleOwner = viewLifecycleOwner

        return courseDataBinding.root
    }

    @InternalCoroutinesApi
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        args = CourseDetailFragmentArgs.fromBundle(requireArguments())
        val application = requireActivity().application
        val dataSource = JnanaDatabase.getInstance(application).courseDatabaseDAO
        val sDataSource = JnanaDatabase.getInstance(application).courseStudentDAO
        val studentDAO = JnanaDatabase.getInstance(application).studentDatabaseDAO

        val courseDetailViewModelFactory = CourseDetailViewModelFactory(
            args.courseID,
            dataSource,
            studentDAO,
            sDataSource
        )

        val actionBar = this.activity?.actionBar
        actionBar?.setDisplayShowHomeEnabled(false)
        actionBar?.setHomeButtonEnabled(false)
        actionBar?.setDisplayHomeAsUpEnabled(false)

        courseDetailViewModel = ViewModelProvider(
            this,
            courseDetailViewModelFactory
        ).get(CourseDetailViewModel::class.java)
        courseDetailViewModel.getCourseData()

        courseDataBinding.buttonBuy.setOnClickListener {
            if (userAuthName != "null") { // User is signed in
                courseDetailViewModel.getUserIDByUsername(userAuthName)
                courseDetailViewModel.studentID.observe(viewLifecycleOwner, { l ->
                    if (l > 0) {
                        courseDetailViewModel.buyCourse(args.courseID, l)
                        it.isEnabled = false
                        Log.d("Log....", "${args.courseID}, $it")
                    }
                })
            } else { // Redirect to signin fragment
                findNavController().navigate(R.id.action_courseDetail_to_userAuth)
            }
        }

        courseDataBinding.previewYoutube.setOnClickListener {
            courseDetailViewModel.courseOpted.value?.url?.let { url -> launchYoutubeWithURL(url) }
        }

        when (args.isFromProfile) {
            true -> {
                courseDataBinding.buttonBuy.visibility = View.GONE
                courseDataBinding.previewYoutube.visibility = View.VISIBLE
            }
            false -> {
                courseDataBinding.buttonBuy.visibility = View.VISIBLE
                courseDataBinding.previewYoutube.visibility = View.GONE
            }
        }

        courseDataBinding.courseDetailVM = courseDetailViewModel
        courseDataBinding.lifecycleOwner = viewLifecycleOwner
        courseDataBinding.executePendingBindings()
    }

    private fun launchYoutubeWithURL(url: String) {
        val youtubeIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        this.requireActivity().startActivity(youtubeIntent)
    }
}