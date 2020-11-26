package `in`.co.jnana.ui.profile

import `in`.co.jnana.R
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController

class ProfileFragment : Fragment() {

    private lateinit var homeViewModel: ProfileViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
            ViewModelProvider(this).get(ProfileViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        val textView: TextView = root.findViewById(R.id.text_home)
        homeViewModel.text.observe(viewLifecycleOwner, {
            textView.text = it
        })
        return root
    }

    override fun onStart() {
        super.onStart()
        Toast.makeText(this.context, R.string.toast_name, Toast.LENGTH_SHORT).show()
        val sharedPrefs = requireActivity().getPreferences(
            Context.MODE_PRIVATE
        )
        val defaultValue: Int = 0
        val userAuth = sharedPrefs.getInt(getString(R.string.user_auth_string), defaultValue)

        if (userAuth != 12){
//            Toast.makeText(this.context, R.string.user_not_authenticated, Toast.LENGTH_LONG).show()
//            Redirect user to login fragment.. to login
            this.findNavController().navigate(R.id.action_navigation_profile_to_userAuth)
        }

    }
}