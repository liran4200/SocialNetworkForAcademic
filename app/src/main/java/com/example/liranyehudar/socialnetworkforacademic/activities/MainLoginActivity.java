package com.example.liranyehudar.socialnetworkforacademic.activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.example.liranyehudar.socialnetworkforacademic.Interface.RegistrationTypes;
import com.example.liranyehudar.socialnetworkforacademic.logic.Student;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.example.liranyehudar.socialnetworkforacademic.R;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.json.JSONException;
import org.json.JSONObject;

public class MainLoginActivity extends AppCompatActivity{

    private LoginButton loginButton;
    private Button btnLogin;
    private Button btnCreateAccount;
    private EditText edtEmail;
    private EditText edtPassword;

    private CallbackManager callbackManager;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_login);

        firebaseAuth = FirebaseAuth.getInstance();
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
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        if(currentUser != null) {
           // Intent intent = new Intent(getApplicationContext(),MainActivity.class);
           // startActivity(intent);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        disconnectFromFacebook();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void loginWithFacebook() {
        if(AccessToken.getCurrentAccessToken() != null) {
            // go to feed activity.
            Toast.makeText(this,"Feed Activity",Toast.LENGTH_LONG).show();
            Intent intent = new Intent(getApplicationContext(),MainActivity.class);
            startActivity(intent);
        }

        loginButton.setReadPermissions("email");
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                // check if already regiter user, if he register user go to feed activty,
                // else go to register activity to continue register from third page.

                // case 1: go to feed activty
                handleFacebookAccessToken(loginResult.getAccessToken());
                // case 2: continue register.

            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {
                Log.e("facebookError",error.getMessage());
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

    public Student getData(JSONObject object) {
        Student student = new Student();
        try {
         //   student.setId(object.getString("id"));
            student.setFirstName(object.getString("first_name"));
            student.setLastName(object.getString("last_name"));
         //   student.setEmail(object.getString("email"));
        //    student.setProfileImageUrl(object.getJSONObject("picture")
         //           .getJSONObject("data").getString("url"));
        } catch (JSONException e) {
            Log.e("JsonError",e.getMessage());
        }
        return student;
    }

    public void disconnectFromFacebook() {

        if (AccessToken.getCurrentAccessToken() == null) {
            return; // already logged out
        }

        new GraphRequest(AccessToken.getCurrentAccessToken(), "/me/permissions/", null, HttpMethod.DELETE, new GraphRequest
                .Callback() {
            @Override
            public void onCompleted(GraphResponse graphResponse) {

                LoginManager.getInstance().logOut();

            }
        }).executeAsync();
    }

    public void authLogin() {
        String email = edtEmail.getText().toString();
        String pass =  edtPassword.getText().toString();
        if(email.length() == 0 || pass.length() == 0) {
            Toast.makeText(getApplicationContext(),"Please fill all the fields",Toast.LENGTH_SHORT).show();
            return;
        }
        firebaseAuth.signInWithEmailAndPassword(email,pass).
                addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(Task<AuthResult> task) {
                if(task.isSuccessful()) {
                    //mainactivty
                    Intent intent = new Intent(getApplicationContext(),ProfileEditActivity.class);
                    startActivity(intent);
                }else{
                    //handle error
                    Toast.makeText(getApplicationContext(),task.getException().getMessage(),Toast.LENGTH_LONG).show();
                    Log.e("err",task.getException().getMessage());
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
                            if (task.getResult().getAdditionalUserInfo().isNewUser()) {
                                //registaration
                                //loadUserDataFromFacebook(token);
                                Student student = new Student();
                                Intent intent = new Intent(getApplicationContext(), RegistrationProccessActivity.class);
                                intent.putExtra("calling-by",RegistrationTypes.BY_FACEBOOK);
                                intent.putExtra("student",student);
                                startActivity(intent);
                            }
                            else {
                                Log.d("msg", "signInWithCredential:success");
                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                startActivity(intent);
                            }
                        }
                        else{

                                // If sign in fails, display a message to the user.
                                Log.w("err", "signInWithCredential:failure", task.getException());
                                Toast.makeText(getApplicationContext(), "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();

                        }
                    }
                });
    }

    private void loadUserDataFromFacebook(AccessToken token) {
        GraphRequest graphRequest = GraphRequest.newMeRequest(
                token, new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        Student student = getData(object);
                        Intent intent = new Intent(getApplicationContext(), RegistrationProccessActivity.class);
                        intent.putExtra("calling-by",RegistrationTypes.BY_FACEBOOK);
                        intent.putExtra("student",student);
                        startActivity(intent);
                    }
                });

        Bundle parameters = new Bundle();
        parameters.putString("fields","id,first_name,last_name,email,picture.type(normal)");
        graphRequest.setParameters(parameters);
        graphRequest.executeAsync();

    }
}
