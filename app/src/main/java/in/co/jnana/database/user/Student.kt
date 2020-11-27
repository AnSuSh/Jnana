package `in`.co.jnana.database.user

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Student_table")
data class Student(

    @PrimaryKey(autoGenerate = true)
    var studentID: Long = 0L,

    @ColumnInfo(name = "username")
    val userName: String,

    @ColumnInfo(name = "password")
    var password: String = "",

    @ColumnInfo(name = "stud_name")
    val name: String,
//
//    @ColumnInfo(name = "stud_dob")
//    val dateOfBirth: Date?,

    @ColumnInfo(name = "stud_gender")
    val gender: String,

    @ColumnInfo(name = "stud_email")
    var emailID: String = "",

    @ColumnInfo(name = "stud_mobile")
    var mobileNo: Long = -1
)