package com.example.journey.JourneyApp.Profile;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.journey.JourneyApp.Main.Database;
import com.example.journey.JourneyApp.Main.Helper;
import com.example.journey.JourneyApp.Profile.Adapters.CompletedRecyclerViewAdapter;
import com.example.journey.JourneyApp.Profile.Adapters.TasksRecyclerViewAdapter;
import com.example.journey.JourneyApp.Profile.Listeners.TaskOnClickListener;
import com.example.journey.JourneyApp.Profile.Modals.AddTaskModal;
import com.example.journey.JourneyApp.Profile.Models.ProfileViewModel;
import com.example.journey.JourneyApp.Profile.Models.TaskItemModel;
import com.example.journey.JourneyApp.Profile.Models.UserModel;
import com.example.journey.R;
import com.example.journey.databinding.FragmentProfileTodoBinding;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class ProfileToDoFragment extends Fragment {
    FragmentProfileTodoBinding binding;
    RecyclerView todoRecyclerView;
    RecyclerView completedRecyclerView;
    TasksRecyclerViewAdapter todoAdapter;
    CompletedRecyclerViewAdapter completedAdapter;
    FirebaseUser currentUser = Database.FIREBASE_AUTH.getCurrentUser();
    UserModel currentUserModel;
    String TAG = ProfileToDoFragment.class.toGenericString();
    Button addButton;
    ProfileViewModel profileViewModel;
    ArrayList<TaskItemModel> completed = new ArrayList<>();
    ArrayList<TaskItemModel> tasks = new ArrayList<>();

    public ProfileToDoFragment() {
        super(R.layout.fragment_profile_todo);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = this.getArguments();
        profileViewModel = new ViewModelProvider(requireActivity()).get(ProfileViewModel.class);
        currentUserModel = profileViewModel.getCurrentUserModel().getValue();
}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentProfileTodoBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        addButton = binding.addButton;
        todoRecyclerView = binding.todoRecyclerView;
        completedRecyclerView = binding.todoCompletedRecyclerView;

        createRecyclerView();

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAddTaskModal();
            }
        });

        FirebaseRecyclerOptions<TaskItemModel> tasksOptions = new FirebaseRecyclerOptions.Builder<TaskItemModel>()
                .setSnapshotArray(profileViewModel.tasks)
                .setLifecycleOwner(this)
                .build();


        FirebaseRecyclerOptions<TaskItemModel> completedOptions = new FirebaseRecyclerOptions.Builder<TaskItemModel>()
                .setSnapshotArray(profileViewModel.completedTasks)
                .setLifecycleOwner(this)
                .build();

        todoAdapter = new TasksRecyclerViewAdapter(tasksOptions);
        completedAdapter = new CompletedRecyclerViewAdapter(completedOptions);

        todoAdapter.setOnClickListener(new TaskOnClickListener() {
            @Override
            public void checkboxTapped(int position) {
                TaskItemModel taskItemModel = todoAdapter.getItem(position);
                Task<Void> updateTasks = Database.DB_REFERENCE.child(Database.TASKS).child(currentUserModel.getUserID()).child(Database.TODO_TASKS).child(taskItemModel.getPushKey()).removeValue();

                updateTasks.continueWith(new Continuation<Void, Task<Void>>() {
                    @Override
                    public Task<Void> then(@NonNull Task<Void> task) throws Exception {
                        taskItemModel.setIsCompleted(true);
                        return Database.DB_REFERENCE.child(Database.TASKS).child(currentUserModel.getUserID()).child(Database.COMPLETED_TASKS).child(taskItemModel.getPushKey()).setValue(taskItemModel).addOnCompleteListener(
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
                }).addOnSuccessListener(new OnSuccessListener<Task<Void>>() {
                    @Override
                    public void onSuccess(Task<Void> voidTask) {
                        Log.i(TAG, "DONE!");
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e(TAG, "FAILED TO DO ALL THAT FOR USER: " + currentUserModel.getUserID());
                    }
                });
            }

            @Override
            public void deleteTapped(int position) {
                TaskItemModel taskItemModel = todoAdapter.getItem(position);
                Database.DB_REFERENCE.child(Database.TASKS).child(currentUserModel.getUserID()).child(Database.TODO_TASKS).child(taskItemModel.getPushKey()).removeValue().addOnCompleteListener(
                        new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Log.i(TAG, "DONE!");
                                } else {
                                    Log.e(TAG, "FAILED TO DELETE TASK MODEL FOR USER: " + currentUserModel.getUserID());
                                }
                            }
                        }
                );
                todoRecyclerView.swapAdapter(todoAdapter, true);

            }
        });

        completedAdapter.setOnClickListener(new TaskOnClickListener() {
            @Override
            public void checkboxTapped(int position) {
                TaskItemModel taskItemModel = completedAdapter.getItem(position);
                Task<Void> updateTasks = Database.DB_REFERENCE.child(Database.TASKS).child(currentUserModel.getUserID()).child(Database.COMPLETED_TASKS).child(taskItemModel.getPushKey()).removeValue();

                updateTasks.continueWith(new Continuation<Void, Task<Void>>() {
                    @Override
                    public Task<Void> then(@NonNull Task<Void> task) throws Exception {
                        taskItemModel.setIsCompleted(false);
                        return Database.DB_REFERENCE.child(Database.TASKS).child(currentUserModel.getUserID()).child(Database.TODO_TASKS).child(taskItemModel.getPushKey()).setValue(taskItemModel).addOnCompleteListener(
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
                }).addOnSuccessListener(new OnSuccessListener<Task<Void>>() {
                    @Override
                    public void onSuccess(Task<Void> voidTask) {
                        Log.i(TAG, "DONE!");
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e(TAG, "FAILED TO DO ALL THAT FOR USER: " + currentUserModel.getUserID());
                    }
                });
            }

            @Override
            public void deleteTapped(int position) {
                TaskItemModel taskItemModel = completedAdapter.getItem(position);
                Database.DB_REFERENCE.child(Database.TASKS).child(currentUserModel.getUserID()).child(Database.COMPLETED_TASKS).child(taskItemModel.getPushKey()).removeValue().addOnCompleteListener(
                        new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Log.i(TAG, "DONE!");
                                } else {
                                    Log.e(TAG, "FAILED TO DELETE TASK MODEL FOR USER: " + currentUserModel.getUserID());
                                }
                            }
                        }
                );
                completedRecyclerView.swapAdapter(completedAdapter, true);
            }
        });

        todoRecyclerView.setAdapter(todoAdapter);
        completedRecyclerView.setAdapter(completedAdapter);
    }

    public void openAddTaskModal() {
        AddTaskModal addTaskModal = new AddTaskModal();
        Bundle bundle = new Bundle();
        bundle.putParcelable(Helper.USER_MODEL, currentUserModel);
        addTaskModal.setArguments(bundle);
        addTaskModal.show(getChildFragmentManager(), AddTaskModal.TAG);
    }

    public void addNewTask() {
        int pos = tasks.size();
        todoAdapter.notifyItemInserted(pos);
        todoRecyclerView.scrollToPosition(pos);
    }

    public void createRecyclerView() {
        todoRecyclerView.setHasFixedSize(false);
        todoRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, true));

        completedRecyclerView.setHasFixedSize(false);
        completedRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, true));
    }
}