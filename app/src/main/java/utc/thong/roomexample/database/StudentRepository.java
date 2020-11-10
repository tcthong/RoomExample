package utc.thong.roomexample.database;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;
import androidx.room.Room;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import utc.thong.roomexample.Student;

public class StudentRepository {
    private static final String DATABASE_NAME = "student.db";
    private static StudentRepository instance = null;
    private StudentDao dao;
    private ExecutorService executor;

    private StudentRepository(Context context) {
        StudentDatabase database = Room.databaseBuilder(
                context.getApplicationContext(),
                StudentDatabase.class,
                DATABASE_NAME
        ).build();
        dao = database.getDao();

        executor = Executors.newSingleThreadExecutor();
    }

    public static void initialize(Context context) {
        if (instance == null) {
            instance = new StudentRepository(context);
        }
    }

    public static StudentRepository getInstance() {
        if (instance == null) {
            throw new IllegalStateException("be not initialized");
        }
        return instance;
    }

    public LiveData<List<Student>> getAllStudents() {
        return Transformations.distinctUntilChanged(dao.getAllStudents());
    }

    public void addStudent(Student student) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                dao.addStudent(student);
            }
        });
    }

    public void updateStudent(Student student) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                dao.updateStudent(student);
            }
        });
    }

    public LiveData<Student> getStudentById(String studentId) {
        return dao.getStudentById(studentId);
    }

    public void removeStudent(Student student) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                dao.removeStudent(student);
            }
        });

    }
}
