package `in`.co.jnana.ui.auth

import `in`.co.jnana.database.Student
import `in`.co.jnana.database.StudentDAO
import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import kotlinx.coroutines.*

class UserAuthViewModel(private val dataSource: StudentDAO, application: Application) :
    AndroidViewModel(application) {

    /**
     * temp entry
     * */
    private val sNew = Student(
        userName = "gani",
        password = "123456",
        gender = "male",
        name = "Ganesh",
        emailID = "gani@123.com",
        mobileNo = 9078563412
    )

    private var _sAuth = MutableLiveData<Student>()
    val sAuth: LiveData<Student>
        get() = _sAuth


    private val _userName = MutableLiveData<String>()

    /**
     * Livedata holding value of username textview
     * */

    private val userName: LiveData<String>
        get() = _userName


    private val _password = MutableLiveData<String>()

    /**
     * Livedata holding value of password textview
     * */
    private val password: LiveData<String>
        get() = _password

    private val viewModelJob = Job()

    /**
     * Scope of UI and Viewmodel for the coroutine to run
     * */
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)


    private var _navigateToSignUpFragment = MutableLiveData<Boolean>()

    /**
     * Variable to observe state of the Signup process fragment transition
     * */
    val navigateToSignUpFragment: LiveData<Boolean>
        get() = _navigateToSignUpFragment


    private var _navigateToProfileFragment = MutableLiveData<Boolean>()

    /**
     * Variable to observe state of the Profile fragment transition
     * */
    val navigateToProfileFragment: LiveData<Boolean>
        get() = _navigateToProfileFragment

    private var _showToast = MutableLiveData<String>()
    val showToast: LiveData<String>
        get() = _showToast

//    private var _passwordErrorShow = MutableLiveData<Boolean>()
//
//    /**
//     * Variable to observe state of the password textView to show error.
//     * */
//    val passwordErrorShow: LiveData<Boolean>
//        get() = _passwordErrorShow
//
//    private var _usernameErrorShow = MutableLiveData<Boolean>()
//
//    /**
//     * Variable to observe state of the username textView to show error.
//     * */
//    val usernameErrorShow: LiveData<Boolean>
//        get() = _usernameErrorShow
//
    /**
     *  Takes user to login page and further to authentication.
     * */
    fun doUserLogin() {   // Advanced code folding
        Log.i(
            "...........LOG.......",
            "Login button is clicked..!!${dataSource}, ${userName.value}, ${password.value}"
        )
        uiScope.launch {
            userIfExist()?.let {
                Log.i(
                    "....................LOG",
                    "password is: ${it.password} entered password is : ${password.value}"
                )
                if (it.password == password.value) { //password matched
                    _sAuth.value = it
                    _navigateToProfileFragment.value = true
                    Log.i(">>>>Log", "Navigating to profile fragment")
                } //                else {
//                    _showToast.value = "password"
//                }
//                if (it == null)
//                    _showToast.value = "user"
            }
        }
    }

    /**
     *  Function for the signUP process.
     *  Called when user hits signup button in USER
     * */
    fun doUserSignup() {
        Log.i("....................LOG", "Signup is in process")
        _navigateToSignUpFragment.value = true
    }

    /**
     * Function checks if user exists in table or not and if exists, returns the password of the user or null
     * */
    private suspend fun userIfExist(): Student? {
        return withContext(Dispatchers.IO) {
            val student = userName.value?.let {
                dataSource.getStudentByUsername(it)
            }  // Advanced code folding
            Log.i(
                ".......LOG",
                student.toString()
            )
            student?.let {
                Log.i(
                    ".................LOG",
                    "Student is not null"
                )
            }
            student
        }
    }

//    /**
//     * Setting viewmodel variable of profile page navigation to false
//     * when navigation is complete.
//     * */
//    fun doneNavigationToProfileFragment() {
//        _navigateToProfileFragment.value = null
//        Log.i(">>>>Log", "Navigation set to false for profile fragment")
//    }

//    /**
//     *  Setting viewmodel variable of Error show in username textview to false
//     * */
//    fun doneUsernameErrorShow() {
//        _usernameErrorShow.value = false
//    }

//    /**
//     *  Setting viewmodel variable of Error show in password textview to false
//     * */
//    fun donePasswordErrorShow() {
//        _passwordErrorShow.value = false
//    }

    fun runSetupDatabase() {
        uiScope.launch {
            withContext(Dispatchers.IO) {
                val sOld = dataSource.getStudentByUsername("gani")
                if (sOld == null) {
                    dataSource.insert(sNew)
                }
            }
        }
    }

    fun setValues(username: String, password: String) {
        _userName.value = username
        _password.value = password
    }
}


/**
 *  Factory which provides viewmodel with required parameters.
 * */
@Suppress("UNCHECKED_CAST")
class UserAuthViewModelFactory(
    private val dataSource: StudentDAO,
    private val application: Application
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UserAuthViewModel::class.java))
            return UserAuthViewModel(dataSource, application) as T
        throw IllegalArgumentException("Unknown viewmodel class")
    }
}