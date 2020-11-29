package `in`.co.jnana.ui.auth

import `in`.co.jnana.R
import `in`.co.jnana.database.user.StudentDatabase
import `in`.co.jnana.databinding.UserAuthFragmentBinding
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

class UserAuthFragment : Fragment() {

    private lateinit var binding: UserAuthFragmentBinding

    @InternalCoroutinesApi
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val application = requireActivity().application
        val dataSource = StudentDatabase.getInstance(application).studentDatabaseDAO

        val viewModelFactory = UserAuthViewModelFactory(dataSource, application)
        val viewModel = ViewModelProvider(this, viewModelFactory).get(UserAuthViewModel::class.java)

        binding = DataBindingUtil.inflate(inflater, R.layout.user_auth_fragment, container, false)

        binding.loginVM = viewModel

        /**
         *  Observer which shows error in password textview
         *  based on the value of passwordErrorShow variable
         * */
        viewModel.passwordErrorShow.observe(viewLifecycleOwner, {
            if (it) {
                binding.passwordLayout.error = "Password not matching..please try again!"
            }
        })

        binding.passwordTextView.setOnClickListener {
            Log.i(getString(R.string.logKey), "password textview is clicked..!!")
            viewModel.donePasswordErrorShow()
            binding.passwordLayout.error = null
        }

        /**
         *  Observer which shows error in username textview
         *  based on the value of usernameErrorShow variable
         * */
        viewModel.usernameErrorShow.observe(viewLifecycleOwner, {
            if (it) {
                binding.usernameLayout.error = "User not found..please check credentials!"
            }
        })

        binding.userNameTextView.setOnClickListener {
            Log.i(getString(R.string.logKey), "Username textview is clicked..!!")
            viewModel.doneUsernameErrorShow()
            binding.usernameLayout.error = null
        }

        /**
         *  Observer which navigates to profile fragment upon user authentication
         *  based on the value of navigateToProfileFragment variable
         * */
        viewModel.navigateToProfileFragment.observe(viewLifecycleOwner, {
            if (it) {
                findNavController()
                    .navigate(UserAuthFragmentDirections.actionUserAuthToNavigationProfile(viewModel.sAuth.userName))
            }
            viewModel.doneNavigationToProfileFragment()
        })

        viewModel.runSetupDatabase()

        return binding.root
    }
}