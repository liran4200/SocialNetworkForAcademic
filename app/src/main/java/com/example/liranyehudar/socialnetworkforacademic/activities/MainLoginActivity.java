package com.example.liranyehudar.socialnetworkforacademic.activities;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;


import com.example.liranyehudar.socialnetworkforacademic.R;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MainLoginActivity extends AppCompatActivity {

    LoginButton loginButton;
    CallbackManager callbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_login);

        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    "com.example.liranyehudar.socialnetworkforacademic",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        loginButton = findViewById(R.id.login_button);

        callbackManager = CallbackManager.Factory.create();
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Toast.makeText(getApplicationContext(),"sucess to login",Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCancel() {
                Toast.makeText(getApplicationContext(),"cancel",Toast.LENGTH_LONG).show();
            }

            @Override
            public void onError(FacebookException error) {
                Toast.makeText(getApplicationContext(),error.getMessage(),Toast.LENGTH_LONG).show();

            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }
}
