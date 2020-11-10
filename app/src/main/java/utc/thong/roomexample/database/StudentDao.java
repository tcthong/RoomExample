package utc.thong.roomexample.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import utc.thong.roomexample.Student;

@Dao
public interface StudentDao {
    @Query("select * from student")
    LiveData<List<Student>> getAllStudents();

    @Query("select * from student where id = :id")
    LiveData<Student> getStudentById(String id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addStudent(Student student);

    @Update
    void updateStudent(Student student);

    @Delete
    void removeStudent(Student student);

    @Query("delete from student")
    void removeAllStudents();
}
