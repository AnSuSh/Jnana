package `in`.co.jnana.database

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Junction
import androidx.room.Relation

@Entity(primaryKeys = ["courseID", "studentID"], tableName = "course_student")
data class CourseStudentCrossRef(
    val courseID: Long,
    val studentID: Long
)

// You want list of courses which a user has bought.
data class StudentWithCourses(
    @Embedded val student: Student,
    @Relation(
        parentColumn = "studentID",
        entityColumn = "courseID",
        associateBy = Junction(CourseStudentCrossRef::class)
    )
    val courses: List<Course>
)

// You want list of Users who bought the course
//data class CourseWithStudent(
//    @Embedded val course: Course,
//    @Relation(
//        parentColumn = "courseID",
//        entityColumn = "studentID",
//        associateBy = Junction(CourseStudentCrossRef::class)
//    )
//    val students: List<Student>
//)