package com.example.liranyehudar.socialnetworkforacademic.logic;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.liranyehudar.socialnetworkforacademic.R;

import java.util.ArrayList;
import java.util.HashSet;

public class RecycleViewAdapterCoursesSelection extends RecyclerView.Adapter<RecycleViewAdapterCoursesSelection.ViewHolder> {

    private ArrayList<Course> courses;
    private HashSet<Course> setSelected;
    private Context context;

    public RecycleViewAdapterCoursesSelection(ArrayList<Course> courses, HashSet<Course> setSelectedCourses, Context context) {
        this.courses = courses;
        this.setSelected = setSelectedCourses;
        this.context = context;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.listitem_courses_selection, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        Log.d("Adapter","onBindViewHolder called");
        holder.heading.setText(courses.get(position).heading());
        holder.description.setText(courses.get(position).description());
        holder.takesPlaceOn.setText(courses.get(position).takesPlaceOn());
        holder.selection.setChecked(false);

        holder.selection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.selection.setChecked(holder.selection.isChecked());
                if(holder.selection.isChecked()) {
                    Course selected = courses.get(position);
                    if(setSelected.size() == 0)
                        setSelected.add(selected);
                    else {
                        String overLapping = isOverLappingToSelectedCourses(courses.get(position));
                        if(overLapping.equals(""))
                            setSelected.add(selected);
                        else{
                            holder.selection.setChecked(false);
                            Toast.makeText(context, "OverLapping to "+overLapping, Toast.LENGTH_LONG).show();
                        }
                    }
                }
                else{
                    setSelected.remove(courses.get(position));
                }
            }
        });

    }

    private String isOverLappingToSelectedCourses(Course c) {
        for(Course course: setSelected) {
            if(course.isOverLapping(c))
                return course.getName();
        }
        return "";
    }

    @Override
    public int getItemCount() {
        return courses.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView heading;
        TextView description;
        TextView takesPlaceOn;
        CheckBox selection;
        LinearLayout parent;

        public ViewHolder(View itemView) {
            super(itemView);
            heading = itemView.findViewById(R.id.txt_heading);
            description = itemView.findViewById(R.id.txt_description);
            takesPlaceOn = itemView.findViewById(R.id.txt_takes_place);
            selection = itemView.findViewById(R.id.selected);
            parent = itemView.findViewById(R.id.parent_layout);
        }
    }
}
