package com.example.journey.JourneyApp.Dashboard;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

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

    FragmentActivity ctx;

    public DashboardAdapter(ArrayList<CardModel> items, FragmentActivity context) {
        this.items = items;
        this.ctx = context;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView nameText;
        private TextView dateText;
        private TextView contentText;
        private ImageView userImage;
        private Button share;
        private Button like;
        private Button comment;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nameText =(TextView)itemView.findViewById(R.id.info_name);
            dateText =(TextView)itemView.findViewById(R.id.info_date);
            contentText=(TextView)itemView.findViewById(R.id.info_infoText);
            userImage=(ImageView)itemView.findViewById(R.id.info_image);
            //userImage =(ImageView).findViewById(R.id.dash_image);
    }

}

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.fragment_dashboard_cards,parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull DashboardAdapter.ViewHolder holder, int position) {
        CardModel card = items.get(position);

        holder.nameText.setText(card.getCardName());
        holder.dateText.setText(card.getCardDate());
        holder.contentText.setText(card.getCardSummary());

        if (card.getCardImage() == null)
        {
            holder.userImage.setImageDrawable(ContextCompat.getDrawable(ctx, R.drawable.pick_photo));
        } else {
            //StorageReference profileURL = Database.DB_STORAGE_REFERENCE.child(card.getCardImage());
            Log.d("debug", ctx.toString());
            Log.d("debug", card.getCardImage().toString());
            Log.d("debug", holder.userImage.toString());

            Glide.with(ctx).load(card.getCardImage()).into(holder.userImage);
        }
    }



    @Override
    public int getItemCount(){return items.size();}


}
