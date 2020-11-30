package `in`.co.jnana.ui.auth

import `in`.co.jnana.R
import `in`.co.jnana.database.user.StudentDatabase
import `in`.co.jnana.databinding.UserAuthFragmentBinding
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.InternalCoroutinesApi

class UserAuthFragment : Fragment() {

    private lateinit var binding: UserAuthFragmentBinding
    private lateinit var viewModel: UserAuthViewModel

    @InternalCoroutinesApi
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = DataBindingUtil.inflate(inflater, R.layout.user_auth_fragment, container, false)

        val application = requireActivity().application
        val dataSource = StudentDatabase.getInstance(application).studentDatabaseDAO

        val viewModelFactory = UserAuthViewModelFactory(dataSource, application)
        viewModel = ViewModelProvider(this, viewModelFactory).get(UserAuthViewModel::class.java)

        binding.lifecycleOwner = viewLifecycleOwner
        binding.loginVM = viewModel


//        /**
//         *  Observer which shows error in password textview
//         *  based on the value of passwordErrorShow variable
//         * */
//        viewModel.passwordErrorShow.observe(viewLifecycleOwner, {
//            if (it) {
//                binding.passwordLayout.error = "Password not matching..please try again!"
//            }
//        })

//        binding.passwordTextView.setOnFocusChangeListener { _: View, _: Boolean ->
//            Log.i(getString(R.string.logKey), "password textview is clicked..!!")
//            viewModel.donePasswordErrorShow()
//            binding.passwordLayout.error = null
//        }

//        /**
//         *  Observer which shows error in username textview
//         *  based on the value of usernameErrorShow variable
//         * */
//        viewModel.usernameErrorShow.observe(viewLifecycleOwner, {
//            if (it) {
//                binding.usernameLayout.error = "User not found..please check credentials!"
//            }
//        })

//        binding.userNameTextView.setOnClickListener {
//            Log.i(getString(R.string.logKey), "Username textview is clicked..!!")
//            viewModel.doneUsernameErrorShow()
//            binding.usernameLayout.error = null
//        }

        /**
         *  Observer which navigates to profile fragment upon user authentication
         *  based on the value of navigateToProfileFragment variable
         * */
        viewModel.navigateToProfileFragment.observe(viewLifecycleOwner, {
            if (it) {
                Log.i(">>>>Log", "Calling from fragment, about to navigate..")
                viewModel.sAuth.value?.let { s1 ->
                    findNavController().navigate(
                        UserAuthFragmentDirections.actionUserAuthToNavigationProfile(
                            s1.userName, 1
                        )
                    )
                    Log.i(">>>>Log", "Calling from fragment, started to navigate")
                }
            }
//            viewModel.doneNavigationToProfileFragment()
        })

        viewModel.runSetupDatabase()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val afterTextChanged = object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun afterTextChanged(p0: Editable?) {
                viewModel.setValues(
                    binding.userNameTextView.text.toString(),
                    binding.passwordTextView.text.toString()
                )
            }
        }
        binding.userNameTextView.addTextChangedListener(afterTextChanged)
        binding.passwordTextView.addTextChangedListener(afterTextChanged)

        viewModel.navigateToSignUpFragment.observe(viewLifecycleOwner,{
            if (it){
                findNavController().navigate(R.id.action_userAuth_to_signupFragment)
            }
        })
    }
}