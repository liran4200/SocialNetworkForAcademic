package com.example.liranyehudar.socialnetworkforacademic.activities;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ProgressBar;

import com.example.liranyehudar.socialnetworkforacademic.Interface.Communicator;
import com.example.liranyehudar.socialnetworkforacademic.R;
import com.example.liranyehudar.socialnetworkforacademic.Interface.RegistrationTypes;
import com.example.liranyehudar.socialnetworkforacademic.fragments.RegisterFirstPageFragment;
import com.example.liranyehudar.socialnetworkforacademic.fragments.RegisterFourthPageFragment;
import com.example.liranyehudar.socialnetworkforacademic.fragments.RegisterThirdPageFragment;
import com.example.liranyehudar.socialnetworkforacademic.logic.Student;
import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.login.LoginManager;


public class RegistrationProccessActivity extends AppCompatActivity implements Communicator {

    final static int PROGRESS_CHUNCK = 25;

    private ProgressBar progressBar;
    private int currentProgress = PROGRESS_CHUNCK;

    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    private Fragment currentFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_first_page);

        bindUI();
        int callingBy = getIntent().getIntExtra("calling-by",0);
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        switch (callingBy){
            case RegistrationTypes.BY_FACEBOOK: {
                currentProgress = PROGRESS_CHUNCK*3;
                progressBar.setProgress(currentProgress);
                Student student =(Student)getIntent().getSerializableExtra("student");
                Bundle args = new Bundle();
                args.putSerializable("student",student);
                currentFragment = new RegisterThirdPageFragment();
                currentFragment.setArguments(args);
                fragmentTransaction.add(R.id.fragments_container,currentFragment, "Third");
                fragmentTransaction.commit();
                break;
            }
            case RegistrationTypes.BY_NEW_ACCOUNT:{
                Student student = new Student();
                Bundle args = new Bundle();
                args.putSerializable("student",student);
                currentFragment = new RegisterFirstPageFragment();
                currentFragment.setArguments(args);
                fragmentTransaction.add(R.id.fragments_container,currentFragment, "first");
                fragmentTransaction.commit();
                break;
            }
        }

    }

    private void bindUI() {
        progressBar = findViewById(R.id.progress_bar);
    }

    @Override
    public void onBackPressed() {

        int count = fragmentManager.getBackStackEntryCount();

        if (count == 0) {
            super.onBackPressed();
            //additional code
        } else {
            fragmentManager.popBackStack();
            currentProgress-=PROGRESS_CHUNCK;
            progressBar.setProgress(currentProgress);
        }

    }

    @Override
    public void update() {
        currentProgress+=PROGRESS_CHUNCK;
        progressBar.setProgress(currentProgress);
    }
}
