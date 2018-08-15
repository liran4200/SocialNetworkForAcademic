package com.example.liranyehudar.socialnetworkforacademic.activities;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.LinearLayout;

import com.example.liranyehudar.socialnetworkforacademic.R;
import com.example.liranyehudar.socialnetworkforacademic.fragments.CoursesFragment;
import com.example.liranyehudar.socialnetworkforacademic.fragments.HomeFeedFragment;
import com.example.liranyehudar.socialnetworkforacademic.fragments.SearchFragment;

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
        init();
        // navigate between home,courses,searching fragments
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

    private void init() {
        homeFeedFragment = new HomeFeedFragment();
        coursesFragment = new CoursesFragment();
        searchFragment = new SearchFragment();
        setFragment(homeFeedFragment); // default
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
