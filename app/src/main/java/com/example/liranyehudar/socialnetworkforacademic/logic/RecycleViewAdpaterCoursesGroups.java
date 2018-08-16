package com.example.liranyehudar.socialnetworkforacademic.logic;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.liranyehudar.socialnetworkforacademic.R;
import com.example.liranyehudar.socialnetworkforacademic.activities.ChatGroupActivity;
import com.example.liranyehudar.socialnetworkforacademic.activities.CourseActivity;
import com.example.liranyehudar.socialnetworkforacademic.activities.MainActivity;
import com.example.liranyehudar.socialnetworkforacademic.activities.ProfileActivity;

import java.util.ArrayList;

public class RecycleViewAdpaterCoursesGroups extends
                RecyclerView.Adapter<RecycleViewAdpaterCoursesGroups.ViewHolder>{

    private ArrayList<Course> courses;
    private Context context;

    public RecycleViewAdpaterCoursesGroups(ArrayList<Course> courses, Context context) {
        this.courses = courses;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.listitem_course_group, parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.txtCourse.setText(courses.get(position).heading());

        holder.imgCourseGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("clicked","onClickGroup");
                Toast.makeText(v.getContext(),"OPEN GROUP PAGE",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context.getApplicationContext(), CourseActivity.class);
                intent.putExtra("courseId",courses.get(position).getKey());
                context.startActivity(intent);
            }
        });

        holder.imgMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(),"OPEN MESSAGE",Toast.LENGTH_SHORT).show();
                Log.d("clicked","onClickmessage");
                Intent intent = new Intent(context.getApplicationContext(), ChatGroupActivity.class);
                intent.putExtra("courseId",courses.get(position).getKey());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return courses.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtCourse;
        ImageView imgMessage;
        ImageView imgCourseGroup;

        public ViewHolder(View itemView) {
            super(itemView);
            txtCourse = itemView.findViewById(R.id.txt_course);
            imgCourseGroup = itemView.findViewById(R.id.nav_group_course);
            imgMessage = itemView.findViewById(R.id.message_group);
        }
    }
}
