package utc.thong.roomexample;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

public class StudentDetailFragment extends Fragment {
    private EditText edId;
    private EditText edFullName;
    private EditText edEmail;
    private Button btnSave;
    private Student student;
    private StudentDetailViewModel viewModel;

    private static final String ARG_STUDENT_ID = "studentId";

    public StudentDetailFragment() {

    }

    public static StudentDetailFragment newInstance(String studentId) {
        StudentDetailFragment fragment = new StudentDetailFragment();
        Bundle args = new Bundle();
        args.putString(ARG_STUDENT_ID, studentId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(StudentDetailViewModel.class);
        if (getArguments() != null) {
            loadStudent(getArguments().getString(ARG_STUDENT_ID));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_student_detail, container, false);
        edId = view.findViewById(R.id.id_ed);
        edFullName = view.findViewById(R.id.name_ed);
        edEmail = view.findViewById(R.id.email_ed);
        btnSave = view.findViewById(R.id.save_btn);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel.getStudentLiveData().observe(getViewLifecycleOwner(), new Observer<Student>() {
            @Override
            public void onChanged(Student student) {
                if (student != null) {
                    StudentDetailFragment.this.student = student;
                    updateUi();
                }
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = edId.getText().toString();
                String name = edFullName.getText().toString();
                String email = edEmail.getText().toString();

                student.setId(id);
                student.setFullName(name);
                student.setEmail(email);

                viewModel.updateStudent(student);

                onBackPressed();
            }
        });
    }

    private void onBackPressed() {
        getParentFragmentManager().popBackStackImmediate();
    }

    private void updateUi() {
        edId.setText(student.getId());
        edFullName.setText(student.getFullName());
        edEmail.setText(student.getEmail());
    }

    public void loadStudent(String studentId) {
        viewModel.loadStudent(studentId);
    }
}