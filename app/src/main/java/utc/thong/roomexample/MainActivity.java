package utc.thong.roomexample;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class MainActivity extends AppCompatActivity implements StudentListFragment.OnItemClickedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentById(R.id.fragment_container);
        Fragment listFragment = fragmentManager.findFragmentById(R.id.fragment_list_container);
        Fragment detailFragment = fragmentManager.findFragmentById(R.id.fragment_detail_container);

        //Đang ở chế độ 1 fragment
        if (findViewById(R.id.fragment_container) != null) {
            //Kiểm tra xem có phải thay đổi cấu hình không
            if (fragment == null) {

                FragmentTransaction transaction = fragmentManager.beginTransaction();
                if (listFragment != null) {
                    transaction.remove(listFragment);
                }

                if (detailFragment != null) {
                    transaction.remove(detailFragment);
                }

                transaction.add(R.id.fragment_container, StudentListFragment.newInstance()).commit();
            }
        } else {
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            if (fragment != null) {
                transaction.remove(fragment);
            }

            if (listFragment == null) {
                transaction.add(R.id.fragment_list_container, StudentListFragment.newInstance())
                        .commit();
            }
        }
    }

    @Override
    public void onItemCLicked(String studentId) {
        FragmentManager fragmentManager = getSupportFragmentManager();

        //Đang ở chế độ 1 fragment -> cần thay thế fragment cũ với fragment mới.
        if (findViewById(R.id.fragment_container) != null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, StudentDetailFragment.newInstance(studentId))
                    .addToBackStack(null)
                    .commit();
        } else {
            //Ở chế độ 2 fragment
            StudentDetailFragment detailFragment = (StudentDetailFragment) fragmentManager.findFragmentById(R.id.fragment_detail_container);

            if (detailFragment == null) {
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.fragment_detail_container, StudentDetailFragment.newInstance(studentId))
                        .commit();
            } else {
                detailFragment.loadStudent(studentId);
            }
        }
    }
}