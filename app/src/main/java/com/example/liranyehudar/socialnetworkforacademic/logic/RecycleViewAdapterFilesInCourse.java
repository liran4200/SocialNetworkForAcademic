package com.example.liranyehudar.socialnetworkforacademic.logic;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.liranyehudar.socialnetworkforacademic.R;


public class RecycleViewAdapterFilesInCourse extends RecyclerView.Adapter<RecycleViewAdapterFilesInCourse.FilesViewHolder> {

    private String [] allFiles;
    private Context context;

    public RecycleViewAdapterFilesInCourse (String[] all_files, Context context) {
        this.allFiles = all_files;
        this.context = context;
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
