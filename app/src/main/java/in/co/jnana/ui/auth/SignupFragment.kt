package `in`.co.jnana.ui.auth

import `in`.co.jnana.R
import `in`.co.jnana.databinding.SignupFragmentBinding
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SignupFragment : Fragment() {

    private lateinit var viewModel: SignupViewModel
    private lateinit var binding: SignupFragmentBinding
    private val uiScopeOffload = CoroutineScope(Dispatchers.Main)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = DataBindingUtil.inflate(inflater, R.layout.signup_fragment, container, false)

        viewModel = ViewModelProvider(this).get(SignupViewModel::class.java)
        binding.signupVM = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        uiScopeOffload.launch {
            val textWatcher = object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    //
                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    //
                }

                override fun afterTextChanged(p0: Editable?) {
                    viewModel.setValuesOfEdits(
                        binding.editNameOfUser.toString(),
                        binding.editUserName.toString(),
                        binding.editPasswordOnce.toString(),
                        binding.editPasswordTwice.toString(),
                        binding.radioGroupGender.checkedRadioButtonId.toString(),
                        binding.editEmail.toString(),
                        binding.editMobile.toString().toLong()
                    )
                }
            }

            with(binding) {
                editNameOfUser.addTextChangedListener(textWatcher)
                editUserName.addTextChangedListener(textWatcher)
                editPasswordOnce.addTextChangedListener(textWatcher)
                editPasswordTwice.addTextChangedListener(textWatcher)
                editEmail.addTextChangedListener(textWatcher)
                editMobile.addTextChangedListener(textWatcher)
            }
        }
    }
}