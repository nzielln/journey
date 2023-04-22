package com.example.journey.JourneyApp.Dashboard;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.journey.R;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class CommentsAdapter extends RecyclerView.Adapter<CommentsAdapter.ViewHolder> {

    ArrayList<CommentsModel> commentItems;
    DatabaseReference dbReference;
    FirebaseUser currentUser;

    public CommentsAdapter(ArrayList<CommentsModel> commentItems){
        this.commentItems = commentItems;
    }

    public class ViewHolder extends RecyclerView.ViewHolder){
        private TextView commenterUserName;
        private TextView commentDate;
        private TextView commentContext;


    }
    public ViewHolder(@NonNull View itemView){
        super(itemView);

    }

    @NonNull
    @Override
    public CommentsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.fragment_dashboard_comments,parent,false);
        ViewHolder viewHolder = new ViewHolder();
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CommentsAdapter.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return commentItems.size();
    }
}
