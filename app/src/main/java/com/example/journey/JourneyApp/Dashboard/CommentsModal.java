package com.example.journey.JourneyApp.Dashboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.journey.JourneyApp.Profile.Models.UserModel;
import com.example.journey.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class CommentsModal extends BottomSheetDialogFragment {
    Button addComment;
    Button cancelComment;
    UserModel currentUser;

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
        return view;

    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        addComment = (Button)view.findViewById(R.id.add_comment_btn);
        cancelComment = (Button)view.findViewById(R.id.cancel_comment_btn);
    }



}
