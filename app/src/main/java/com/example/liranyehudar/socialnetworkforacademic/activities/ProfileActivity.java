package com.example.liranyehudar.socialnetworkforacademic.activities;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.target.Target;
import com.example.liranyehudar.socialnetworkforacademic.Interface.RegistrationTypes;
import com.example.liranyehudar.socialnetworkforacademic.R;
import com.example.liranyehudar.socialnetworkforacademic.logic.ChildCategory;
import com.example.liranyehudar.socialnetworkforacademic.logic.Course;
import com.example.liranyehudar.socialnetworkforacademic.logic.ParentCategory;
import com.example.liranyehudar.socialnetworkforacademic.logic.Student;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import iammert.com.expandablelib.ExpandableLayout;
import iammert.com.expandablelib.Section;

public class ProfileActivity extends AppCompatActivity {

    final String USER_SKILLS_SIZE = "skillSize";
    final String STUDENT = "student";
    final String SOURCE = "source";
    final int SELECT_FILE = 0;

    private boolean counerRot = false;
    private boolean edit;
    private TextView name_txt;
    private TextView country_txt;
    private TextView education_txt;
    private TextView year_txt;
    private TextView field_txt;
    private String userSkills;
    private ImageButton editProfile;
    private ImageView camera;
    private ImageView profileImg;
    private ExpandableLayout layout;
    private Student student;
    private FirebaseDatabase database;
    private DatabaseReference ref;
    private StorageReference storageReference;
    private FirebaseAuth auth;
    private String allCourses;
    private ImageView imgBack;
    private Uri selectImage;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        auth = FirebaseAuth.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();
        setUI();
        updateUI();
        readCourseFromDB();

        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()){
                    case R.id.camera_profile:
                        if (edit == false){
                            selectImage();
                            edit = true;
                        }else{
                         editImage();
                        }
                        break;
                }
            }
        });
        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this,ProfileEditActivity.class);
                intent.putExtra(STUDENT,student);
                intent.putExtra(SOURCE, RegistrationTypes.FR0M_PROFILE);
                startActivity(intent);
            }
        });

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProfileActivity.super.onBackPressed();
            }
        });

    }

    private void writeDataOfStudent(){
        database = FirebaseDatabase.getInstance();
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        DatabaseReference studentRef =  database.getReference().child("Students").child(userId);
        studentRef.setValue(student);
    }

    private void writeImageToStorage(){
        FirebaseUser user =auth.getCurrentUser();
        String userId = user.getUid();
        StorageReference storageReference1 = storageReference.child("images/users/"+userId+"/" +"image"+".jpg");
        storageReference1.putFile(selectImage).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                toastMessage("Upload Success");
            }
        });
    }


    private void toastMessage(String message){
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }

    private void readCourseFromDB(){
        final String userid = FirebaseAuth.getInstance().getUid();
        database = FirebaseDatabase.getInstance();
        ref =  database.getReference("Courses");


        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                takeAllCourse(dataSnapshot,userid);
                doLayout();
            }

            @Override
            public void onCancelled( DatabaseError databaseError) {
                Log.d("error",databaseError.getMessage());
            }
        });
    }

    private void takeAllCourse(DataSnapshot dataSnapshot,String userID){
        try {
            for (DataSnapshot dataSn : dataSnapshot.getChildren()) {
                Course course = dataSn.getValue(Course.class);
                if (course.isRegisteredStudent(userID))
                    if(allCourses == null){
                        allCourses = course.getNumber() + ": "+course.getName()+",";
                    }else{
                        allCourses += course.getNumber() + ": "+course.getName()+",";
                    }

            }
        }catch (Exception e){
            Log.e("err",e.getMessage());
        }
    }
    private void doLayout(){
        layout.setRenderer(new ExpandableLayout.Renderer<ParentCategory,ChildCategory>() {

            @Override
            public void renderParent(View view, ParentCategory parentCategory, boolean isExpanded, int parentPosition) {
                ((TextView)view.findViewById(R.id.tv_parent_name)).setText(parentCategory.getName());
                view.findViewById(R.id.arrow).setBackgroundResource(isExpanded?R.drawable.ic_arrow_up:R.drawable.ic_arrow_down);
            }

            @Override
            public void renderChild(View view, ChildCategory childCategory, int parentPosition, int childPosition) {
                ((TextView)view.findViewById(R.id.tv_child_name)).setText(childCategory.getName());

            }
        });
        layout.addSection(getSection("Courses",allCourses));
        if(userSkills.length() != 0){
            layout.addSection(getSection("Skills",userSkills));
        }



    }

    private Section<ParentCategory,ChildCategory> getSection(String parent, String child) {

        Section<ParentCategory,ChildCategory> section = new Section<>();
        ParentCategory parentCategory = new ParentCategory(parent);
        String [] all = child.split(",");
        List<ChildCategory> listChild = new ArrayList<>();
        for(int i = 0; i<all.length; i++){
            listChild.add(new ChildCategory(all[i]));
        }
        section.parent = parentCategory;
        section.children.addAll(listChild);
        return section;

    }

    private void galleryIntent(){
        Intent i = new Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        i.setType("image/*");
        startActivityForResult(i.createChooser(i,"select file"),SELECT_FILE);
    }

    private void editImage(){
        final CharSequence [] items = {"Is Ok","Inverted picture ","Picture on the right side","Picture on the left side","Choose from Library"};
        AlertDialog.Builder builder = new AlertDialog.Builder(ProfileActivity.this);
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(items[which].equals("Is Ok")){
                    dialog.dismiss();
                }else if(items[which].equals("Inverted picture ")){
                    profileImg.setRotation(profileImg.getRotation()+ 180);
                }else if(items[which].equals("Picture on the right side")){
                    profileImg.setRotation(profileImg.getRotation()+ 90);
                }else if(items[which].equals("Picture on the left side")){
                    profileImg.setRotation(profileImg.getRotation()+ 270);
                }else{
                    galleryIntent();
                }
            }
        });
        builder.show();
        writeImageToStorage();
        student.setProfileImageUrl(true);
        writeDataOfStudent();
    }


    private void selectImage(){
        final CharSequence [] items = {"Choose from Library","Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(ProfileActivity.this);
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(items[which].equals("Choose from Library")){
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
            if(requestCode == SELECT_FILE){
                final CharSequence [] items = {"Is Ok","Inverted picture ","Picture on the right side","Picture on the left side"};
                selectImage = data.getData();
                profileImg.setImageURI(selectImage);
                if(counerRot == false){
                    profileImg.setRotation(profileImg.getRotation()+ 270);
                    counerRot = true;
                }
                AlertDialog.Builder builder = new AlertDialog.Builder(ProfileActivity.this);
                builder.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(items[which].equals("Is Ok")){
                            dialog.dismiss();
                        }else if(items[which].equals("Inverted picture ")){
                            profileImg.setRotation(profileImg.getRotation()+ 180);
                        }else if(items[which].equals("Picture on the right side")){
                            profileImg.setRotation(profileImg.getRotation()+ 90);
                        }else{
                            profileImg.setRotation(profileImg.getRotation()+ 270);
                        }
                    }
                });
                builder.show();
                writeImageToStorage();
                student.setProfileImageUrl(true);
                writeDataOfStudent();

            }
        }
    }

    private void setUI(){
        name_txt = (TextView)findViewById(R.id.name_user);
        country_txt = (TextView)findViewById(R.id.city_and_country);
        education_txt = (TextView)findViewById(R.id.textView5);
        year_txt = (TextView)findViewById(R.id.textView9);
        field_txt = (TextView)findViewById(R.id.textView15);
        editProfile = (ImageButton) findViewById(R.id.imageButton3);
        profileImg = (ImageView)findViewById(R.id.user_profile_image);
        camera = (ImageView)findViewById(R.id.camera_profile);
        layout = (ExpandableLayout)findViewById(R.id.expandable_layout);
        imgBack = (ImageView)findViewById(R.id.imagView_back_home);
    }

    private void updateUI() {
        student = (Student) getIntent().getSerializableExtra(STUDENT);
        int source = getIntent().getIntExtra("source",-1);
        if(source == RegistrationTypes.FROM_SEARCHING){
            editProfile.setVisibility(View.INVISIBLE);
            camera.setVisibility(View.INVISIBLE);
        }
        name_txt.setText(student.getFirstName() + " " + student.getLastName());;
        country_txt.setText(student.getCity() + ","+student.getCountry());
        education_txt.setText("Education: "+student.getAcademic());
        year_txt.setText("Year: " + student.getYear());
        field_txt.setText("Study: "+student.getField());
        userSkills = student.getSkills();
        if(student.getProfileImageUrl() == true) {
            downloadImage();
        }
    }

    private void downloadImage(){
        String userId = student.getKey();
        StorageReference storageReference1 = FirebaseStorage.getInstance().getReferenceFromUrl("gs://socialnetworkforacademic.appspot.com/images/users/"+userId+"/image.jpg");
        Glide.with(this /* context */).using(new FirebaseImageLoader()).load(storageReference1).fitCenter().into(profileImg);
    }


}
