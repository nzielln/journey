package com.example.journey.JourneyApp.Profile.ViewHolders;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.journey.JourneyApp.Profile.Listeners.TaskOnClickListener;
import com.example.journey.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.checkbox.MaterialCheckBox;

public class ProfileTodoViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public TextView todoTitle;
    public TextView todoTimeAdded;
    public MaterialCheckBox checkBox;
    public MaterialButton deleteButton;
    TaskOnClickListener onClickListener;

    public ProfileTodoViewHolder(View item, TaskOnClickListener onClickListener) {
        super(item);
        this.onClickListener = onClickListener;

        todoTitle = item.findViewById(R.id.todo_item_title);
        todoTimeAdded = item.findViewById(R.id.todo_item_time);
        checkBox = item.findViewById(R.id.todo_checkbox);
        deleteButton = item.findViewById(R.id.delete_button);

        checkBox.setOnClickListener(this);
        deleteButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == checkBox.getId()) {
            onClickListener.checkboxTapped(getAbsoluteAdapterPosition());
        } else if (v.getId() == deleteButton.getId()) {
            onClickListener.deleteTapped(getAbsoluteAdapterPosition());
        }
    }
}
