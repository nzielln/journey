package com.example.journey.JourneyApp.Profile.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.journey.JourneyApp.Profile.Models.TimelineItemObject;
import com.example.journey.JourneyApp.Profile.ViewHolders.ProfileTimelineViewHolder;
import com.example.journey.R;

import java.util.ArrayList;

public class ProfileTimelineRecyclerViewAdapter extends RecyclerView.Adapter<ProfileTimelineViewHolder> {
    ArrayList<TimelineItemObject> items;
    Context context;

    static final Integer TOP = 0;
    static final Integer MIDDLE = 1;
    static final Integer BOTTOM = 2;


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

            holder.timelineContent.setBackground(ContextCompat.getDrawable(context, R.drawable.rounded_15_gray_15));
            holder.timelineCirle.setBackground(ContextCompat.getDrawable(context, R.drawable.circle));
        } else {
            holder.timelineCirle.setBackground(ContextCompat.getDrawable(context, R.drawable.circle_complete));
            holder.timelineContent.setBackground(ContextCompat.getDrawable(context, R.drawable.rounded_15_white_border_dark));

        }

        if (getItemViewType(position) == TOP) {
            holder.timeline.setBackground(ContextCompat.getDrawable(context, R.drawable.timeline_bg_round));
        } else {
            holder.timeline.setBackground(ContextCompat.getDrawable(context, R.drawable.timeline_bg));

        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TOP;
        } else {
            return MIDDLE;
        }
    }
}
