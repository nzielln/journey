package com.example.journey.JourneyApp.Dashboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.journey.JourneyApp.Main.Database;
import com.example.journey.JourneyApp.Profile.Models.UserModel;
import com.example.journey.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.textfield.TextInputLayout;

public class CommentsModal extends BottomSheetDialogFragment {
    Button addCommentBtn;
    Button cancelCommentBtn;
    UserModel currentUser;
    TextInputLayout commentContentInput;

    RecyclerView recyclerView;

    String postId;

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

        commentContentInput = view.findViewById(R.id.comment_input);
        cancelCommentBtn = view.findViewById(R.id.cancel_comment_btn);
        addCommentBtn = view.findViewById(R.id.add_comment_btn);
//        recyclerView = view.findViewById(R.id.comments_recycler_view);
//
//        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));



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


        addCommentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Added", Toast.LENGTH_SHORT).show();
                String comment_text = String.valueOf(commentContentInput.getEditText().getText());
                addComment();
                dismiss();
            }
        });
        return view;

    }

    public void addComment(){

//        Database.DB_REFERENCE.child("comments").addValueEventListener(
//
//        )

    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.comments_recycler_view);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

    }

}
