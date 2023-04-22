package com.example.journey.JourneyApp.Dashboard;

import static com.example.journey.JourneyApp.Main.Helper.getShortDate;

import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.journey.JourneyApp.Main.Database;
import com.example.journey.JourneyApp.Profile.Models.UserModel;
import com.example.journey.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class CommentsModal extends BottomSheetDialogFragment {
    Button addCommentBtn;
    Button cancelCommentBtn;
    TextInputLayout commentContentInput;
    RecyclerView recyclerView;

    String postId;
    Context context;

    FirebaseUser currentUser;
    DatabaseReference dbReference;
    UserModel user;
    ArrayList<CommentModel> items = new ArrayList<>();
    FragmentActivity contxt;

    public CommentsModal(String pId){
        postId = pId;
    }

    @Override
    public void onCreate(@NonNull Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.add_comment_modal,container,false);
        currentUser = Database.FIREBASE_AUTH.getCurrentUser();


        commentContentInput = view.findViewById(R.id.comment_input);
        //TextInputLayout editText_1 = new TextInputLayout(commentContentInput.getContext());

        //commentContentInput = view.findViewById(R.id.comment_editText);
        cancelCommentBtn = view.findViewById(R.id.cancel_comment_btn);
        addCommentBtn = view.findViewById(R.id.add_comment_btn);
        recyclerView = view.findViewById(R.id.comments_recycler_view);

//
//        recyclerView.setOnTouchListener(new View.OnTouchListener(){
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                v.getParent().requestDisallowInterceptTouchEvent(true);
//                v.onTouchEvent(event);
//                return true;
//            }
//        });


        cancelCommentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Cancelled", Toast.LENGTH_SHORT).show();
                dismiss();
            }
        });
        dbReference = FirebaseDatabase.getInstance(Database.JOURNEYDB).getReference();

        addCommentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String date_posted = getShortDate();
                String comment_text = commentContentInput.getEditText().getText().toString();
                if(comment_text.equals("")){
                    Toast.makeText(getActivity(), "Post cannot be empty", Toast.LENGTH_SHORT).show();
                }else {
                    String commentId = UUID.randomUUID().toString();
                    String userId = currentUser.getUid();
                    NewComment newComment = new NewComment(commentId,userId,date_posted,comment_text);
                    DatabaseReference commentRef = dbReference.child("posts").child(postId).child("comments");
                    commentRef.child(commentId).setValue(newComment);

                    Toast.makeText(getActivity(), "Added", Toast.LENGTH_SHORT).show();

                }


                //Log.d("edittext testing",commentContentInput.getEditText().getText().toString());
                //addComment();
                dismiss();
            }
        });
        return view;

    }

    public void addComment(){
        //if(commentContentInput.getT)

    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        contxt = requireActivity();


        getDialog().setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                BottomSheetDialog d = (BottomSheetDialog) dialog;

                View mLayout = d.findViewById(R.id.bottom_sheet);

                mLayout.setMinimumHeight((int) (Resources.getSystem().getDisplayMetrics().heightPixels * 0.35));
                //mLayout.set((int) (Resources.getSystem().getDisplayMetrics().heightPixels * 0.35));
            }
        });
        Map<String, UserModel> allUsers = new HashMap<>();

        dbReference.child("users").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()) {
                    for (final DataSnapshot inner : task.getResult().getChildren()) {
                        UserModel user = inner.getValue(UserModel.class);

                        allUsers.put(user.getUserID(), user);
                    }
                    retrieveComments(allUsers);
                }
            }
        });
    }
        private void retrieveComments(Map<String, UserModel> users) {
            dbReference.child("posts").child(postId).child("comments").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DataSnapshot> task) {
                    if (!task.isSuccessful()) {
                        Log.d("firebase", "Error getting comments", task.getException());
                    } else {
                        for (final DataSnapshot inner : task.getResult().getChildren()) {
                            NewComment postedComment = inner.getValue(NewComment.class);
                            assert postedComment != null;
                            UserModel user = users.get(postedComment.getCommentUserId());

//                            StorageReference userPic;
//                            if (user.getProfileImage() == null) {
//                                userPic = null;
//                            } else {
//                                userPic = Database.DB_STORAGE_REFERENCE.child(user.getProfileImage());
//                            }
                            items.add(new CommentModel(user,
                                    postedComment.getCommentId(),
                                    postedComment.getCommentDate(),
                                    postedComment.getCommentContent()));
                        }
                        Collections.reverse(items);

                        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL));
                        recyclerView.setAdapter(new CommentsAdapter(items,contxt));
                        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                    }
                }
            });


        }




//        ArrayList<NewComment> commentItems = new ArrayList<>();
//        NewComment model1 = new NewComment("Id1","22","4/21/2023","Molly","hello I like your post. This is really nice and cool and great and nice.");
//        NewComment model2 = new NewComment("Id2","23","4/22/2023","Polly","hello I like your post too. Wow super duper cool. I love what you said");
//        NewComment model3 = new NewComment("Id3","23","4/25/2023","Rolly","hello I like your post too. It is awesome and nice and cool and nice and great.");
//        NewComment model4 = new NewComment("Id4","23","4/28/2023","Solly","hiiii I your post isnt good. I dont like it why would you say something like that");
//        NewComment model5 = new NewComment("Id5","23","4/29/2023","Wolly","It is really cool to be this cool and do cool things I like your post too");
//        NewComment model6 = new NewComment("Id6","23","4/21/2023","Molly","hello I like your post. This is really nice and cool and great and nice.");
//        NewComment model7 = new NewComment("Id7","23","4/22/2023","Polly","hello I like your post too. Wow super duper cool. I love what you said");
//
//        commentItems.add(model2);
//        commentItems.add(model3);
//        commentItems.add(model4);
//        commentItems.add(model5);
//        commentItems.add(model6);
//        commentItems.add(model7);

//        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL));


        //recyclerView.setAdapter(new CommentsAdapter(commentItems));
//            recyclerView.setAdapter(new CommentsAdapter(items));
//        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));


}
