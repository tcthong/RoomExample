package utc.thong.roomexample;

import android.app.Application;

import utc.thong.roomexample.database.StudentRepository;

public class StudentApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        StudentRepository.initialize(this);
    }
}
