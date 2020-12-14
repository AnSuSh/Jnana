package `in`.co.jnana.ui.profile

import `in`.co.jnana.database.CourseStudentDAO
import `in`.co.jnana.database.StudentWithCourses
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kotlinx.coroutines.*

class ProfileViewModel(
    private val dataSource: CourseStudentDAO
) : ViewModel() {

    private val viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private val _navigateToCourseDetail = MutableLiveData<Long>()
    val navigateToCourseDetail
        get() = _navigateToCourseDetail


    // initialize database values here..
    private var _dataset = MutableLiveData<List<StudentWithCourses>>()
    val dataset: LiveData<List<StudentWithCourses>> = _dataset

    fun onCourseItemClicked(courseID: Long) {
        _navigateToCourseDetail.value = courseID

    }

    fun onCourseDetailNavigated() {
        _navigateToCourseDetail.value = null
    }

    fun loadDataSet() {
        uiScope.launch {
            withContext(Dispatchers.IO) {
                _dataset.postValue(dataSource.getStudentWithCourses())
            }
        }
    }

    fun greetUser(username: String) {
        _text.value = "Hello $username..!!"
    }

    private val _text = MutableLiveData<String>().apply {
        value = "This is Profile Fragment"
    }
    val text: LiveData<String> = _text
}

@Suppress("UNCHECKED_CAST")
class ProfileViewModelFactory(private val dataSource: CourseStudentDAO) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProfileViewModel::class.java))
            return ProfileViewModel(dataSource) as T
        throw IllegalArgumentException("ProfileViewModel class not exist!!")
    }
}