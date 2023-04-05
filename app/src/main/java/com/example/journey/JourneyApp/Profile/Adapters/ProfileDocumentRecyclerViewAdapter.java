package com.example.journey.JourneyApp.Profile.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.journey.JourneyApp.Main.Helper;
import com.example.journey.JourneyApp.Profile.Models.DocumentItemModel;
import com.example.journey.JourneyApp.Profile.Models.TaskItemModel;
import com.example.journey.JourneyApp.Profile.Models.TimelineItemObject;
import com.example.journey.JourneyApp.Profile.ViewHolders.ProdileTodoViewHolder;
import com.example.journey.JourneyApp.Profile.ViewHolders.ProfileDocumentViewHolder;
import com.example.journey.R;

import java.util.ArrayList;

public class ProfileDocumentRecyclerViewAdapter extends RecyclerView.Adapter<ProfileDocumentViewHolder> {
    ArrayList<DocumentItemModel> items;

    public ProfileDocumentRecyclerViewAdapter(ArrayList<DocumentItemModel> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public ProfileDocumentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.document_item, parent, false);
        return new ProfileDocumentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProfileDocumentViewHolder holder, int position) {
        DocumentItemModel itemModel = items.get(position);
        holder.documentTitle.setText(itemModel.getTitle());
        holder.documentTime.setText(itemModel.getDateAdded());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
