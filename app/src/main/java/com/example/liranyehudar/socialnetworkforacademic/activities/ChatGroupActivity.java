package com.example.liranyehudar.socialnetworkforacademic.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.liranyehudar.socialnetworkforacademic.R;
import com.example.liranyehudar.socialnetworkforacademic.logic.Message;
import com.example.liranyehudar.socialnetworkforacademic.logic.RecycleViewAdapterChatMessages;
import com.example.liranyehudar.socialnetworkforacademic.logic.Student;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ChatGroupActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecycleViewAdapterChatMessages recycleViewAdapterChatMessages;
    private ArrayList<Message> messages;
    private Button btnSend;
    private EditText edtMessage;
    private Student student;
    private String courseId;

    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_group);

        bindUI();
        init();
        loadStudent();

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edtMessage.getText().toString().trim().length() != 0) {
                    Message m = new Message("", edtMessage.getText().toString().trim(),
                            student.getFullName(), student.getKey());
                    messages.add(m);
                    // save message to firebase
                    DatabaseReference ref = database.getReference().child("ChatGroups").child(courseId).child("messages").push();
                    m.setKey(ref.getKey());
                    ref.setValue(m);
                    edtMessage.setText("");
                }
            }
        });
    }

    private void loadStudent(){
        String userId = FirebaseAuth.getInstance().getUid();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Students/"+userId);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                student = dataSnapshot.getValue(Student.class);
                loadMessages();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(),databaseError.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void loadMessages(){
        database = FirebaseDatabase.getInstance();
        DatabaseReference ref =  database.getReference("ChatGroups/"+courseId+"/messages");
        Query myTopPostsQuery = ref.orderByChild("createdTime").limitToFirst(25);
        myTopPostsQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                messages.clear();
                addMessages(dataSnapshot);
                updateUI();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(),databaseError.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
    }

    private void updateUI() {
        if(messages.size() == 0){

        }
        else {
            recycleViewAdapterChatMessages.notifyDataSetChanged();
        }
    }

    private void addMessages(DataSnapshot dataSnapshot) {
        for(DataSnapshot ds : dataSnapshot.getChildren()){
            Message m = ds.getValue(Message.class);
            messages.add(m);
        }
    }

    private void init() {
        courseId = getIntent().getStringExtra("courseId");
        messages = new ArrayList<>();

        recycleViewAdapterChatMessages =
                new RecycleViewAdapterChatMessages(messages,this);
        recyclerView.setAdapter(recycleViewAdapterChatMessages);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        firebaseAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
    }

    private void bindUI() {
        recyclerView = findViewById(R.id.reyclerview_message_list);
        btnSend = findViewById(R.id.button_chatbox_send);
        edtMessage = findViewById(R.id.edittext_chatbox);
    }
}
