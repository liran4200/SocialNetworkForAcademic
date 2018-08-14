package com.example.liranyehudar.socialnetworkforacademic.logic;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.liranyehudar.socialnetworkforacademic.Interface.RegistrationTypes;
import com.example.liranyehudar.socialnetworkforacademic.R;
import com.example.liranyehudar.socialnetworkforacademic.activities.ProfileActivity;

import java.util.ArrayList;

public class RecycleViewAdapterSearching extends
        RecyclerView.Adapter<RecycleViewAdapterSearching.ViewHolderStudent>{

    private Context context;
    private ArrayList<Student> students;

    public RecycleViewAdapterSearching(Context context, ArrayList<Student> students) {
        this.context = context;
        this.students = students;
    }

    @Override
    public ViewHolderStudent onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.listitem_search_student,parent,false);
        ViewHolderStudent viewHolderStudent = new ViewHolderStudent(view);
        return viewHolderStudent;
    }

    @Override
    public void onBindViewHolder(ViewHolderStudent holder, final int position) {
        final Student student = students.get(position);
        holder.txtStudentName.setText(student.getFirstName()+" "+student.getLastName());
        holder.txtDescription.setText(student.getYear()+" year "+student.getField()+" at "+student.getAcademic());
        //image...

        holder.layoutStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context.getApplicationContext(),students.get(position).getFirstName(),Toast.LENGTH_LONG).show();
                Intent intent = new Intent(context.getApplicationContext(), ProfileActivity.class);
                intent.putExtra("student",students.get(position));
                intent.putExtra("source", RegistrationTypes.FROM_SEARCHING);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return students.size();
    }

    class ViewHolderStudent extends RecyclerView.ViewHolder {

        private ImageView imgProfile;
        private TextView txtStudentName;
        private TextView txtDescription;
        private LinearLayout layoutStudent;

        public ViewHolderStudent(View itemView) {
            super(itemView);
            imgProfile = itemView.findViewById(R.id.imageView_search_profile);
            txtStudentName  = itemView.findViewById(R.id.txt_item_student_name);
            txtDescription = itemView.findViewById(R.id.txt_item_student_desc);
            layoutStudent = itemView.findViewById(R.id.linearLayout_listitem_student);
        }
    }
}
