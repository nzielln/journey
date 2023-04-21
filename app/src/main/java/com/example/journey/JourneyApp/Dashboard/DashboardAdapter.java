package com.example.journey.JourneyApp.Dashboard;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.journey.JourneyApp.Main.Database;
import com.example.journey.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class DashboardAdapter extends RecyclerView.Adapter<DashboardAdapter.ViewHolder> {
    ArrayList<CardModel> items;


    DatabaseReference dbReference;
    FirebaseUser user;
    private FirebaseAuth userAuth;
    private CardClickListener listener;

    FragmentActivity ctx;

    public DashboardAdapter(ArrayList<CardModel> items, FragmentActivity context) {
        this.items = items;
        this.ctx = context;
        //this.listener = listener;
    }

    public void setListener(CardClickListener listener) {
        this.listener = listener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView nameText;
        private TextView dateText;
        private TextView contentText;
        private ImageView userImage;
        Button share;
        Button like;
        Button comment;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nameText = (TextView)itemView.findViewById(R.id.info_name);
            dateText = (TextView)itemView.findViewById(R.id.info_date);
            contentText = (TextView)itemView.findViewById(R.id.info_infoText);
            userImage = (ImageView)itemView.findViewById(R.id.info_image);
            share = (Button)itemView.findViewById(R.id.share_Btn);
            like = (Button)itemView.findViewById(R.id.heart_Btn);
            comment = (Button)itemView.findViewById(R.id.comment_Btn);
            //itemView.setOnClickListener(this);

            userImage.setOnClickListener(this);
    }

        @Override
        public void onClick(View v) {
            listener.onPositionCLicked(getAbsoluteAdapterPosition());
        }
    }
    //private final OnClickListener mOnClickListener = new CardClickListener();
    //@NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.fragment_dashboard_cards,parent, false);
       // view.setOnClickListener(mOnClickListener);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull DashboardAdapter.ViewHolder holder, int position) {
        CardModel card = items.get(position);

        holder.nameText.setText(card.getCardName());
        holder.dateText.setText(card.getCardDate());
        holder.contentText.setText(card.getCardSummary());


        if (card.getCardImage() == null) {
            holder.userImage.setImageDrawable(ContextCompat.getDrawable(ctx, R.drawable.pick_photo));
        } else {
            Glide.with(ctx).load(card.getCardImage()).into(holder.userImage);
        }

        holder.share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("share clicked","shared clicked");
                Intent sharingIntent = null;

                sharingIntent = new Intent(Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");

                String shareTitle = "title";
                String shareContent = "content";
                sharingIntent.putExtra(Intent.EXTRA_TEXT, shareContent);
                sharingIntent.putExtra(Intent.EXTRA_SUBJECT, shareTitle);
                v.getContext().startActivity(Intent.createChooser(sharingIntent, "Share using"));
            }
        });
        holder.like.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Boolean check = false;

                Log.d("like button","heart button clicked");
                if (!check) {
                    v.setBackgroundColor(Color.RED);
                }
                v.setBackgroundColor(Color.WHITE);
//                if (!check){
//                    v.setBackgroundResource(R.drawable.red_heart);
//                }
//                v.setBackgroundResource(R.drawable.heart);
            }
        });


    }


    @Override
    public int getItemCount(){return items.size();}




}
