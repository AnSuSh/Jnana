package `in`.co.jnana.ui.auth

import `in`.co.jnana.R
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider

class UserAuth : Fragment() {

    companion object {
        fun newInstance() = UserAuth()
    }
    
    private lateinit var viewModel: UserAuthViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.user_auth_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(UserAuthViewModel::class.java)
        // TODO: Use the ViewModel
    }

}