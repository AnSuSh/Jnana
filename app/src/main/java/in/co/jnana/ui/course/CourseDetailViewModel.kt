package `in`.co.jnana.ui.course

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class CourseDetailViewModel(val title: String, val description: String) : ViewModel() {

    private var _courseTitle = MutableLiveData<String>()
    val courseTitle: LiveData<String>
        get() = _courseTitle

    private var _courseDescription = MutableLiveData<String>()
    val courseDescription: LiveData<String>
        get() = _courseDescription

    fun setTitle(title: String){
        _courseTitle.value = title
    }

    fun setDescription(desc: String) {
        _courseDescription.value = desc
    }
}

@Suppress("UNCHECKED_CAST")
class CourseDeatilViewModelFactory(
    private val title: String,
    private val description: String
): ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CourseDetailViewModel::class.java))
            return CourseDetailViewModel(title, description) as T
        throw IllegalArgumentException("Unknown viewmodel class")
    }
}