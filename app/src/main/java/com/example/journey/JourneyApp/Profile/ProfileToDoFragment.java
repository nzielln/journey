package com.example.journey.JourneyApp.Profile;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.journey.JourneyApp.Main.Database;
import com.example.journey.JourneyApp.Main.Helper;
import com.example.journey.JourneyApp.Profile.Adapters.ProfileTodoCompletedRecyclerViewAdapter;
import com.example.journey.JourneyApp.Profile.Adapters.ProfileTodoRecyclerViewAdapter;
import com.example.journey.JourneyApp.Profile.Modals.AddTaskModal;
import com.example.journey.JourneyApp.Profile.Models.TaskItemModel;
import com.example.journey.R;
import com.example.journey.databinding.FragmentProfileTodoBinding;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileToDoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileToDoFragment extends Fragment {
    FragmentProfileTodoBinding binding;
    RecyclerView todoRecyclerView;
    RecyclerView todoCompletedRecyclerView;
    ProfileTodoRecyclerViewAdapter profileTodoRecyclerViewAdapter;
    ProfileTodoCompletedRecyclerViewAdapter profileTodoCompletedRecyclerViewAdapter;

    Button addButton;

    ArrayList<TaskItemModel> sample = new ArrayList<>();
    ArrayList<TaskItemModel> completed;
    ArrayList<TaskItemModel> tasks;

    DatabaseReference databaseReference;

    public ProfileToDoFragment() {
        super(R.layout.fragment_profile_todo);
    }

    public static ProfileToDoFragment newInstance(String param1, String param2) {
        ProfileToDoFragment fragment = new ProfileToDoFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addSamples();
        setUpDatabase();
        Bundle bundle = new Bundle();

    }

    public void setUpDatabase() {
        databaseReference = FirebaseDatabase.getInstance(Database.JOURNEYDB).getReference();
    }

    public void addSamples() {
        TaskItemModel sample1 = new TaskItemModel("Transitioned to waiting for approval","2022-09-25T15:18:45+00:00" , false);
        TaskItemModel sample2 = new TaskItemModel("Transitioned to waiting for approval","2022-09-25T15:18:45+00:00" , false);
        TaskItemModel sample1Completed = new TaskItemModel("Transitioned to waiting for approval","2022-09-25T15:18:45+00:00" , true);

        sample.add(sample1);
        sample.add(sample2);
        sample.add(sample1Completed);

        tasks = Helper.getToBeCompletedTasks(sample);
        completed = Helper.getCompletedTasks(sample);

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
        todoCompletedRecyclerView = binding.todoCompletedRecyclerView;

        createRecyclerView();

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                addNewTask();

                openAddTaskModal();
            }
        });
    }

    public void openAddTaskModal() {
        AddTaskModal addTaskModal = new AddTaskModal();
        addTaskModal.show(getChildFragmentManager(), AddTaskModal.TAG);
    }

    public void addNewTask() {
        int pos = tasks.size();

        TaskItemModel sample1 = new TaskItemModel("Some new task name here","2022-09-25T15:18:45+00:00" , false);
        tasks.add(pos, sample1);
        profileTodoRecyclerViewAdapter.notifyItemInserted(pos);
        todoRecyclerView.scrollToPosition(pos);
    }

    public void createRecyclerView() {
        profileTodoRecyclerViewAdapter = new ProfileTodoRecyclerViewAdapter(tasks);
        profileTodoCompletedRecyclerViewAdapter = new ProfileTodoCompletedRecyclerViewAdapter(completed);

        todoRecyclerView.setHasFixedSize(true);
        todoRecyclerView.setAdapter(profileTodoRecyclerViewAdapter);
        todoRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        todoCompletedRecyclerView.setHasFixedSize(true);
        todoCompletedRecyclerView.setAdapter(profileTodoCompletedRecyclerViewAdapter);
        todoCompletedRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

    }

//    public ArrayList<ProfileTodoItemModel> getCompletedTasks() {
//        return sample.stream().filter(ProfileTodoItemModel::getCompleted).collect(Collectors.toCollection(ArrayList::new));
//    }
//    public ArrayList<ProfileTodoItemModel> getToBeCompletedTasks() {
//        return sample.stream().filter(task -> !task.getCompleted()).collect(Collectors.toCollection(ArrayList::new));
//    }
}