package com.example.liranyehudar.socialnetworkforacademic.logic;

import android.content.Context;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.liranyehudar.socialnetworkforacademic.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;


public class RecycleViewAdapterFilesInCourse extends RecyclerView.Adapter<RecycleViewAdapterFilesInCourse.FilesViewHolder> {

    private String [] allFiles;
    private String courseId;
    private Context context;

    public RecycleViewAdapterFilesInCourse (String[] all_files, Context context,String courseId) {
        this.allFiles = all_files;
        this.context = context;
        this.courseId = courseId;
    }

    @NonNull
    @Override
    public FilesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_file, parent,false);
        RecycleViewAdapterFilesInCourse.FilesViewHolder viewHolder = new RecycleViewAdapterFilesInCourse.FilesViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecycleViewAdapterFilesInCourse.FilesViewHolder holder, final int position) {
        holder.theText.setText(allFiles[position]);
        holder.theText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context.getApplicationContext(),allFiles[position],Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return allFiles.length;
    }

    public class FilesViewHolder extends RecyclerView.ViewHolder{

        TextView theText;

        public FilesViewHolder(View itemView) {
            super(itemView);

            theText = (TextView)itemView.findViewById(R.id.txt_the_text1);
        }
    }
}
