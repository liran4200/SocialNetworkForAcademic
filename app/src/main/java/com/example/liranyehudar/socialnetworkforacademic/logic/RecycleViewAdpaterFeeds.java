package com.example.liranyehudar.socialnetworkforacademic.logic;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.example.liranyehudar.socialnetworkforacademic.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class RecycleViewAdpaterFeeds extends RecyclerView.Adapter<RecycleViewAdpaterFeeds.ModelFeedViewHolder>{

    ArrayList<Post> postArrayList;
    Context context;

    public RecycleViewAdpaterFeeds(ArrayList<Post> postArrayList, Context context) {
        this.postArrayList = postArrayList;
        this.context = context;
    }

    @Override
    public ModelFeedViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.listitem_posts,parent,false);
        ModelFeedViewHolder modelFeedViewHolder = new ModelFeedViewHolder(view);
        return modelFeedViewHolder;
    }

    @Override
    public void onBindViewHolder(final ModelFeedViewHolder holder, final int position) {
        Post p = postArrayList.get(position);
        holder.fullName.setText(p.getFullName());
        holder.txtStatus.setText(p.getStatus());
        String time = getTimeSincePosts(p);
        holder.txtTime.setText(time);
        holder.txtCommentsAmount.setText(p.getComments()+" comments");
        holder.txtLikesAmount.setText(p.getLikes()+"");

        final String userId = FirebaseAuth.getInstance().getUid();
        if(p.isStudentLiked(userId)){
            holder.imgViewLike.setColorFilter(Color.BLUE);
        }

        holder.layoutLikes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(),"like clicked",Toast.LENGTH_SHORT).show();
                Post p = postArrayList.get(position);
                if(p.isStudentLiked(userId)){
                    holder.imgViewLike.setColorFilter(Color.TRANSPARENT);
                    p.setLikes(p.getLikes()-1);
                    p.removeStudentsWhoLiked(userId);
                }
                else{
                    holder.imgViewLike.setColorFilter(Color.BLUE);
                    p.setLikes(p.getLikes()+1);
                    p.addStudentsWhoLiked(userId);
                }

                DatabaseReference ref = FirebaseDatabase.getInstance()
                        .getReference().child("Posts/"+p.getKey());
                ref.setValue(p);
            }
        });
    }

    private String getTimeSincePosts(Post post) {
        long diff = System.currentTimeMillis()-post.getCreatedTime();
        long convertDiff = TimeUnit.MILLISECONDS.toSeconds(diff);
        if(convertDiff < 60)
         {
            return convertDiff + " sec";
         }

        convertDiff = TimeUnit.MILLISECONDS.toMinutes(diff);
        if(convertDiff <60 ) {
            return convertDiff +" mins";
        }

        convertDiff = TimeUnit.MILLISECONDS.toHours(diff);
        if(convertDiff < 60){
            return convertDiff +" hrs";
        }

        convertDiff = TimeUnit.MILLISECONDS.toDays(diff);
        if(convertDiff <=7 ){
            return convertDiff +" days";
        }

        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd,yyyy HH:mm", Locale.ENGLISH);
        Date resultdate = new Date(post.getCreatedTime());
        return sdf.format(resultdate);
    }

    @Override
    public int getItemCount() {
        return postArrayList.size();
    }

    public class ModelFeedViewHolder extends RecyclerView.ViewHolder {

        TextView fullName;
        TextView txtLikesAmount;
        TextView txtCommentsAmount;
        TextView txtTime;
        TextView txtStatus;
        ImageView imgViewLike;
        LinearLayout layoutLikes;

        public ModelFeedViewHolder(View itemView) {
            super(itemView);

            fullName = itemView.findViewById(R.id.txt_fullname);
            txtLikesAmount =itemView.findViewById(R.id.txt_likes_amount);
            txtCommentsAmount = itemView.findViewById(R.id.txt_comment);
            txtTime = itemView.findViewById(R.id.txt_date_time);
            txtStatus = itemView.findViewById(R.id.txt_status);
            layoutLikes = itemView.findViewById(R.id.layout_likes);
            imgViewLike = itemView.findViewById(R.id.imgView_like);

        }
    }
}
