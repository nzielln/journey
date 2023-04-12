package com.example.journey.JourneyApp.Profile.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.journey.JourneyApp.Profile.Listeners.TaskOnClickListener;
import com.example.journey.JourneyApp.Profile.Models.TaskItemModel;
import com.example.journey.JourneyApp.Profile.ViewHolders.ProfileTodoViewHolder;
import com.example.journey.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import java.util.ArrayList;

public class CompletedRecyclerViewAdapter extends FirebaseRecyclerAdapter<TaskItemModel, ProfileTodoViewHolder> {
    public static final int TODO_ITEM = 0;
    public static final int TODO_ITEM_COMPLETED = 1;
    public TaskOnClickListener onClickListener;

    public CompletedRecyclerViewAdapter(@NonNull FirebaseRecyclerOptions<TaskItemModel> options) {
        super(options);
    }

    public void setOnClickListener(TaskOnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    @NonNull
    @Override
    public ProfileTodoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.profile_todo_item, parent, false);
        return new ProfileTodoViewHolder(view, onClickListener);
    }

    @Override
    protected void onBindViewHolder(@NonNull ProfileTodoViewHolder holder, int position, @NonNull TaskItemModel model) {
        holder.todoTitle.setText(model.getTitle());
        holder.todoTimeAdded.setText(model.getDateAdded());
        holder.checkBox.setChecked(model.getCompleted());
    }

}
