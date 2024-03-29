package com.example.journey.JourneyApp.Chat.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.journey.JourneyApp.Chat.Model.Chat;
import com.example.journey.JourneyApp.Main.Database;
import com.example.journey.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder>{

    private Context context;
    private List<Chat> mChat;
    private String profileImage;

//Firebase


    public static final int MSG_TYPE_LEFT = 0;
    public static final int MSG_TYPE_RIGHT = 1;

    // Constructor
    public MessageAdapter(Context context, List<Chat> mChat, String profileImage){
        this.context = context;
        this.mChat = mChat;
        this.profileImage = profileImage;
    }

    @NonNull
    @Override
    public MessageAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == MSG_TYPE_RIGHT) {
            View view = LayoutInflater.from(context).inflate(R.layout.chat_item_right, parent, false);
            return new MessageAdapter.ViewHolder(view);

        } else {
            View view = LayoutInflater.from(context).inflate(R.layout.chat_item_left, parent, false);
            return new MessageAdapter.ViewHolder(view);

        }
    }

    @Override
//public void onBindViewHolder(@NonNull MessageAdapter.ViewHolder holder, int position){
    public void onBindViewHolder(@NonNull ViewHolder holder, int position){

        Chat chat = mChat.get(position);
        holder.show_message.setText(chat.getMessage());
        holder.timeofmessage.setText(chat.getCurrenttime());


        if (profileImage == null || profileImage.equals("default")){
            holder.profile_image.setImageResource(R.drawable.person_image);
        }
        else{
//Adding Glide Library
            Glide.with(context)
                    .load(profileImage)
                    .into(holder.profile_image);
        }

        if (position == mChat.size() -1){
            if(chat.isIsseen()){
                holder.txt_seen.setText("Seen");
            } else{
                holder.txt_seen.setText("Delivered");
            }
        }
        else {
            holder.txt_seen.setVisibility(View.GONE);
        }



    }

    @Override
    public int getItemCount(){
        return mChat.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView show_message;
        public ImageView profile_image;
        public TextView txt_seen;
        public TextView timeofmessage;


        public ViewHolder(@NonNull View itemView){
            super(itemView);

            show_message = itemView.findViewById(R.id.show_message);
            profile_image = itemView.findViewById(R.id.profile_image);
            txt_seen = itemView.findViewById(R.id.txt_seen_status);
            timeofmessage = itemView.findViewById(R.id.timeofmessage);
        }
    }


    @Override
    public int getItemViewType(int position){
        FirebaseUser fuser = Database.FIREBASE_AUTH.getCurrentUser();
        if (mChat.get(position).getSender().equals(fuser.getUid())){
            return MSG_TYPE_RIGHT;
        }
        else {
            return MSG_TYPE_LEFT;
        }

    }

}