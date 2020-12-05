package `in`.co.jnana.ui.auth

import `in`.co.jnana.database.user.Student
import `in`.co.jnana.database.user.StudentDAO
import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kotlinx.coroutines.*

class SignupViewModel(private val dataSource: StudentDAO, application: Application) : ViewModel() {

    private var name: String? = null
    private var userName: String? = null
    private var passOnce: String? = null
    private var passTwice: String? = null
    private var gender: String? = null
    private var emailID: String? = null
    private var mobileNum: Long? = null

    private var _successSignup = MutableLiveData<Boolean>()
    val successSignup: LiveData<Boolean>
        get() = _successSignup

    private val uiScope = CoroutineScope(Dispatchers.Main + Job())

    fun setValuesOfEdits(
        name: String,
        userName: String,
        password: String,
        password2: String,
        gender: String,
        email: String,
        mobile: Long
    ) {
        Log.i(">>>>>>>>>>>>Log", "setValuesOfEdits is called..!!")
        this.name = name
        this.userName = userName
        this.passOnce = password
        this.passTwice = password2
        this.gender = gender
        this.emailID = email
        this.mobileNum = mobile
    }

    fun insertDataToDatabase() {
        val student = Student(
            userName = this.userName!!,
            password = this.passOnce!!,
            name = this.name!!,
            gender = this.gender!!,
            emailID = this.emailID!!,
            mobileNo = this.mobileNum!!
        )
        uiScope.launch {
            withContext(Dispatchers.IO){
                dataSource.insert(student) // Inserting to database
            }
        }
        _successSignup.value = true
    }

    fun setPassword(): Boolean {
        return passOnce == passTwice
    }

    fun checkUsername(): Boolean {
        var stud: Student? = null
        uiScope.launch {
            withContext(Dispatchers.IO){
                stud = dataSource.getStudentByUsername(userName!!)
            }
        }
        return stud == null
    }
}

@Suppress("UNCHECKED_CAST")
class SignupViewmodelFactory(
    private val dataSource: StudentDAO,
    private val application: Application
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SignupViewModel::class.java))
            return SignupViewModel(dataSource, application) as T
        throw IllegalArgumentException("Unknown viewmodel class")
    }

}