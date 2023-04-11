package com.example.journey.JourneyApp.Dashboard;

import static com.example.journey.JourneyApp.Main.Helper.getShortTime;

import android.os.Bundle;
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

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class CreateNewPost extends BottomSheetDialogFragment {
    Button postCancelButton;
    Button postAddPostButton;
    TextView postContent;
    TextView postTitle;

    FirebaseUser currentUser;
    DatabaseReference dbReference;
    Helper helper;


    public static String TAG = CreateNewPost.class.toGenericString();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup bucket, @NonNull Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.add_new_post, bucket,false);
        currentUser = FirebaseAuth.getInstance().getCurrentUser();

        postCancelButton = view.findViewById(R.id.post_cancel_button);
        postAddPostButton = view.findViewById(R.id.post_add_button);
        postContent = view.findViewById(R.id.post_edit_text);
        postTitle = view.findViewById(R.id.title_edit_text);

        String authorId = currentUser.getUid();
        String timePosted = helper.getShortTime();

        postCancelButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getParentFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                DashboardFragment dashboardFragment = new DashboardFragment();

                transaction.replace(R.id.journey_fragment_container, dashboardFragment).commit();
                //Toast.makeText(getActivity(), "Cancel",Toast.LENGTH_SHORT).show();
                //Intent intent = new Intent(NewPost, JourneyMain.class);
                //startActivity(intent);
            }
        });


        dbReference = FirebaseDatabase.getInstance(Database.JOURNEYDB).getReference();
        postAddPostButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(),"Post Added", Toast.LENGTH_SHORT).show();
                String postId= UUID.randomUUID().toString();


                NewPost newPost = new NewPost(postId,postTitle.getText().toString(),
                        authorId,timePosted,postContent.getText().toString());

               // newPost = Database.DB_REFERENCE.child(Database.POST_ID_TO_POST).child(newPost).setValue(newPost);

               //newPost = Database.DB_REFERENCE.child(Database("post")).child(newPost).setValue(newPost);
               DatabaseReference postRef = dbReference.child("posts");
               postRef.push().setValue(newPost);


                //newPost.push()


                postTitle.setText(null);
                postContent.setText(null);

            }
        });
        return view;

    }
}
