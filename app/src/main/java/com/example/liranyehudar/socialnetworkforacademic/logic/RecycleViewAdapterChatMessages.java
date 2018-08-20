package com.example.liranyehudar.socialnetworkforacademic.logic;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.liranyehudar.socialnetworkforacademic.R;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

public class RecycleViewAdapterChatMessages extends RecyclerView.Adapter<RecycleViewAdapterChatMessages.MessageViewHolder> {

    private ArrayList<Message> messages;
    private Context context;

    public RecycleViewAdapterChatMessages(ArrayList<Message> messages, Context context) {
        this.messages = messages;
        this.context = context;
    }

    @Override
    public MessageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.listitem_message,parent,false);
        MessageViewHolder messageViewHolder = new MessageViewHolder(view);
        return messageViewHolder;
    }

    @Override
    public void onBindViewHolder(MessageViewHolder holder, int position) {
        String userId = FirebaseAuth.getInstance().getUid();
        Message message = messages.get(position);
        String senderId = message.getSenderKey();

        downloadImage(senderId,holder.imgSender);

        SimpleDateFormat sdf =
                new SimpleDateFormat("MMM dd, HH:mm", Locale.ENGLISH);
        Date resultdate = new Date(message.getCreatedTime());

        holder.txtName.setText(message.getSenderName());
        holder.txtBody.setText(message.getBody());
        holder.txtTime.setText(sdf.format(resultdate));

        if(userId.equals(senderId)){
            holder.txtBody.setBackgroundResource(R.drawable.rounded_rectangle_sender);
        }
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    public class MessageViewHolder extends RecyclerView.ViewHolder {

        CircleImageView imgSender;
        TextView txtTime;
        TextView txtName;
        TextView txtBody;

        public MessageViewHolder(View itemView) {
            super(itemView);

            imgSender = itemView.findViewById(R.id.image_message_profile);
            txtTime   = itemView.findViewById(R.id.text_message_time);
            txtName   = itemView.findViewById(R.id.text_message_name);
            txtBody   = itemView.findViewById(R.id.text_message_body);
        }
    }

    private void downloadImage(String userId,CircleImageView profileImg) {
        StorageReference storageReference1 = FirebaseStorage.getInstance().getReferenceFromUrl("gs://socialnetworkforacademic.appspot.com/images/users/" + userId + "/image.jpg");
        Glide.with(context.getApplicationContext()/* context */).using(new FirebaseImageLoader()).load(storageReference1).diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true)
                .error(R.drawable.baseline_account_circle_black_24dp).fitCenter().into(profileImg);
    }
}
