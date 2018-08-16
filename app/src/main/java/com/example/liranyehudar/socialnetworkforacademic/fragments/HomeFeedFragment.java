package com.example.liranyehudar.socialnetworkforacademic.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.liranyehudar.socialnetworkforacademic.R;
import com.example.liranyehudar.socialnetworkforacademic.activities.MainLoginActivity;
import com.example.liranyehudar.socialnetworkforacademic.activities.ProfileActivity;
import com.example.liranyehudar.socialnetworkforacademic.activities.SharePostActivty;
import com.example.liranyehudar.socialnetworkforacademic.logic.Post;
import com.example.liranyehudar.socialnetworkforacademic.logic.RecycleViewAdpaterFeeds;
import com.example.liranyehudar.socialnetworkforacademic.logic.Student;
import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.login.LoginManager;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.Collections;

import de.hdodenhof.circleimageview.CircleImageView;


public class HomeFeedFragment extends Fragment {

    public static int RESULT_SHARE_ACTIVTIY = 1;

    private RecyclerView recyclerView;
    private RecycleViewAdpaterFeeds adpaterFeeds;
    private ArrayList<Post> postArrayList;

    private CircleImageView profile;
    private TextView txtPost;
    private LinearLayout txtLogOut;
    private View view;
    private ProgressBar progressBarPosts;
    private TextView txtNoPosts;
    private CircleImageView imgProfile;

    private Student student;
    private DatabaseReference ref;
    private FirebaseDatabase database;

    public HomeFeedFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view  = inflater.inflate(R.layout.fragment_home_feed, container, false);
        bindUI();
        init();
        loadStudent();
        loadPosts();

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent intent = new Intent(v.getContext(), ProfileActivity.class);
               intent.putExtra("student",student);
               startActivity(intent);
            }
        });

        txtPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(view.getContext(), SharePostActivty.class);
                startActivityForResult(intent, RESULT_SHARE_ACTIVTIY);
            }
        });

        txtLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                if (AccessToken.getCurrentAccessToken() != null) {
                    disconnectFromFacebook();
                }
                else{
                    logOut();
                }

            }
        });

        return view;
    }

    private void init(){
        postArrayList = new ArrayList<>();
        adpaterFeeds = new RecycleViewAdpaterFeeds(postArrayList,view.getContext());
        recyclerView.setAdapter(adpaterFeeds);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
    }

    private void logOut(){
        Intent intent = new Intent(view.getContext(), MainLoginActivity.class);
        startActivity(intent);
        getActivity().finish();
    }

    private void loadStudent(){
        String userId = FirebaseAuth.getInstance().getUid();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Students/"+userId);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                student = dataSnapshot.getValue(Student.class);
                if(student.getProfileImageUrl()) {
                    downloadImage(student.getKey(),imgProfile);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getActivity().getApplicationContext(),
                        databaseError.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void downloadImage(String userId,CircleImageView profileImg) {
        StorageReference storageReference1 = FirebaseStorage.getInstance().getReferenceFromUrl("gs://socialnetworkforacademic.appspot.com/images/users/" + userId + "/image.jpg");
        Glide.with(view.getContext() /* context */).using(new FirebaseImageLoader()).load(storageReference1)
                .error(R.drawable.baseline_account_circle_black_24dp).fitCenter().into(profileImg);

    }



    private void loadPosts() {
        progressBarPosts.setVisibility(View.VISIBLE);
        database = FirebaseDatabase.getInstance();
        ref =  database.getReference("Posts");
        Query myTopPostsQuery = database.getReference("Posts")
                .orderByChild("createdTime").limitToFirst(20);


        myTopPostsQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                postArrayList.clear();
                addPosts(dataSnapshot);
                updateUI();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(view.getContext(),databaseError.getMessage(),Toast.LENGTH_LONG).show();
                progressBarPosts.setVisibility(View.INVISIBLE);
                txtNoPosts.setText("Error");
                txtNoPosts.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == getActivity().RESULT_OK) {

            if(requestCode == RESULT_SHARE_ACTIVTIY){
                String status = data.getStringExtra("post");
                sharePost(status);
            }
            if (resultCode == getActivity().RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }
    }

    private void bindUI() {
        profile = view.findViewById(R.id.profile_image);
        recyclerView = view.findViewById(R.id.recycleView_posts);
        txtPost = view.findViewById(R.id.txt_post);
        progressBarPosts = view.findViewById(R.id.prg_loading_posts);
        txtNoPosts = view.findViewById(R.id.txt_no_posts_found);
        txtLogOut = view.findViewById(R.id.layout_logout);
        imgProfile = view.findViewById(R.id.profile_image);
    }

    private void sharePost(String status) {
        Post p = new Post("0",0,0
                ,student.getFullName(),status,System.currentTimeMillis(),student.getKey());
        savePost(p);
    }

    private void savePost(Post p) {
        database = FirebaseDatabase.getInstance();
        DatabaseReference postRef =  database.getReference().child("Posts").push();
        p.setKey(postRef.getKey());
        student.addPostId(postRef.getKey());
        postRef.setValue(p);
        //save post to student
        DatabaseReference studentRef = database.getReference().child("Students/"+student.getKey());
        studentRef.setValue(student);
    }

    private void addPosts(DataSnapshot dataSnapshot) {
        for(DataSnapshot ds : dataSnapshot.getChildren()) {
            Post post = ds.getValue(Post.class);
            postArrayList.add(post);
        }

        // reverse order because firebase return data only in asending order.
        Collections.reverse(postArrayList);
    }

    private void updateUI() {
        progressBarPosts.setVisibility(View.INVISIBLE);
        txtNoPosts.setVisibility(View.INVISIBLE);
        if(postArrayList.size() == 0){
            recyclerView.setVisibility(View.INVISIBLE);
            txtNoPosts.setVisibility(View.VISIBLE);
        }
        else {
            recyclerView.setVisibility(View.VISIBLE);
            adpaterFeeds.notifyDataSetChanged();
        }
    }

    public void disconnectFromFacebook() {
        new GraphRequest(AccessToken.getCurrentAccessToken(), "/me/permissions/", null, HttpMethod.DELETE, new GraphRequest
                .Callback() {
            @Override
            public void onCompleted(GraphResponse graphResponse) {
                LoginManager.getInstance().logOut();
                logOut();
            }
        }).executeAsync();
    }
}
