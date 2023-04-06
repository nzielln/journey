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
import java.util.stream.Collectors;

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
        Bundle bundle = new Bundle();

    }

    public void addSamples() {

        sample.add(TaskItemModel.getMockTask(false));
        sample.add(TaskItemModel.getMockTask(false));
        sample.add(TaskItemModel.getMockTask(true));
        sample.add(TaskItemModel.getMockTask(true));
        sample.add(TaskItemModel.getMockTask(false));

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
        tasks.add(pos, TaskItemModel.getMockTask(false));
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

//    public ArrayList<TaskItemModel> getCompletedTasks() {
//        return sample.stream().filter(TaskItemModel::getCompleted).collect(Collectors.toCollection(ArrayList::new));
//    }
//    public ArrayList<TaskItemModel> getToBeCompletedTasks() {
//        return sample.stream().filter(task -> !task.getCompleted()).collect(Collectors.toCollection(ArrayList::new));
//    }
}