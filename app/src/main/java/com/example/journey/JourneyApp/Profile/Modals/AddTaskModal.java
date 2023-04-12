package com.example.journey.JourneyApp.Profile.Modals;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.example.journey.JourneyApp.Main.Database;
import com.example.journey.JourneyApp.Main.Helper;
import com.example.journey.JourneyApp.Profile.Models.ProfileViewModel;
import com.example.journey.JourneyApp.Profile.Models.TaskItemModel;
import com.example.journey.JourneyApp.Profile.Models.TaskModel;
import com.example.journey.JourneyApp.Profile.Models.UserModel;
import com.example.journey.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;

import java.util.UUID;

public class AddTaskModal extends BottomSheetDialogFragment {
    Button cancelButton;
    Button addTaskButton;
    UserModel currentUserModel;
    FirebaseUser currentUser = Database.FIREBASE_AUTH.getCurrentUser();
    TextInputLayout taskTitle;
    ProfileViewModel profileViewModel;

    public static String TAG =  AddTaskModal.class.toGenericString();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = this.getArguments();
        profileViewModel = new ViewModelProvider(requireActivity()).get(ProfileViewModel.class);
        currentUserModel = profileViewModel.getCurrentUserModel().getValue();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.add_todo_item_modal, container, false);

        cancelButton = view.findViewById(R.id.cancel_button);
        addTaskButton = view.findViewById(R.id.add_task_button);
        taskTitle = view.findViewById(R.id.task_input);

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
                String title = String.valueOf(taskTitle.getEditText().getText());
                TaskItemModel newTask = new TaskItemModel(UUID.randomUUID().toString(), title, Helper.getLongDate(), Boolean.FALSE);
                addTask(newTask);
                dismiss();
            }
        });

        return view;
    }

    public void addTask(TaskItemModel taskItemModel) {
        String key = profileViewModel.todoUserTasksRef.push().getKey();
        taskItemModel.setPushKey(key);

        assert key != null;
        Database.DB_REFERENCE.child(Database.TASKS).child(currentUserModel.getUserID()).child(Database.TODO_TASKS).child(key).setValue(taskItemModel).addOnCompleteListener(
                new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.i(TAG, "DONE!");
                        } else {
                            Log.e(TAG, "FAILED TO UPDATE TASK MODEL FOR USER: " + currentUserModel.getUserID() + "WITH NEW TASK");
                        }
                    }
                }
        );
    }
}
