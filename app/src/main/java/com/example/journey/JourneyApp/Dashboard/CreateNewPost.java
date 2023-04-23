package com.example.journey.JourneyApp.Dashboard;

import static com.example.journey.JourneyApp.Main.Helper.getShortDate;
import static com.example.journey.JourneyApp.Main.Helper.getShortTime;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.journey.JourneyApp.Main.Database;
import com.example.journey.JourneyApp.Main.Helper;
import com.example.journey.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class CreateNewPost extends BottomSheetDialogFragment {
    Button postCancelButton;
    Button postAddPostButton;
    TextView postContent;
    TextView postTitle;
    FragmentTransaction transaction;
    DashboardFragment dashboardFragment;

    FirebaseUser currentUser;
    DatabaseReference dbReference;

    String currentUserId;

    public static String TAG = CreateNewPost.class.toGenericString();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup bucket, @NonNull Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.add_new_post, bucket,false);
        currentUser = Database.FIREBASE_AUTH.getCurrentUser();

        if(currentUser!=null){
            currentUserId = currentUser.getUid();
            Log.d("current Id",currentUserId);
        }

        postCancelButton = view.findViewById(R.id.post_cancel_button);
        postAddPostButton = view.findViewById(R.id.post_add_button);
        postContent = view.findViewById(R.id.post_edit_text);
        postTitle = view.findViewById(R.id.title_edit_text);

        postCancelButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getParentFragmentManager();
                transaction = fragmentManager.beginTransaction();
                dashboardFragment = new DashboardFragment();

                transaction.replace(R.id.journey_fragment_container, dashboardFragment).commit();
                Toast.makeText(getActivity(), "Cancel",Toast.LENGTH_SHORT).show();
            }
        });


        dbReference = FirebaseDatabase.getInstance(Database.JOURNEYDB).getReference();
        postAddPostButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String timePosted = getShortDate() + " " + getShortTime();

                //postContent.getText().toString();
                if(postTitle.getText().toString().trim().equals("") || postContent.getText().toString().trim().equals("")) {
                   Toast.makeText(getActivity(),"Cannot Post Empty Content", Toast.LENGTH_SHORT).show();

               } else {
                   String postId= UUID.randomUUID().toString();
                   String authorId = currentUser.getUid();

                    HashMap<String, Boolean> liked = new HashMap<String, Boolean>();
                    liked.put("test1",false);

                   NewPost newPost = new NewPost(postId,postTitle.getText().toString(),
                           authorId,timePosted,postContent.getText().toString(), liked);


                   DatabaseReference postRef = dbReference.child("posts");
                   postRef.child(postId).setValue(newPost);
                   Toast.makeText(getActivity(),"Post Added", Toast.LENGTH_SHORT).show();
                   postTitle.setText(null);
                   postContent.setText(null);
               }

            }
        });
        return view;

    }
}
