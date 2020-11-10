package utc.thong.roomexample;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import utc.thong.roomexample.database.StudentRepository;

public class StudentListViewModel extends ViewModel {
    private StudentRepository repository = StudentRepository.getInstance();
    private LiveData<List<Student>> studentsLiveData = repository.getAllStudents();

    public LiveData<List<Student>> getStudentsLiveData() {
        return studentsLiveData;
    }

    public void addStudent(Student student) {
        repository.addStudent(student);
    }

    public void removeStudent(Student student) {
        repository.removeStudent(student);
    }
}
