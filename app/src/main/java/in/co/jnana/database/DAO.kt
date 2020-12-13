package `in`.co.jnana.database

import android.content.Context
import androidx.room.*
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.internal.synchronized

@Database(entities = [Student::class, Course::class, CourseStudentCrossRef::class], version = 1, exportSchema = false)
//@TypeConverters(Converters::class)
abstract class JnanaDatabase : RoomDatabase() {
    abstract val studentDatabaseDAO: StudentDAO
    abstract val courseDatabaseDAO: CourseDAO
    abstract val courseStudentDAO: CourseStudentDAO

    companion object {
        @Volatile
        private var INSTANCE: JnanaDatabase? = null

        @InternalCoroutinesApi
        fun getInstance(context: Context): JnanaDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context,
                        JnanaDatabase::class.java,
                        "Student_Record_Database"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}

@Dao
interface CourseStudentDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(courseStudentCrossRef: CourseStudentCrossRef)

    @Transaction
    @Query("select * from Student_table")
    fun getStudentWithCourses(): List<StudentWithCourses>

}

@Dao
interface StudentDAO {
    @Insert
    fun insert(student: Student)

    @Update
    fun update(student: Student)

    @Delete
    fun delete(student: Student)

    @Query("select * from student_table where username=:uname")
    fun getStudentByUsername(uname: String): Student?
}

@Dao
interface CourseDAO {
    @Insert
    fun insert(course: Course)

    @Update
    fun update(course: Course)

    @Delete
    fun delete(course: Course)

    @Query("select * from course_table where courseID=:courseID")
    fun getCourseByID(courseID: Long): Course?

    @Query("select * from course_table")
    fun getAllCourses(): List<Course>
}

//class Converters {
//    @TypeConverter
//    fun fromTimestamp(value: Long?): Date? {
//        return value?.let { Date(it) }
//    }
//
//    @TypeConverter
//    fun dateToTimestamp(date: Date?): Long? {
//        return date?.time
//    }
//}