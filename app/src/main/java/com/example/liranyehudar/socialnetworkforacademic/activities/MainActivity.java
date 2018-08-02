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
import com.example.liranyehudar.socialnetworkforacademic.logic.Student;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView mMainNav;
    private LinearLayout mMainFrame;

    private HomeFeedFragment homeFeedFragment;
    private CoursesFragment coursesFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bindUI();
        //Temporary ...
       // Student student =(Student) getIntent().getSerializableExtra("student");

        homeFeedFragment = new HomeFeedFragment();
        coursesFragment = new CoursesFragment();

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
