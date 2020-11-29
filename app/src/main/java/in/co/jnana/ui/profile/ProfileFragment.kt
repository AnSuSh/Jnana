package `in`.co.jnana.ui.profile

import `in`.co.jnana.R
import android.os.Bundle
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController

class ProfileFragment : Fragment() {

    private lateinit var profileViewModel: ProfileViewModel
    private lateinit var arg: ProfileFragmentArgs

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        arg = ProfileFragmentArgs.fromBundle(requireArguments())

        profileViewModel = ViewModelProvider(this).get(ProfileViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)

        val textView: TextView = root.findViewById(R.id.text_home)

        profileViewModel.text.observe(viewLifecycleOwner,{
            textView.text = it
        })

        return root
    }

    override fun onStart() {
        super.onStart()
        if (arg.loginState == 0) {
            Toast.makeText(
                this.activity,
                "this is new, No arguments here, passing to Login..",
                Toast.LENGTH_SHORT
            ).show()
            android.os.Handler(Looper.getMainLooper()).postDelayed({
                findNavController().navigate(R.id.action_navigation_profile_to_userAuth)
            }, 2000L)
        }else{
            Toast.makeText(
                this.activity,
                "User Authentication successful..!!",
                Toast.LENGTH_SHORT
            ).show()
            profileViewModel.greetUser(arg.username)
        }
    }

//    override fun onStart() {
//        super.onStart()
//        Log.i(getString(R.string.logKey), "Profile fragment is started..!!")
//
//        if (bundledData == 90) {
//            Toast.makeText(
//                this.activity,
//                "this is new, No arguments here, passing to Login..",
//                Toast.LENGTH_SHORT
//            ).show()
//            android.os.Handler(Looper.getMainLooper()).postDelayed({
//                findNavController().navigate(R.id.action_navigation_profile_to_userAuth)
//            }, 2000L)
//        }
//
//        /*
//        * Share Preference part..
//        * */
//        Toast.makeText(this.context, R.string.toast_name, Toast.LENGTH_SHORT).show()
//        val sharedPrefs = requireActivity().getPreferences(
//            Context.MODE_PRIVATE
//        )
//        val defaultValue: Int = 0
//        val userAuth = sharedPrefs.getInt(getString(R.string.user_auth_string), defaultValue)
//
//        Log.d(getString(R.string.logKey), userAuth.toString())
//        if (userAuth != 12){
////            Toast.makeText(this.context, R.string.user_not_authenticated, Toast.LENGTH_LONG).show()
////            Redirect user to login fragment.. to login
//            this.findNavController().navigate(R.id.action_navigation_profile_to_userAuth)
//        }
//    }
}