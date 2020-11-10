package utc.thong.roomexample;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.Objects;

@Entity
public class Student {
    @PrimaryKey
    @NonNull
    private String id;

    @ColumnInfo(name = "full_name")
    private String fullName;

    private String email;

    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Ignore
    public Student(String id) {
        this.id = id;
    }

    public Student(String id, String fullName, String email) {
        this.id = id;
        this.fullName = fullName;
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return StringUtils.isSameStrings(id, student.id) && StringUtils.isSameStrings(fullName, student.fullName) && StringUtils.isSameStrings(email, student.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, fullName, email);
    }

    public String getAvatarPath() {
        return id + "_avatar.jpg";
    }
}
