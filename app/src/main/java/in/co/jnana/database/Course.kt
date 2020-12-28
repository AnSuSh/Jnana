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
    val description: String,

    @ColumnInfo(name = "course_URI")
    val url: String
)

val course: List<Course> = listOf(
    Course(
        1,
        "Introduction to Python Programming Language",
        "Python is an interpreted, high-level and general-purpose programming language. Python's design philosophy emphasizes code readability with its notable use of significant whitespace. Its language constructs and object-oriented approach aim to help programmers write clear, logical code for small and large-scale projects.",
        "https://www.youtube.com/?v=WvhQhj4n6b8"
    ),
    Course(
        2,
        "Introduction to Dart Programming Language",
        "Dart is a client-optimized programming language for apps on multiple platforms. It is developed by Google and is used to build mobile, desktop, server, and web applications.",
        "https://www.youtube.com/?v=KajbC6TzcTc"
    ),
    Course(
        3,
        "Introduction to Java Programming Language",
        "Java is a class-based, object-oriented programming language that is designed to have as few implementation dependencies as possible. It is a general-purpose programming language intended to let application developers write once, run anywhere (WORA), meaning that compiled Java code can run on all platforms that support Java without the need for recompilation",
        "https://www.youtube.com/?v=Ypoad4wtJ7A"
    ),
    Course(
        4,
        "Introduction to JavaScript Programming Language",
        "JavaScript was developed by Brendan Eich in 1995, which appeared in Netscape, a popular browser of that time. The language was initially called LiveScript and was later renamed JavaScript. There are many programmers who think that JavaScript and Java are the same. In fact, JavaScript and Java are very much unrelated.",
        "https://www.youtube.com/?v=1HakS7KsbCk"
    ),
    Course(
        5,
        "Introduction to PHP Programming Language",
        "PHP is a general-purpose scripting language especially suited to web development. It was originally created by Danish-Canadian programmer Rasmus Lerdorf in 1994. The PHP reference implementation is now produced by The PHP Group.",
        "https://www.youtube.com/?v=qVU3V0A05k8"
    )
)
