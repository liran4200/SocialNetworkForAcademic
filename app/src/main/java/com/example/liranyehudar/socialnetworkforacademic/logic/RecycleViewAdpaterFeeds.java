package com.example.liranyehudar.socialnetworkforacademic.logic;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.example.liranyehudar.socialnetworkforacademic.R;

import java.util.ArrayList;

public class RecycleViewAdpaterFeeds extends RecyclerView.Adapter<RecycleViewAdpaterFeeds.ModelFeedViewHolder>{

    ArrayList<ModelFeed> modelFeedArrayList;
    Context context;

    public RecycleViewAdpaterFeeds(ArrayList<ModelFeed> modelFeedArrayList, Context context) {
        this.modelFeedArrayList = modelFeedArrayList;
        this.context = context;
    }

    @Override
    public ModelFeedViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.listitem_posts,parent,false);
        ModelFeedViewHolder modelFeedViewHolder = new ModelFeedViewHolder(view);
        return modelFeedViewHolder;
    }

    @Override
    public void onBindViewHolder( ModelFeedViewHolder holder, int position) {

        holder.fullName.setText(modelFeedArrayList.get(position).getFullName());
        holder.txtStatus.setText(modelFeedArrayList.get(position).getStatus());
        holder.txtTime.setText(modelFeedArrayList.get(position).getTime());
        holder.txtCommentsAmount.setText(modelFeedArrayList.get(position).getAmountOfComments()+" comments");
        holder.txtLikesAmount.setText(modelFeedArrayList.get(position).getAmountOfLikes()+"");
    }

    @Override
    public int getItemCount() {
        return modelFeedArrayList.size();
    }

    public class ModelFeedViewHolder extends RecyclerView.ViewHolder {

        TextView fullName;
        TextView txtLikesAmount;
        TextView txtCommentsAmount;
        TextView txtTime;
        TextView txtStatus;

        public ModelFeedViewHolder(View itemView) {
            super(itemView);

            fullName = itemView.findViewById(R.id.txt_fullname);
            txtLikesAmount =itemView.findViewById(R.id.txt_likes_amount);
            txtCommentsAmount = itemView.findViewById(R.id.txt_comment);
            txtTime = itemView.findViewById(R.id.txt_date_time);
            txtStatus = itemView.findViewById(R.id.txt_status);

        }
    }
}
