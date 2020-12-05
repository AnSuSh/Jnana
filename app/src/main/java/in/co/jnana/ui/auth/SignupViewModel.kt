package `in`.co.jnana.ui.auth

import android.util.Log
import androidx.lifecycle.ViewModel
import kotlin.properties.Delegates

class SignupViewModel : ViewModel() {

    private lateinit var name: String
    private lateinit var userName: String
    private lateinit var passOnce: String
    private lateinit var passTwice: String
    private lateinit var gender: String
    private lateinit var emailID: String
    private var mobileNum by Delegates.notNull<Long>()

    fun setValuesOfEdits(name: String, userName: String, password: String, password2: String, gender: String, email: String, mobile: Long) {
        Log.i(">>>>>>>>>>>>Log", "setValuesOfedits is called..!!")
        this.name = name
        this.userName = userName
        this.passOnce = password
        this.passTwice = password2
        this.gender = gender
        this.emailID = email
        this.mobileNum = mobile
    }
}