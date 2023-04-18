package com.example.journey.JourneyApp.Dashboard;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.journey.JourneyApp.Main.Database;
import com.example.journey.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class DashboardAdapter extends RecyclerView.Adapter<DashboardAdapter.ViewHolder> {
    ArrayList<CardModel> items;


    DatabaseReference dbReference;
    FirebaseUser user;
    private FirebaseAuth userAuth;

    public DashboardAdapter(ArrayList<CardModel> items) {
        this.items = items;

    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView nameText;
        private TextView dateText;
        private TextView contentText;
        private Button share;
        private Button like;
        private Button comment;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nameText =(TextView)itemView.findViewById(R.id.info_name);
            dateText =(TextView)itemView.findViewById(R.id.info_date);
            contentText=(TextView)itemView.findViewById(R.id.info_infoText);
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

    }



    @Override
    public int getItemCount(){return items.size();}


}
