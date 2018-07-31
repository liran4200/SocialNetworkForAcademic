package com.example.liranyehudar.socialnetworkforacademic.activities;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.liranyehudar.socialnetworkforacademic.R;

public class ProfileActivity extends AppCompatActivity {

    final String USER_NAME = "name";
    final String USER_YEAR = "year";
    final String USER_COUNTRY = "country";
    final String USER_EDUCATION = "education";
    final String USER_CITY = "city";
    final int REQUEST_CAMERA = 1;
    final int SELECT_FILE = 0;

    private TextView name_txt;
    private TextView country_txt;
    private TextView city_txt;
    private TextView education_txt;
    private TextView year_txt;
    private String userName;
    private String userCountry;
    private String userCity;
    private String userEducation;
    private String userYear;
    private ImageButton editProfile;
    private ImageView camera;
    private ImageView profileImg;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        setUI();
        updateUI();

        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()){
                    case R.id.camera_profile:
                        selectImage();
                        break;
                }
            }
        });
        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this,ProfileEditActivity.class);
                intent.putExtra(USER_NAME,userName);
                intent.putExtra(USER_COUNTRY,userCountry);
                intent.putExtra(USER_CITY,userCity);
                intent.putExtra(USER_EDUCATION,userEducation);
                intent.putExtra(USER_YEAR,userYear);
                startActivity(intent);
            }
        });

    }

    private void galleryIntent(){
        Intent i = new Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        i.setType("image/*");
        startActivityForResult(i.createChooser(i,"select file"),SELECT_FILE);
    }

    private void cameraIntent(){
        Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(i,REQUEST_CAMERA);
    }

    private void selectImage(){
        final CharSequence [] items = {"Take Photo","Choose from Library","Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(ProfileActivity.this);
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(items[which].equals("Take Photo")){
                    cameraIntent();
                }else if(items[which].equals("Choose from Library")){
                    galleryIntent();
                }else if(items[which].equals("Cancel")){
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }
    @Override
    public void onActivityResult(int requestCode,int resultCode,Intent data){
        super.onActivityResult(requestCode,resultCode,data);

        if(resultCode == Activity.RESULT_OK){
            if(requestCode == REQUEST_CAMERA){
                Bundle bundle = data.getExtras();
                Bitmap bmp = (Bitmap)bundle.get("data");
                profileImg.setImageBitmap(bmp);
            }else if(requestCode == SELECT_FILE){
                Uri selectImage = data.getData();
                profileImg.setImageURI(selectImage);
            }
        }
    }

    private void setUI(){
        name_txt = (TextView)findViewById(R.id.name_user);
        country_txt = (TextView)findViewById(R.id.city_and_country);
        city_txt = (TextView)findViewById(R.id.city_and_country);
        education_txt = (TextView)findViewById(R.id.textView5);
        year_txt = (TextView)findViewById(R.id.textView9);
        editProfile = (ImageButton) findViewById(R.id.imageButton3);
        profileImg = (ImageView)findViewById(R.id.user_profile_image);
        camera = (ImageView)findViewById(R.id.camera_profile);
    }

    private void updateUI() {
        userName = getIntent().getStringExtra(USER_NAME).toString();
        name_txt.setText(userName);
        userCountry = getIntent().getStringExtra(USER_COUNTRY).toString();
        userCity = getIntent().getStringExtra(USER_CITY).toString();
        country_txt.setText(userCity + ","+userCountry);
        userEducation = getIntent().getStringExtra(USER_EDUCATION).toString();
        education_txt.setText("Education: "+userEducation);
        userYear = getIntent().getStringExtra(USER_YEAR).toString();
        year_txt.setText("Year: " + userYear);
    }
}
