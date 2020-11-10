package utc.thong.roomexample.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import utc.thong.roomexample.Student;

@Database(entities = {Student.class}, version = 1)
public abstract class StudentDatabase extends RoomDatabase {
    public abstract StudentDao getDao();
}
