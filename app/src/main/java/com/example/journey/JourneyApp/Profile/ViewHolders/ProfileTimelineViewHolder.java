package com.example.journey.JourneyApp.Profile.ViewHolders;

import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.journey.JourneyApp.Profile.Listeners.TimelineOnClickListener;
import com.example.journey.R;

public class ProfileTimelineViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public TextView timelineTitle;
    public TextView timelineTime;
    public LinearLayout timelineContent;
    public LinearLayout timeline;
    public View timelineCirle;
    TimelineOnClickListener onClickListener;

    public ProfileTimelineViewHolder(View item, TimelineOnClickListener onClickListener) {
        super(item);
        this.onClickListener = onClickListener;
        timelineTitle = item.findViewById(R.id.timeline_title);
        timelineTime = item.findViewById(R.id.timeline_time);
        timelineContent = item.findViewById(R.id.timeline_content);
        timeline = item.findViewById(R.id.timeline);
        timelineCirle = item.findViewById(R.id.timeline_rank_circle);

        item.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        onClickListener.itemTapped(getAbsoluteAdapterPosition());
    }
}
