package `in`.co.jnana.database.user

import android.content.Context
import androidx.room.*
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.internal.synchronized

@Database(entities = [Student::class], version = 1, exportSchema = false)
//@TypeConverters(Converters::class)
abstract class StudentDatabase : RoomDatabase() {
    abstract val studentDatabaseDAO: StudentDAO

    companion object {
        @Volatile
        private var INSTANCE: StudentDatabase? = null

        @InternalCoroutinesApi
        fun getInstance(context: Context): StudentDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context,
                        StudentDatabase::class.java,
                        "Student_Record_Database"
                    )
                        .fallbackToDestructiveMigration().build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}

@Dao
interface StudentDAO {
    @Insert
    fun insert(student: Student)

    @Update
    fun update(student: Student)

    @Delete
    fun delete(student: Student)

    @Query("select * from Student_table where studentID = :key")
    fun getStudent(key: Long): Student?

    @Query(value = "delete from STUDENT_TABLE")
    fun clear()

    @Query("select * from student_table where stud_email=:email")
    fun getStudentByEmail(email: String): Student?

    @Query("select * from student_table where username=:uname")
    fun getStudentByUsername(uname: String): Student?
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