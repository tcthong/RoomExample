package utc.thong.roomexample;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;
import java.util.Random;

public class StudentListFragment extends Fragment {
    private RecyclerView recyclerView;
    private FloatingActionButton fabAddStudent;
    private StudentListViewModel viewModel;
    private StudentAdapter adapter;
    private OnItemClickedListener listener;

    public StudentListFragment() {

    }


    public static StudentListFragment newInstance() {
        return new StudentListFragment();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof OnItemClickedListener) {
            listener = (OnItemClickedListener) context;
        } else {
            throw new IllegalStateException("Have not implemented OnItemClickedListener");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(StudentListViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_student_list, container, false);
        recyclerView = view.findViewById(R.id.recycler_view);
        fabAddStudent = view.findViewById(R.id.add_student_fab);

        adapter = new StudentAdapter();
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        recyclerView.addItemDecoration(new DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL));

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel.getStudentsLiveData().observe(getViewLifecycleOwner(), new Observer<List<Student>>() {
            @Override
            public void onChanged(List<Student> students) {
                if (students != null) {
                    adapter.submitList(students);
                }
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        fabAddStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Student student = new Student("SV" + new Random().nextInt(1000));
                viewModel.addStudent(student);
                listener.onItemCLicked(student.getId());
            }
        });
    }

    public interface OnItemClickedListener {
        void onItemCLicked(String studentId);
    }

    private static class StudentDiffUtil extends DiffUtil.ItemCallback<Student> {
        @Override
        public boolean areItemsTheSame(@NonNull Student oldItem, @NonNull Student newItem) {
            return StringUtils.isSameStrings(oldItem.getId(), newItem.getId());
        }

        @Override
        public boolean areContentsTheSame(@NonNull Student oldItem, @NonNull Student newItem) {
            return StringUtils.isSameStrings(oldItem.getFullName(), newItem.getFullName())
                    && StringUtils.isSameStrings(oldItem.getEmail(), newItem.getEmail());
        }
    }

    private class StudentHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private Student student;
        private TextView tvFullName;
        private TextView tvEmail;

        public StudentHolder(@NonNull View itemView) {
            super(itemView);

            tvFullName = itemView.findViewById(R.id.full_name_tv);
            tvEmail = itemView.findViewById(R.id.email_tv);

            itemView.setOnClickListener(this);
        }

        public void bindStudent(Student student) {
            this.student = student;
            tvFullName.setText(student.getFullName());
            tvEmail.setText(student.getEmail());
        }

        @Override
        public void onClick(View v) {
            listener.onItemCLicked(student.getId());
        }
    }

    private class StudentAdapter extends ListAdapter<Student, StudentHolder> {
        public StudentAdapter() {
            super(new StudentDiffUtil());
        }

        @NonNull
        @Override
        public StudentHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());

            View view = inflater.inflate(R.layout.student_item, parent, false);
            return new StudentHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull StudentHolder holder, int position) {
            holder.bindStudent(getItem(position));
        }
    }
}