package `in`.co.jnana.ui.dashboard

import `in`.co.jnana.ui.course.Course
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class DashboardViewModel : ViewModel() {

//    private val viewModelJob = Job()
//    val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private val _text = MutableLiveData<String>().apply {
        value = "Trending Courses"
    }
    val text: LiveData<String> = _text

    // initialize database values here..
    private var _dataset = MutableLiveData<List<Course>>().apply {
        value = listOf(
            Course(
                1,
                "Introduction to Python Programming Language",
                "Python is an interpreted, high-level and general-purpose programming language. Python's design philosophy emphasizes code readability with its notable use of significant whitespace. Its language constructs and object-oriented approach aim to help programmers write clear, logical code for small and large-scale projects."
            ),
            Course(
                2,
                "Introduction to Dart Programming Language",
                "Dart is a client-optimized programming language for apps on multiple platforms. It is developed by Google and is used to build mobile, desktop, server, and web applications."
            ),
            Course(
                3,
                "Introduction to Java Programming Language",
                "Java is a class-based, object-oriented programming language that is designed to have as few implementation dependencies as possible. It is a general-purpose programming language intended to let application developers write once, run anywhere (WORA), meaning that compiled Java code can run on all platforms that support Java without the need for recompilation"
            )
        )
    }
    val dataset: LiveData<List<Course>> = _dataset
}

//
//            "Introduction to C Programming Language",
//            "Introduction to C++ Programming Language",
//
//            "Introduction to Flutter Programming Language",
//            "Introduction to C Sharp Programming Language",
//            "Introduction to Assembly Programming Language",
//            "Advanced Android Course",
//            "iOS Development Course",
//            "Swift Programming Basics"
