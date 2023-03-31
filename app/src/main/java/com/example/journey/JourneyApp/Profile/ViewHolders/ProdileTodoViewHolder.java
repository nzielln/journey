package com.example.journey.JourneyApp.Profile.ViewHolders;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.journey.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.checkbox.MaterialCheckBox;

public class ProdileTodoViewHolder extends RecyclerView.ViewHolder {
    public TextView todoTitle;
    public TextView todoTimeAdded;
    public MaterialCheckBox checkBox;
    public MaterialButton moreButton;

    public ProdileTodoViewHolder(View item) {
        super(item);

        todoTitle = item.findViewById(R.id.todo_item_title);
        todoTimeAdded = item.findViewById(R.id.todo_item_time);
        checkBox = item.findViewById(R.id.todo_checkbox);
        moreButton = item.findViewById(R.id.more_button);
    }

}
