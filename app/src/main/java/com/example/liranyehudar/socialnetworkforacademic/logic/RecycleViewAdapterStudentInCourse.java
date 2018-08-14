package com.example.liranyehudar.socialnetworkforacademic.logic;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.liranyehudar.socialnetworkforacademic.R;

import java.util.ArrayList;

public class RecycleViewAdapterStudentInCourse extends RecyclerView.Adapter<RecycleViewAdapterStudentInCourse.StudentViewHolder>{
    private ArrayList<Student> allStudents;

    public RecycleViewAdapterStudentInCourse(ArrayList<Student> all_students) {
        this.allStudents = all_students;
    }

    @NonNull
    @Override
    public StudentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_student, parent,false);
        StudentViewHolder viewHolder = new StudentViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull StudentViewHolder holder, int position) {
        holder.theText.setText(allStudents.get(position).getFirstName()+" "+allStudents.get(position).getLastName());
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
