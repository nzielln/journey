package com.example.journey.JourneyApp.Dashboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.journey.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class NewPost extends BottomSheetDialogFragment {
    Button postCancelButton;
    Button postAddPostButton;

    public static String TAG = NewPost.class.toGenericString();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup bucket, @NonNull Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.add_new_post, bucket,false);

        postCancelButton = view.findViewById(R.id.post_cancel_button);
        postAddPostButton = view.findViewById(R.id.post_add_button);

        postCancelButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Cancel",Toast.LENGTH_LONG).show();
            }
        });
        postAddPostButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(),"Post Added", Toast.LENGTH_SHORT).show();
            }
        });
        return view;

    }
}
