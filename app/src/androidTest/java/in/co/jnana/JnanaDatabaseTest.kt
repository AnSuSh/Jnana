package `in`.co.jnana

import `in`.co.jnana.database.JnanaDatabase
import `in`.co.jnana.database.Student
import `in`.co.jnana.database.StudentDAO
import android.util.Log
import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class JnanaDatabaseTest {

    private lateinit var studentDAO: StudentDAO
    private lateinit var db: JnanaDatabase

    @Before
    fun createDB() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext

        db = Room.databaseBuilder(context, JnanaDatabase::class.java, "Student_Database")
            .allowMainThreadQueries().build()
        studentDAO = db.studentDatabaseDAO
    }

    @After
    @Throws(IOException::class)
    fun closeDB() {
        db.close()
    }


    @Test
    @Throws(Exception::class)
    fun insertAndGetStudnet() {
        val student = Student(
            userName = "hero",
            password = "",
            name = "",
            gender = "male",
            emailID = "90",
            mobileNo = 9056789453
        )
        studentDAO.insert(student)
        val studentNew = studentDAO.getStudentByUsername("hero")
        assertEquals(studentNew?.mobileNo, 9056789453)
        Log.i("STudentTableDataNEW", studentNew.toString())
    }

}