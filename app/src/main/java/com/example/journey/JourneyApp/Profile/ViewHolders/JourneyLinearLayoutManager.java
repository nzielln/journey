package com.example.journey.JourneyApp.Profile.ViewHolders;

import android.content.Context;
import android.util.AttributeSet;

import androidx.recyclerview.widget.LinearLayoutManager;

public class JourneyLinearLayoutManager extends LinearLayoutManager {
    public JourneyLinearLayoutManager(Context context) {
        super(context);
    }

    public JourneyLinearLayoutManager(Context context, int orientation, boolean reverseLayout) {
        super(context, orientation, reverseLayout);
    }

    public JourneyLinearLayoutManager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public boolean supportsPredictiveItemAnimations() {
        return false;
    }
}
