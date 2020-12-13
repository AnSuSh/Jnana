package `in`.co.jnana.ui.course

import `in`.co.jnana.database.*
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kotlinx.coroutines.*

class CourseDetailViewModel(
    private val courseID: Long,
    private val dataSource: CourseDAO,
    private val studentDataSource: StudentDAO,
    private val sDataSource: CourseStudentDAO
) : ViewModel() {

    private val viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private lateinit var courseTemporary: Course
    private var _studentID = MutableLiveData<Long>()
    val studentID: LiveData<Long>
        get() = _studentID


    fun getCourseData() {
        uiScope.launch {
            withContext(Dispatchers.IO) {
                courseTemporary = dataSource.getCourseByID(courseID)!!
            }
            withContext(Dispatchers.Main) {
                _courseTitle.value = courseTemporary.title
                _courseDescription.value = courseTemporary.description
            }
        }
    }

    private var _courseTitle = MutableLiveData<String>()
    val courseTitle: LiveData<String>
        get() = _courseTitle

    private var _courseDescription = MutableLiveData<String>()
    val courseDescription: LiveData<String>
        get() = _courseDescription

    fun buyCourse(courseID: Long, studentID: Long) {
        uiScope.launch {
            withContext(Dispatchers.IO) {
                sDataSource.insert(CourseStudentCrossRef(courseID, studentID))
                Log.d("Log....", "Inserting...")
            }
        }
    }

    fun getUserIDByUsername(userAuthName: String) {
        _studentID.value = 0
        uiScope.launch {
            withContext(Dispatchers.IO) {
                val student = studentDataSource.getStudentByUsername(userAuthName)
                _studentID.postValue(student?.studentID!!)
            }
        }
        Log.d("Log.....", "returning id of the user ${_studentID.value}")
    }
}

@Suppress("UNCHECKED_CAST")
class CourseDetailViewModelFactory(
    private val courseID: Long,
    private val dataSource: CourseDAO,
    private val studentDataSource: StudentDAO,
    private val sDataSource: CourseStudentDAO
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CourseDetailViewModel::class.java))
            return CourseDetailViewModel(
                courseID,
                dataSource,
                studentDataSource,
                sDataSource
            ) as T
        throw IllegalArgumentException("Unknown viewmodel class")
    }
}