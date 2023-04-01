package com.example.journey.JourneyApp.Profile;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.journey.JourneyApp.Main.Database;
import com.example.journey.JourneyApp.Profile.Adapters.ProfileTimelineRecyclerViewAdapter;
import com.example.journey.JourneyApp.Profile.Adapters.ProfileTodoCompletedRecyclerViewAdapter;
import com.example.journey.JourneyApp.Profile.Adapters.ProfileTodoRecyclerViewAdapter;
import com.example.journey.JourneyApp.Profile.Models.TaskItemModel;
import com.example.journey.JourneyApp.Profile.Models.TimelineItemObject;
import com.example.journey.R;
import com.example.journey.databinding.FragmentTimelineBinding;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.android.material.timepicker.MaterialTimePicker;
import com.google.android.material.timepicker.TimeFormat;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;

public class ProfileTimelineFragment extends Fragment {
    FragmentTimelineBinding binding;
    ArrayList<TimelineItemObject> items = new ArrayList<>();
    DatabaseReference databaseReference;

    RecyclerView recyclerView;
    ProfileTimelineRecyclerViewAdapter adapter;

    public ProfileTimelineFragment() {
        super(R.layout.fragment_timeline);
    }

    public static ProfileTimelineFragment newInstance(String param1, String param2) {
        ProfileTimelineFragment fragment = new ProfileTimelineFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setUpDatabase();
        Bundle bundle = new Bundle();

    }

    public void setUpDatabase() {
        databaseReference = FirebaseDatabase.getInstance(Database.JOURNEYDB).getReference();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentTimelineBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = binding.timelineRecyclerView;
        createFakeTimeline();
        createRecyclerView();

    }

    private void createFakeTimeline() {
        items.add(TimelineItemObject.getMockTimelineItem(false));
        items.add(TimelineItemObject.getMockTimelineItem(true));
        items.add(TimelineItemObject.getMockTimelineItem(true));
        items.add(TimelineItemObject.getMockTimelineItem(true));
    }

    public void createRecyclerView() {
        adapter = new ProfileTimelineRecyclerViewAdapter(items, getContext());

        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

    }

}