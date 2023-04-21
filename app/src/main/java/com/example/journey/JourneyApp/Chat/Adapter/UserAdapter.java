package com.example.journey.JourneyApp.Chat.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.journey.JourneyApp.Chat.MessageActivity;
//import com.example.journey.JourneyApp.Chat.Model.Users;
import com.example.journey.JourneyApp.Profile.Models.UserModel;
import com.example.journey.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder>{

    private Context context;
    private List<UserModel> mUsers;
    //private boolean isChat;

    //String defaultImage = "https://firebasestorage.googleapis.com/v0/b/journey-c6761.appspot.com/o/profile_2YunYJpvCPd0qv9CB5oIrqvT5h92_cc68d573-29e2-497d-9995-4d4c74ffe4bf?alt=media&token=f9447699-5e6f-40d6-82c9-fcf4f6011aac";
    //String defaultImage = "https://firebasestorage.googleapis.com/v0/b/journey-c6761.appspot.com/o/profile_q3XSGj0VfqO9iseYRQ2cL9jN2K33_d1357f54-84b5-4a44-a6fe-6da239b6c448?alt=media&token=6702bac9-9131-4e2c-8911-2ee45e5b1cf4";

    // Constructor
    public UserAdapter(Context context, List<UserModel> mUsers, boolean isChat){
        this.context = context;
        this.mUsers = mUsers;
        //this.isChat = isChat;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int ViewType){
        View view = LayoutInflater.from(context).inflate(R.layout.user_item, parent, false);
        return new UserAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position){

        UserModel userModel = mUsers.get(position);
        String fullName = userModel.getFirstName() + " " + userModel.getLastName();
        //holder.username.setText(userModel.getFirstName());
        holder.username.setText(fullName);

        if (userModel.getProfileImage() != null){
        //if (userModel.getProfileImage() == null){ //add this line back if profile image doesnt work
            //holder.imageView.setImageResource(R.drawable.person_image); // same as above. replace if not working
            Glide.with(context)
                    .load(userModel.getProfileImage())
                    .placeholder(R.drawable.person_image) // added new line. Remove if not needed
                    .into(holder.imageView);


        }else{

            holder.imageView.setImageResource(R.drawable.person_image);
            //Adding Glide Library
            /*Glide.with(context)
                    .load(userModel.getProfileImage())
                    .into(holder.imageView);

             */
        }

        String lastMessage = userModel.getLastMessage();
        Long lastMessageTimeStamp = userModel.getLastMessageTimeStamp();
        if (lastMessage != null) {
            holder.lastMessage.setText(lastMessage);
        } else {
            holder.lastMessage.setText("");
        }


        /*

        //Status Check
        if (isChat) {
            if (user.getStatus().equals("online")) {
                holder.imageViewON.setVisibility(View.VISIBLE);
                holder.imageViewOFF.setVisibility(View.GONE);
            } else {
                holder.imageViewON.setVisibility(View.GONE);
                holder.imageViewOFF.setVisibility(View.VISIBLE);
            }
        }

        else{
            holder.imageViewON.setVisibility(View.GONE);
            holder.imageViewOFF.setVisibility(View.GONE);
        }

         */

        holder.itemView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent i = new Intent(context, MessageActivity.class);
                i.putExtra("userid", userModel.getUserID());
                context.startActivity(i);
            }
        });

    }

    private String getFormattedTime(long lastMessageTimeStamp) {
        Date date = new Date(lastMessageTimeStamp);
        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy hh:mm a", Locale.getDefault());
        return sdf.format(date);
    }

    @Override
    public int getItemCount(){
        return mUsers.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView username;
        public ImageView imageView;
        public TextView lastMessage;
        public TextView lastMessageTimeStamp;

       // public ImageView imageViewON;
        //public ImageView imageViewOFF;

        public ViewHolder(@NonNull View itemView){
            super(itemView);

            username = itemView.findViewById(R.id.userName);
            imageView = itemView.findViewById(R.id.imageView);
            lastMessage = itemView.findViewById(R.id.lastMessage);
            lastMessageTimeStamp = itemView.findViewById(R.id.lastMessageTimestamp);
            //imageViewON = itemView.findViewById(R.id.statusimageON);
            //imageViewOFF = itemView.findViewById(R.id.statusimageOFF);

        }

    }

}
