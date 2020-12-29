package `in`.co.jnana.ui.auth

import `in`.co.jnana.MainActivity
import `in`.co.jnana.R
import `in`.co.jnana.database.JnanaDatabase
import `in`.co.jnana.databinding.UserAuthFragmentBinding
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
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
        val dataSource = JnanaDatabase.getInstance(application).studentDatabaseDAO

        val viewModelFactory = UserAuthViewModelFactory(dataSource, application)
        viewModel = ViewModelProvider(this, viewModelFactory).get(UserAuthViewModel::class.java)

        binding.lifecycleOwner = viewLifecycleOwner
        binding.loginVM = viewModel

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

        val actionBar = this.activity?.actionBar
        actionBar?.setDisplayShowHomeEnabled(false)
        actionBar?.setHomeButtonEnabled(false)
        actionBar?.setDisplayHomeAsUpEnabled(false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.userNameTextView.doAfterTextChanged {
            viewModel.setValues(
                binding.userNameTextView.text.toString(),
                binding.passwordTextView.text.toString()
            )
        }
        binding.passwordTextView.doAfterTextChanged {
            viewModel.setValues(
                binding.userNameTextView.text.toString(),
                binding.passwordTextView.text.toString()
            )
        }

        viewModel.navigateToSignUpFragment.observe(viewLifecycleOwner, {
            if (it) {
                findNavController().navigate(R.id.action_userAuth_to_signupFragment)
            }
        })
    }

    override fun onResume() {
        super.onResume()
        val activity: MainActivity = requireActivity() as MainActivity
        activity.supportActionBar?.setDisplayHomeAsUpEnabled(false)
    }
}