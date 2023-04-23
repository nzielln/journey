package com.example.journey.JourneyApp.Dashboard;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.journey.JourneyApp.Main.Database;
import com.example.journey.JourneyApp.Profile.Models.UserModel;
import com.example.journey.R;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.core.Context;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class CommentsAdapter extends RecyclerView.Adapter<CommentsAdapter.ViewHolder> {

    ArrayList<CommentModel> commentItems;
    DatabaseReference dbReference;
    FirebaseUser currentUser;
    //Context context;
    FragmentActivity contxt;

    Button viewComment;
    public CommentsAdapter(ArrayList<CommentModel> commentItems, FragmentActivity contxt){
        this.commentItems = commentItems;
        this.contxt = contxt;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView commenterUserName;
        private TextView commentDate;
        private TextView commentContext;
        private ImageView userImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            commenterUserName = (TextView)itemView.findViewById(R.id.name_comment_Tv);
            commentDate = (TextView)itemView.findViewById(R.id.date_comment_Tv);
            commentContext = (TextView)itemView.findViewById(R.id.content_comment_Tv);
            userImage = (ImageView)itemView.findViewById(R.id.comment_user_image);
            userImage.setImageDrawable(ContextCompat.getDrawable(contxt,R.drawable.pick_photo));
//
//            DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
//                    layoutManager.getOrientation());
//            recyclerView.addItemDecoration(dividerItemDecoration);


        }
    }

    @NonNull
    @Override
    public CommentsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //Context context = parent.getContext();

       // LayoutInflater inflater = LayoutInflater.from(parent.getContext(context));
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.fragment_dashboard_comments,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CommentsAdapter.ViewHolder holder, int position) {
        dbReference = FirebaseDatabase.getInstance(Database.JOURNEYDB).getReference();
        currentUser = Database.FIREBASE_AUTH.getCurrentUser();

        CommentModel comment = commentItems.get(position);

        UserModel user = comment.getUser();

        //holder.commenterUserName.setText(comment.getCommentUserId());
        holder.commentContext.setText(comment.getCommentContent());
        holder.commentDate.setText(comment.getCommentDate());
        //holder.userImage.setImageDrawable();
        holder.commenterUserName.setText(user.getFirstName() + " " + user.getLastName());

        if (user.getProfileImage()== null){
            holder.userImage.setImageDrawable(ContextCompat.getDrawable(contxt,R.drawable.pick_photo));
        } else {
            StorageReference userPic;

            userPic = Database.DB_STORAGE_REFERENCE.child(user.getProfileImage());
            Glide.with(contxt).load(userPic).into(holder.userImage);
        }



    }

    @Override
    public int getItemCount() {
        return commentItems.size();
    }
}
