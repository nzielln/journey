package com.example.journey.JourneyApp.Dashboard;

import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.journey.JourneyApp.Profile.Models.UserModel;
import com.example.journey.R;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.Objects;

public class CommentsModal extends BottomSheetDialogFragment {
    Button addCommentBtn;
    Button cancelCommentBtn;
    UserModel currentUser;
    TextInputLayout commentContentInput;

    RecyclerView recyclerView;
    BottomSheetBehavior bottomSheetBehavior;

    String postId;
    Context context;

    public CommentsModal(String pId){
        postId = pId;
    }

    @Override
    public void onCreate(@NonNull Bundle savedInstanceState){
        super.onCreate(savedInstanceState);



    }
//    @Override
//    public Dialog onCreateDialog(Bundle savedInstanceState){
//        BottomSheetDialog dialog = (BottomSheetDialog) super.onCreateDialog(savedInstanceState);
//        View view
//    bottomSheetBehavior.setPeekHeight(400);
//    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.add_comment_modal,container,false);


        commentContentInput = view.findViewById(R.id.comment_input);
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

//    @NonNull @Override
//    public void onCreateDialog(Bundle savedInstanceState) {
//
//        BottomSheetDialog dialog = (BottomSheetDialog) super.onCreateDialog(savedInstanceState);
//
//        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
//            @Override
//            public void onShow(DialogInterface dialog) {
//                BottomSheetDialog bottom_dialog = (BottomSheetDialog) dialog;
//
//                LinearLayout bottomSheet = (LinearLayout) bottom_dialog.findViewById(R.id.bottom_sheet);
//                assert bottomSheet != null;
//                //BottomSheetBehavior.from(bottomSheet).setState(BottomSheetBehavior.STATE_EXPANDED);
//                DisplayMetrics displayMetrics = requireActivity().getResources().getDisplayMetrics();
//                int height = displayMetrics.heightPixels;
//                int maxHeight = (int) (height*0.80);
//                BottomSheetBehavior.from(bottomSheet).setPeekHeight(maxHeight);
//            }
//        });
//    }

    public void addComment(){

//        Database.DB_REFERENCE.child("comments").addValueEventListener(
//
//        )

    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getDialog().setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                BottomSheetDialog d = (BottomSheetDialog) dialog;

                View mLayout = d.findViewById(R.id.bottom_sheet);

                mLayout.setMinimumHeight((int) (Resources.getSystem().getDisplayMetrics().heightPixels * 0.35));
            }
        });



        ArrayList<CommentsModel> commentItems = new ArrayList<>();
        CommentsModel model1 = new CommentsModel("Molly","5678","4/21/2023","hello I like your post");
        CommentsModel model2 = new CommentsModel("Polly","5679","4/21/2023","hello I like your post too");
        commentItems.add(model1);
        commentItems.add(model2);



        recyclerView.setAdapter(new CommentsAdapter(commentItems));
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

    }
    //@Ov

}
