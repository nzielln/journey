package com.example.journey.JourneyApp.Profile.Modals;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.journey.JourneyApp.Profile.Models.TaskItemModel;
import com.example.journey.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class AddTaskModal extends BottomSheetDialogFragment {
    Button cancelButton;
    Button addTaskButton;
    TaskItemModel taskItemModel;
    public static String TAG =  AddTaskModal.class.toGenericString();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.add_todo_item_modal, container, false);

        cancelButton = view.findViewById(R.id.cancel_button);
        addTaskButton = view.findViewById(R.id.add_task_button);

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(),
                                "Cancel button clicked", Toast.LENGTH_SHORT)
                        .show();
                dismiss();
            }
        });

        addTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(),
                                "Add task button clicked", Toast.LENGTH_SHORT)
                        .show();
                dismiss();
            }
        });

        return view;
    }

    public void addTask() {

    }
}
