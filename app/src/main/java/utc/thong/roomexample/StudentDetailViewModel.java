package utc.thong.roomexample;

import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import utc.thong.roomexample.database.StudentRepository;

public class StudentDetailViewModel extends ViewModel {
    private StudentRepository repository = StudentRepository.getInstance();
    private MutableLiveData<String> studentIdLiveData = new MutableLiveData<>();

    private LiveData<Student> studentLiveData = Transformations.switchMap(studentIdLiveData, new Function<String, LiveData<Student>>() {
        @Override
        public LiveData<Student> apply(String studentId) {
            return repository.getStudentById(studentId);
        }
    });

    public LiveData<Student> getStudentLiveData() {
        return studentLiveData;
    }

    public void loadStudent(String studentId) {
        studentIdLiveData.setValue(studentId);
    }

    public void updateStudent(Student student) {
        repository.updateStudent(student);
    }

    public void removeStudent(Student student) {
        repository.removeStudent(student);
    }
}
