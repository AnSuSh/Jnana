package `in`.co.jnana.ui.auth

import `in`.co.jnana.R
import `in`.co.jnana.database.user.StudentDatabase
import `in`.co.jnana.databinding.SignupFragmentBinding
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.InternalCoroutinesApi

class SignupFragment : Fragment() {

    private lateinit var viewModel: SignupViewModel
    private lateinit var binding: SignupFragmentBinding

    @InternalCoroutinesApi
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = DataBindingUtil.inflate(inflater, R.layout.signup_fragment, container, false)

        val application = requireActivity().application
        val database = StudentDatabase.getInstance(application).studentDatabaseDAO
        val viewModelFactory = SignupViewmodelFactory(database, application)

        viewModel = ViewModelProvider(this, viewModelFactory).get(SignupViewModel::class.java)
        binding.signupVM = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        binding.buttonSignup.setOnClickListener { doSignup() }

        viewModel.successSignup.observe(viewLifecycleOwner, {
            if (it) {
                Toast.makeText(this.context, "Signup Successful!!", Toast.LENGTH_SHORT).show()
                Handler(Looper.getMainLooper()).postDelayed({
                    findNavController().navigate(R.id.action_signupFragment_to_userAuth)
                }, 4L)
            }
        })

        return binding.root
    }

    @InternalCoroutinesApi
    private fun doSignup() {
        viewModel.setValuesOfEdits(  // Setting viewmodel variable first..
            binding.editNameOfUser.text.toString(),
            binding.editUserName.text.toString(),
            binding.editPasswordOnce.text.toString(),
            binding.editPasswordTwice.text.toString(),
            binding.radioGroupGender.checkedRadioButtonId.toString(),
            binding.editEmail.text.toString(),
            binding.editMobile.text.toString().toLong()
        )
        if (viewModel.checkUsername()) {
            if (viewModel.setPassword())
                viewModel.insertDataToDatabase()
            else {
                binding.editPasswordTwice.error = "Password not matching..!!"
                binding.editPasswordOnce.error = "Password not matching..!!"
            }
        } else
            binding.editUserName.error = "Username already exists..!!"
    }

//    override fun afterTextChanged(p0: Editable?) {
//        viewModel.setValuesOfEdits(
//            binding.editNameOfUser.text.toString(),
//            binding.editUserName.text.toString(),
//            binding.editPasswordOnce.text.toString(),
//            binding.editPasswordTwice.text.toString(),
//            binding.radioGroupGender.checkedRadioButtonId.toString(),
//            binding.editEmail.text.toString(),
//            0
//        )
//        if (binding.editMobile.text.toString() != "")
//            viewModel.setValuesOfEdits(
//                mobile = binding.editMobile.text.toString().toLong()
//            )
}