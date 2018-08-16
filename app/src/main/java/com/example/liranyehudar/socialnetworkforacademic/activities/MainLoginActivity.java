package com.example.liranyehudar.socialnetworkforacademic.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.example.liranyehudar.socialnetworkforacademic.Interface.RegistrationTypes;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.example.liranyehudar.socialnetworkforacademic.R;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainLoginActivity extends AppCompatActivity{

    private LoginButton loginButton;
    private Button btnLogin;
    private Button btnCreateAccount;
    private EditText edtEmail;
    private EditText edtPassword;
    private ProgressDialog progressDialog;

    private CallbackManager callbackManager;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_login);
        init();
        bindUI();
        loginWithFacebook();
        btnCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RegistrationProccessActivity.class);
                intent.putExtra("calling-by", RegistrationTypes.BY_NEW_ACCOUNT);
                startActivity(intent);
            }


        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                authLogin();
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        if(firebaseAuth.getCurrentUser() != null) {
            Intent intent = new Intent(getApplicationContext(),MainActivity.class);
            startActivity(intent);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void init() {
        firebaseAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Login");
        progressDialog.setMessage("Please wait, login...");
        progressDialog.setCanceledOnTouchOutside(false);
    }

    private void loginWithFacebook() {
        // if user already logged in, go to feed activity
        if(AccessToken.getCurrentAccessToken() != null) {
            Intent intent = new Intent(getApplicationContext(),MainActivity.class);
            startActivity(intent);
        }

        // request permissions from facebook
        loginButton.setReadPermissions("email,public_profile,user_location");
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                handleFacebookAccessToken(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
                Toast.makeText(getApplicationContext(),
                        "Error to login with facebook,please try again",Toast.LENGTH_LONG);
            }

            @Override
            public void onError(FacebookException error) {
                Toast.makeText(getApplicationContext(),error.getMessage(),Toast.LENGTH_LONG);
            }
        });
    }

    public void bindUI() {
        loginButton = findViewById(R.id.login_button);
        btnLogin = findViewById(R.id.button_login);
        btnCreateAccount = findViewById(R.id.button_create_account);
        edtEmail = findViewById(R.id.edt_email);
        edtPassword = findViewById(R.id.edt_password);
        callbackManager = CallbackManager.Factory.create();
    }

    public void authLogin() {
        String email = edtEmail.getText().toString();
        String pass =  edtPassword.getText().toString();
        if(email.length() == 0 || pass.length() == 0) {
            Toast.makeText(getApplicationContext(),"Please fill all the fields",Toast.LENGTH_SHORT).show();
            return;
        }
        progressDialog.show();
        // check authentication
        firebaseAuth.signInWithEmailAndPassword(email,pass).
                addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(Task<AuthResult> task) {
                if(task.isSuccessful()) {
                    //mainactivty
                    progressDialog.cancel();
                    Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                    startActivity(intent);
                }else{
                    //handle error
                    Toast.makeText(getApplicationContext(),task.getException().getMessage(),Toast.LENGTH_LONG).show();
                    progressDialog.cancel();
                }
            }
        });
    }

    private void handleFacebookAccessToken(final AccessToken token) {
        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                                // check if this is a new user for create account
                                if (task.getResult().getAdditionalUserInfo().isNewUser()) {
                                    Intent intent = new Intent(getApplicationContext(), RegistrationProccessActivity.class);
                                    intent.putExtra("calling-by", RegistrationTypes.BY_FACEBOOK);
                                    startActivity(intent);
                                }
                                else {
                                    // user already has account
                                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                    startActivity(intent);
                                }
                        }
                        else{
                            // If sign in fails, display a message to the user.
                            Toast.makeText(getApplicationContext(), task.getException().getMessage(),
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
