package `in`.co.jnana.ui.dashboard

import `in`.co.jnana.database.Course
import `in`.co.jnana.database.CourseDAO
import `in`.co.jnana.database.course
import android.app.Application
import androidx.lifecycle.*
import kotlinx.coroutines.*

class DashboardViewModel(private val dataSource: CourseDAO, application: Application) :
    AndroidViewModel(application) {

    private val viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private val _text = MutableLiveData<String>().apply {
        value = "Trending Courses"
    }
    val text: LiveData<String> = _text

    private val _navigateToCourseDetail = MutableLiveData<Long>()
    val navigateToCourseDetail
        get() = _navigateToCourseDetail


    // initialize database values here..
    private var _dataset = MutableLiveData<List<Course>>()
    val dataset: LiveData<List<Course>> = _dataset

    fun onCourseItemClicked(courseID: Long) {
        _navigateToCourseDetail.value = courseID
    }

    fun onCourseDetailNavigated() {
        _navigateToCourseDetail.value = null
    }

    fun runCourseDatabaseSetup() {
        uiScope.launch {
            withContext(Dispatchers.IO) {
                val tempCourse = dataSource.getCourseByID(3)
                if (tempCourse == null) {
                    dataSource.insert(course[0])
                    dataSource.insert(course[1])
                    dataSource.insert(course[2])
                    dataSource.insert(course[3])
                    dataSource.insert(course[4])
                } else
                    refreshData()
            }
        }
    }

    private suspend fun refreshData() {
        withContext(Dispatchers.IO) {
//          _dataset.value = dataSource.getAllCourses()   This won't work, since it touces UI related variable in a backgroud thread
//            Either You use a temp variable to get data from database
            _dataset.postValue(dataSource.getAllCourses())
        }
    }
}

@Suppress("UNCHECKED_CAST")
class DashboardViewModelFactory(private val dataSource: CourseDAO, val application: Application) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DashboardViewModel::class.java))
            return DashboardViewModel(dataSource, application) as T
        throw IllegalArgumentException("DashboardViewModel class not exist!!")
    }
}