package com.example.journey.JourneyApp.Profile.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.journey.JourneyApp.Profile.Models.ApplicationModel;
import com.example.journey.R;

import java.util.ArrayList;
import java.util.List;

public class DropdownArrayAdapter extends ArrayAdapter<ApplicationModel> {
    private Context context;

    public DropdownArrayAdapter(@NonNull Context context, int resource, @NonNull List<ApplicationModel> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        TextView view = (TextView) convertView;
        if (view == null) {
            view = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.dropdown_input_item, parent, false);
        }

        ApplicationModel model = getItem(position);
        view.setText(model.getApplicationName());

        return view;
    }

    @NonNull
    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            public CharSequence convertResultToString(Object resultValue) {
                return ((ApplicationModel) resultValue).getApplicationName();
            }

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                return null;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {

            }
        };
    }
}
