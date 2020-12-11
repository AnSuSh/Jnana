package `in`.co.jnana.ui.course

import `in`.co.jnana.database.Course
import `in`.co.jnana.database.CourseDAO
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kotlinx.coroutines.*

class CourseDetailViewModel(
    private val courseID: Long,
    private val dataSource: CourseDAO,
//    private val sDataSource: CourseStudentDAO
) : ViewModel() {

    private val viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private lateinit var courseTemporary: Course

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

//    fun buyCourse(courseID: Long) {
//        uiScope.launch {
//            withContext(Dispatchers.IO) {
//                sDataSource.insert(StudentWithCourses())
//            }
//        }
//    }

}

@Suppress("UNCHECKED_CAST")
class CourseDetailViewModelFactory(
    private val courseID: Long,
    private val dataSource: CourseDAO,
//    private val sDataSource: CourseStudentDAO
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CourseDetailViewModel::class.java))
            return CourseDetailViewModel(
                courseID,
                dataSource,
//                sDataSource
            ) as T
        throw IllegalArgumentException("Unknown viewmodel class")
    }
}