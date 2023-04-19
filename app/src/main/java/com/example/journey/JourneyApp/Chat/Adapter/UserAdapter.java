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
import com.example.journey.JourneyApp.Chat.Model.Users;
import com.example.journey.JourneyApp.Profile.Models.UserModel;
import com.example.journey.R;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder>{

    private Context context;
    private List<UserModel> mUsers;
    private boolean isChat;

    //String defaultImage = "https://firebasestorage.googleapis.com/v0/b/journey-c6761.appspot.com/o/profile_2YunYJpvCPd0qv9CB5oIrqvT5h92_cc68d573-29e2-497d-9995-4d4c74ffe4bf?alt=media&token=f9447699-5e6f-40d6-82c9-fcf4f6011aac";
    String defaultImage = "https://firebasestorage.googleapis.com/v0/b/journey-c6761.appspot.com/o/profile_q3XSGj0VfqO9iseYRQ2cL9jN2K33_d1357f54-84b5-4a44-a6fe-6da239b6c448?alt=media&token=6702bac9-9131-4e2c-8911-2ee45e5b1cf4";

    // Constructor
    public UserAdapter(Context context, List<UserModel> mUsers, boolean isChat){
        this.context = context;
        this.mUsers = mUsers;
        this.isChat = isChat;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int ViewType){
        View view = LayoutInflater.from(context).inflate(R.layout.user_item, parent, false);
        return new UserAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position){

        UserModel user = mUsers.get(position);
        holder.username.setText(user.getUsername());

        //if (user.getProfileImage().equals("default")){
        if (user.getProfileImage() == null){
            holder.imageView.setImageResource(R.drawable.person_image);
            /*Glide.with(context)
                    .load(defaultImage)
                    .into(holder.imageView);

             */
        }else{
            //Adding Glide Library
            Glide.with(context)
                    .load(user.getProfileImage())
                    .into(holder.imageView);
        }

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

        holder.itemView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent i = new Intent(context, MessageActivity.class);
                i.putExtra("userid", user.getUserID());
                context.startActivity(i);
            }
        });

    }

    @Override
    public int getItemCount(){
        return mUsers.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView username;
        public ImageView imageView;
        public ImageView imageViewON;
        public ImageView imageViewOFF;

        public ViewHolder(@NonNull View itemView){
            super(itemView);

            username = itemView.findViewById(R.id.userName);
            imageView = itemView.findViewById(R.id.imageView);
            imageViewON = itemView.findViewById(R.id.statusimageON);
            imageViewOFF = itemView.findViewById(R.id.statusimageOFF);

        }

    }

}
