package com.example.liranyehudar.socialnetworkforacademic.activities;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.example.liranyehudar.socialnetworkforacademic.R;
import com.example.liranyehudar.socialnetworkforacademic.fragments.CoursesFragment;
import com.example.liranyehudar.socialnetworkforacademic.fragments.HomeFeedFragment;
import com.example.liranyehudar.socialnetworkforacademic.fragments.SearchFragment;
import com.example.liranyehudar.socialnetworkforacademic.logic.Student;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView mMainNav;
    private LinearLayout mMainFrame;

    private HomeFeedFragment homeFeedFragment;
    private CoursesFragment coursesFragment;
    private SearchFragment searchFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bindUI();
        //Temporary ...
       // Student student =(Student) getIntent().getSerializableExtra("student");

        homeFeedFragment = new HomeFeedFragment();
        coursesFragment = new CoursesFragment();
        searchFragment = new SearchFragment();

        setFragment(homeFeedFragment); // default
        mMainNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_home:
                        setFragment(homeFeedFragment);
                        return true;

                    case R.id.nav_courses:
                        setFragment(coursesFragment);
                        return true;

                    case R.id.nav_search:
                        setFragment(searchFragment);
                        return true;

                        default:
                            return false;

                }
            }
        });
    }

    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_frame,fragment);
        fragmentTransaction.commit();
    }

    private void bindUI() {
        mMainFrame = findViewById(R.id.main_frame);
        mMainNav = findViewById(R.id.main_nav);
    }
}
