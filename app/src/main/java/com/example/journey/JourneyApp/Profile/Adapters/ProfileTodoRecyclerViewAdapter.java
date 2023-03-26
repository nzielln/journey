package com.example.journey.JourneyApp.Profile.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.journey.JourneyApp.Main.Helper;
import com.example.journey.JourneyApp.Profile.Models.ProfileTimelineItemModel;
import com.example.journey.JourneyApp.Profile.Models.ProfileTodoItemModel;
import com.example.journey.JourneyApp.Profile.ViewHolders.ProdileTodoViewHolder;
import com.example.journey.R;

import java.util.ArrayList;

public class ProfileTodoRecyclerViewAdapter extends RecyclerView.Adapter<ProdileTodoViewHolder> {
    public static final int TODO_ITEM = 0;
    public static final int TODO_ITEM_COMPLETED = 1;
    ArrayList<ProfileTodoItemModel> items;

    public ProfileTodoRecyclerViewAdapter(ArrayList<ProfileTodoItemModel> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public ProdileTodoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.profile_todo_item, parent, false);
        return new ProdileTodoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProdileTodoViewHolder holder, int position) {
        ProfileTodoItemModel itemModel = items.get(position);
        holder.todoTitle.setText(itemModel.getTitle());
        holder.todoTimeAdded.setText(Helper.todoISOToDate(itemModel.getDateAdded()));
        holder.checkBox.setChecked(itemModel.getCompleted());

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (items.get(position).getCompleted()) {
            return TODO_ITEM_COMPLETED;
        }

        return TODO_ITEM;
    }

    public ArrayList<ProfileTodoItemModel> getItems() {
        return items;
    }
}
