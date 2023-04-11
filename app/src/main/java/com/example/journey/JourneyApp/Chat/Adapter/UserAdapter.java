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
    private List<Users> mUsers;

    private boolean isChat;



    // Constructor
    public UserAdapter(Context context, List<Users> mUsers, boolean isChat){
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

        Users user = mUsers.get(position);
        holder.username.setText(user.getUsername());

        if (user.getImageURL().equals("default")){
            holder.imageView.setImageResource(R.mipmap.ic_launcher);
        }else{
            //Adding Glide Library
            Glide.with(context)
                    .load(user.getImageURL())
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
                i.putExtra("userid", user.getId());
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
