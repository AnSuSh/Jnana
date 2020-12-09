package `in`.co.jnana.ui.course

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
