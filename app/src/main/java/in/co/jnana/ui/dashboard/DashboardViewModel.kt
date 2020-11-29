package `in`.co.jnana.ui.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class DashboardViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Trending Courses"
    }
    val text: LiveData<String> = _text

    private var _dataset = MutableLiveData<Array<String>>().apply {
        value = arrayOf(
            "Introduction to Python Programming Language",
            "Introduction to Dart Programming Language",
            "Introduction to C Programming Language",
            "Introduction to C++ Programming Language",
            "Introduction to Java Programming Language",
            "Introduction to Flutter Programming Language",
            "Introduction to C Sharp Programming Language",
            "Introduction to Assembly Programming Language",
            "Advanced Android Course",
            "iOS Development Course",
            "Swift Programming Basics"
        )
    }
    private val dataset: LiveData<Array<String>> = _dataset

    val adapter = CourseAdapter(dataset.value!!)

}