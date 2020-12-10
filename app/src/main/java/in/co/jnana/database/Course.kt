package `in`.co.jnana.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Course_Table")
data class Course(
    @PrimaryKey
    val courseID: Long,

    @ColumnInfo(name = "course_title")
    val title: String,

    @ColumnInfo(name = "course_description")
    val description: String
)

val course: List<Course> = listOf(
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
