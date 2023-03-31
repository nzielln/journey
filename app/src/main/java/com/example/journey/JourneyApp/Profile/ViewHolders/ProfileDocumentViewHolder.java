package com.example.journey.JourneyApp.Profile.ViewHolders;

import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.journey.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.checkbox.MaterialCheckBox;

public class ProfileDocumentViewHolder extends RecyclerView.ViewHolder {
    public TextView documentTitle;
    public TextView documentTime;
    public MaterialButton moreButton;
    public View documentCirle;

    public ProfileDocumentViewHolder(View item) {
        super(item);

        documentTitle = item.findViewById(R.id.document_title);
        documentTime = item.findViewById(R.id.document_time);
        moreButton = item.findViewById(R.id.more_button);

    }
}
