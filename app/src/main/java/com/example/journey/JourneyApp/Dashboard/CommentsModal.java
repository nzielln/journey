package com.example.journey.JourneyApp.Dashboard;

import static com.example.journey.JourneyApp.Main.Helper.getShortDate;

import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.journey.JourneyApp.Main.Database;
import com.example.journey.JourneyApp.Profile.Models.UserModel;
import com.example.journey.R;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Objects;

public class CommentsModal extends BottomSheetDialogFragment {
    Button addCommentBtn;
    Button cancelCommentBtn;
    TextInputLayout commentContentInput;
    RecyclerView recyclerView;

    String postId;
    Context context;

    FirebaseUser currentUser;
    DatabaseReference dbReference;

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
                Toast.makeText(getActivity(), "Added", Toast.LENGTH_SHORT).show();
                String date_posted = getShortDate();
                commentContentInput.getEditText();

                Log.d("edittext testing",commentContentInput.getEditText().getText().toString());
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

        getDialog().setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                BottomSheetDialog d = (BottomSheetDialog) dialog;

                View mLayout = d.findViewById(R.id.bottom_sheet);

                mLayout.setMinimumHeight((int) (Resources.getSystem().getDisplayMetrics().heightPixels * 0.35));
                //mLayout.set((int) (Resources.getSystem().getDisplayMetrics().heightPixels * 0.35));
            }
        });



        ArrayList<CommentsModel> commentItems = new ArrayList<>();
        CommentsModel model1 = new CommentsModel("Id1","4/21/2023","Molly","hello I like your post. This is really nice and cool and great and nice.");
        CommentsModel model2 = new CommentsModel("Id2","4/22/2023","Polly","hello I like your post too. Wow super duper cool. I love what you said");
        CommentsModel model3 = new CommentsModel("Id3","4/25/2023","Rolly","hello I like your post too. It is awesome and nice and cool and nice and great.");
        CommentsModel model4 = new CommentsModel("Id4","4/28/2023","Solly","hiiii I your post isnt good. I dont like it why would you say something like that");
        CommentsModel model5 = new CommentsModel("Id5","4/29/2023","Wolly","It is really cool to be this cool and do cool things I like your post too");
        CommentsModel model6 = new CommentsModel("Id6","4/21/2023","Molly","hello I like your post. This is really nice and cool and great and nice.");
        CommentsModel model7 = new CommentsModel("Id7","4/22/2023","Polly","hello I like your post too. Wow super duper cool. I love what you said");
        CommentsModel model8 = new CommentsModel("Id8","4/25/2023","Rolly","hello I like your post too. It is awesome and nice and cool and nice and great.");
        CommentsModel model9 = new CommentsModel("Id9","4/28/2023","Solly","hiiii I your post isnt good. I dont like it why would you say something like that");
        commentItems.add(model1);
        commentItems.add(model2);
        commentItems.add(model3);
        commentItems.add(model4);
        commentItems.add(model5);
        commentItems.add(model6);
        commentItems.add(model7);
        commentItems.add(model8);
        commentItems.add(model9);

        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(),
                DividerItemDecoration.VERTICAL));


        recyclerView.setAdapter(new CommentsAdapter(commentItems));
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

    }
}
