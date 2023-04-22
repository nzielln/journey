package com.example.journey.JourneyApp.Dashboard;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.journey.JourneyApp.Main.Database;
import com.example.journey.R;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class CommentsAdapter extends RecyclerView.Adapter<CommentsAdapter.ViewHolder> {

    ArrayList<CommentsModel> commentItems;
    DatabaseReference dbReference;
    FirebaseUser currentUser;

    public CommentsAdapter(ArrayList<CommentsModel> commentItems){
        this.commentItems = commentItems;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView commenterUserName;
        private TextView commentDate;
        private TextView commentContext;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            commenterUserName = (TextView)itemView.findViewById(R.id.name_comment_Tv);
            commentDate = (TextView)itemView.findViewById(R.id.date_comment_Tv);
            commentContext = (TextView)itemView.findViewById(R.id.content_comment_Tv);

        }
    }

    @NonNull
    @Override
    public CommentsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.fragment_dashboard_comments,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CommentsAdapter.ViewHolder holder, int position) {
        dbReference = FirebaseDatabase.getInstance(Database.JOURNEYDB).getReference();
        currentUser = Database.FIREBASE_AUTH.getCurrentUser();

        CommentsModel comment = commentItems.get(position);
        holder.commenterUserName.setText(comment.getCommenterName());
        holder.commentContext.setText(comment.getCommentContent());
        holder.commentDate.setText(comment.getCommentDate());


        CommentsModel model1 = new CommentsModel("Molly","5678","4/21/2023","hello I like your post");
        CommentsModel model2 = new CommentsModel("Polly","5679","4/21/2023","hello I like your post too");
        commentItems.add(model1);
        commentItems.add(model2);

    }

    @Override
    public int getItemCount() {
        return commentItems.size();
    }
}
