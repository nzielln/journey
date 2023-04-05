package com.example.journey.JourneyApp.Profile.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.journey.JourneyApp.Profile.Models.TaskItemModel;
import com.example.journey.JourneyApp.Profile.Models.TimelineItemObject;
import com.example.journey.JourneyApp.Profile.ViewHolders.ProdileTodoViewHolder;
import com.example.journey.JourneyApp.Profile.ViewHolders.ProfileTimelineViewHolder;
import com.example.journey.R;

import java.util.ArrayList;

public class ProfileTimelineRecyclerViewAdapter extends RecyclerView.Adapter<ProfileTimelineViewHolder> {
    ArrayList<TimelineItemObject> items;
    Context context;

    public ProfileTimelineRecyclerViewAdapter(ArrayList<TimelineItemObject> items, Context context) {
        this.items = items;
        this.context = context;
    }

    @NonNull
    @Override
    public ProfileTimelineViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.timeline_item, parent, false);
        return new ProfileTimelineViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProfileTimelineViewHolder holder, int position) {
        TimelineItemObject itemModel = items.get(position);
        holder.timelineTitle.setText(itemModel.getTitle());
        holder.timelineTime.setText(itemModel.getDateAdded());
//         Set circle color here
        if (itemModel.getCompleted()) {

            holder.timelineContent.setBackground(ContextCompat.getDrawable(context, R.drawable.rounded_15_gray));
            holder.timelineCirle.setBackground(ContextCompat.getDrawable(context, R.drawable.circle));
        } else {
            holder.timelineCirle.setBackground(ContextCompat.getDrawable(context, R.drawable.circle_complete));
            holder.timelineContent.setBackground(ContextCompat.getDrawable(context, R.drawable.rounded_15_white_border_dark));

        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
