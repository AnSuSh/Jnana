package `in`.co.jnana.ui.auth

import `in`.co.jnana.R
import `in`.co.jnana.database.user.JnanaDatabase
import `in`.co.jnana.databinding.SignupFragmentBinding
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.InternalCoroutinesApi

class SignupFragment : Fragment() {

    private lateinit var viewModel: SignupViewModel
    private lateinit var binding: SignupFragmentBinding
    private lateinit var uiElements: List<EditText>
    private lateinit var genderString: String

    @InternalCoroutinesApi
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = DataBindingUtil.inflate(inflater, R.layout.signup_fragment, container, false)

        val application = requireActivity().application
        val database = JnanaDatabase.getInstance(application).studentDatabaseDAO
        val viewModelFactory = SignupViewmodelFactory(database, application)

        viewModel = ViewModelProvider(this, viewModelFactory).get(SignupViewModel::class.java)
        binding.signupVM = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        uiElements = listOf(
            binding.editNameOfUser,
            binding.editUserName,
            binding.editPasswordOnce,
            binding.editPasswordTwice,
            binding.editEmail,
            binding.editMobile
        )

        return binding.root
    }

    @InternalCoroutinesApi
    private fun doSignup() {
        if (checkForEmpty()) {
            setGender()
            viewModel.setValuesOfEdits(  // Setting viewmodel variable first..
                binding.editNameOfUser.text.toString(),
                binding.editUserName.text.toString(),
                binding.editPasswordOnce.text.toString(),
                binding.editPasswordTwice.text.toString(),
                genderString,
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
        } else
            Snackbar.make(binding.root, "Please fill out all the fields..!!", Snackbar.LENGTH_LONG)
                .setAction("Okay") {
                    binding.editUserName.requestFocus()
                }
                .show()
    }

    private fun setGender() {
        genderString = when (binding.radioGroupGender.checkedRadioButtonId) {
            R.id.Male -> "Male"
            R.id.Female -> "Female"
            R.id.Others -> "Others"
            else -> null.toString()
        }
    }

    private fun checkForEmpty(): Boolean {
        var retRes = true
        for (l in uiElements) {
            if (TextUtils.isEmpty(l.text)) {
                l.error = "This field is required..!!"
                retRes = false
            }
        }
        return retRes
    }

    @InternalCoroutinesApi
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonSignup.setOnClickListener { doSignup() }

        viewModel.successSignup.observe(viewLifecycleOwner, {
            if (it) {
                Toast.makeText(this.context, "Signup Successful!!", Toast.LENGTH_SHORT).show()
                Handler(Looper.getMainLooper()).postDelayed({
                    findNavController().navigate(R.id.action_signupFragment_to_userAuth)
                }, 4L)
            }
        })
    }
}