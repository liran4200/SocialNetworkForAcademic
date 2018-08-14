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
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.liranyehudar.socialnetworkforacademic.R;
import com.example.liranyehudar.socialnetworkforacademic.logic.ChildCategory;
import com.example.liranyehudar.socialnetworkforacademic.logic.Course;
import com.example.liranyehudar.socialnetworkforacademic.logic.ParentCategory;
import com.example.liranyehudar.socialnetworkforacademic.logic.Student;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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
    private int skillsSize;
    private ImageButton editProfile;
    private ImageView camera;
    private ImageView profileImg;
    private ExpandableLayout layout;
    private Student student;
    private FirebaseDatabase database;
    private DatabaseReference ref;
    private String allCourses;
    private String [] theCourses;
    private int sizeCourses;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

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
                intent.putExtra(SOURCE,1002);
                startActivity(intent);
            }
        });

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
        checkSizeCourse();
        layout.addSection(getSection("Courses",allCourses,sizeCourses));
        layout.addSection(getSection("Skills",userSkills,skillsSize));


    }

    private void checkSizeCourse(){
        theCourses = allCourses.split(",");
        sizeCourses =theCourses.length;

    }

    private Section<ParentCategory,ChildCategory> getSection(String parent, String child,int size) {

        Section<ParentCategory,ChildCategory> section = new Section<>();
        ParentCategory parentCategory = new ParentCategory(parent);
        String [] all = child.split(",");
        List<ChildCategory> listChild = new ArrayList<>();
        for(int i = 0; i<size; i++){
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
                Uri selectImage = data.getData();
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
    }

    private void updateUI() {
        student = (Student) getIntent().getSerializableExtra(STUDENT);
        name_txt.setText(student.getFirstName() + " " + student.getLastName());;
        country_txt.setText(student.getCity() + ","+student.getCountry());
        education_txt.setText("Education: "+student.getAcademic());
        year_txt.setText("Year: " + student.getYear());
        field_txt.setText("Study: "+student.getField());
        skillsSize = getIntent().getIntExtra(USER_SKILLS_SIZE,-1);
        userSkills = student.getSkills();
    }


}
