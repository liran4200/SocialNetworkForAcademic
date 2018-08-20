package com.example.liranyehudar.socialnetworkforacademic.logic;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.liranyehudar.socialnetworkforacademic.R;
import com.example.liranyehudar.socialnetworkforacademic.activities.ProfileActivity;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class RecycleViewAdapterStudentInCourse extends RecyclerView.Adapter<RecycleViewAdapterStudentInCourse.StudentViewHolder>{
    private ArrayList<Student> allStudents;
    private Context context;
    private FirebaseAuth auth;

    public RecycleViewAdapterStudentInCourse(ArrayList<Student> all_students,Context context) {
        this.allStudents = all_students;
        this.context = context;
    }

    @NonNull
    @Override
    public StudentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_student, parent,false);
        StudentViewHolder viewHolder = new StudentViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull StudentViewHolder holder, final int position) {
        auth = FirebaseAuth.getInstance();
        final String key = auth.getUid();
        holder.theText.setText(allStudents.get(position).getFirstName()+" "+allStudents.get(position).getLastName());
        holder.theText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!allStudents.get(position).getKey().equals(key)){
                    Intent intent = new Intent(context.getApplicationContext(), ProfileActivity.class);
                    intent.putExtra("studentId",allStudents.get(position).getKey());
                    intent.putExtra("source",1005);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return allStudents.size();
    }

    public class StudentViewHolder extends RecyclerView.ViewHolder{

        TextView theText;

        public StudentViewHolder(View itemView) {
            super(itemView);

            theText = (TextView)itemView.findViewById(R.id.txt_the_text);
        }
    }
}
