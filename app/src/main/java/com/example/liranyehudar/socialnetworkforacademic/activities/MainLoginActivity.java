package com.example.liranyehudar.socialnetworkforacademic.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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

import org.json.JSONException;
import org.json.JSONObject;

public class MainLoginActivity extends AppCompatActivity{

    LoginButton loginButton;
    Button btnCreateAccount;
    CallbackManager callbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_login);

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

    }

    private void loginWithFacebook() {
        if(AccessToken.getCurrentAccessToken() != null) {
            // go to feed activity.
            Toast.makeText(this,"Feed Activity",Toast.LENGTH_LONG).show();
        }

        loginButton.setReadPermissions("email");
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                String userId = loginResult.getAccessToken().getUserId();
                // check if already regiter user, if he register user go to feed activty,
                // else go to register activity to continue register from third page.

                // case 1: go to feed activty

                // case 2: continue register.
                GraphRequest graphRequest = GraphRequest.newMeRequest(
                        loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
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
                parameters.putString("fields","id,first_name,last_name,email");
                graphRequest.setParameters(parameters);
                graphRequest.executeAsync();
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void bindUI() {
        loginButton = findViewById(R.id.login_button);
        btnCreateAccount = findViewById(R.id.button_create_account);
        callbackManager = CallbackManager.Factory.create();
    }

    public Student getData(JSONObject object) {
        Student student = new Student();
        try {
            student.setId(object.getString("id"));
            student.setFirstName(object.getString("first_name"));
            student.setLastName(object.getString("last_name"));
            student.setEmail(object.getString("email"));
        } catch (JSONException e) {
            Log.e("JsonError",e.getMessage());
        }
        return student;
    }

    @Override
    protected void onResume() {
        super.onResume();
        disconnectFromFacebook();
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
}
