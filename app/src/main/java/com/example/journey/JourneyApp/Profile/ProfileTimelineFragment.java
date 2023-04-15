package com.example.journey.JourneyApp.Profile;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

import com.example.journey.JourneyApp.Main.Database;
import com.example.journey.JourneyApp.Main.Helper;
import com.example.journey.JourneyApp.Profile.Adapters.DropdownArrayAdapter;
import com.example.journey.JourneyApp.Profile.Adapters.ProfileTimelineRecyclerViewAdapter;
import com.example.journey.JourneyApp.Profile.Modals.AddTaskModal;
import com.example.journey.JourneyApp.Profile.Modals.UpdateApplicationModal;
import com.example.journey.JourneyApp.Profile.Models.ApplicationModel;
import com.example.journey.JourneyApp.Profile.Models.ProfileViewModel;
import com.example.journey.JourneyApp.Profile.Models.TaskItemModel;
import com.example.journey.JourneyApp.Profile.Models.TimelineItemObject;
import com.example.journey.JourneyApp.Profile.Models.UserModel;
import com.example.journey.R;
import com.example.journey.databinding.FragmentTimelineBinding;
import com.firebase.ui.database.FirebaseListOptions;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class ProfileTimelineFragment extends Fragment {
    FragmentTimelineBinding binding;
    ArrayList<TimelineItemObject> items = new ArrayList<>();
    AutoCompleteTextView applicationsOptions;
    ProfileViewModel profileViewModel;
    FirebaseUser currentUser = Database.FIREBASE_AUTH.getCurrentUser();
    UserModel currentUserModel;
    RecyclerView recyclerView;
    Button updateApplication;
    ProfileTimelineRecyclerViewAdapter adapter;
    ApplicationModel currentApplication;

    public ProfileTimelineFragment() {
        super(R.layout.fragment_timeline);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        profileViewModel = new ViewModelProvider(requireActivity()).get(ProfileViewModel.class);
        currentUserModel = profileViewModel.getCurrentUserModel().getValue();
        currentApplication = profileViewModel.getCurrentApplication().getValue();

        Bundle bundle = new Bundle();
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
        applicationsOptions = binding.applicationNameOptions;
        recyclerView = binding.timelineRecyclerView;
        updateApplication = binding.updateApplicationButton;

        createRecyclerView();

        DropdownArrayAdapter dropdownInputAdapter = new DropdownArrayAdapter(getContext(), R.layout.application_name_item, profileViewModel.applications);
        applicationsOptions.setThreshold(3);
        applicationsOptions.setAdapter(dropdownInputAdapter);
        applicationsOptions.setText(currentApplication.getApplicationName(), false);

        FirebaseRecyclerOptions<TimelineItemObject> timelineOptions = new FirebaseRecyclerOptions.Builder<TimelineItemObject>()
                .setSnapshotArray(profileViewModel.timeline)
                .setLifecycleOwner(this)
                .build();

        adapter = new ProfileTimelineRecyclerViewAdapter(timelineOptions, getContext());
        recyclerView.setAdapter(adapter);

        applicationsOptions.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ApplicationModel applicationModel = (ApplicationModel) parent.getItemAtPosition(position);
                profileViewModel.updateCurrentApplication(applicationModel);
                recyclerView.swapAdapter(adapter, true);
            }
        });

        updateApplication.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openUpdateApplicationModal();
            }
        });
    }

    public void openUpdateApplicationModal() {
        UpdateApplicationModal updateApplicationModal = new UpdateApplicationModal();
        updateApplicationModal.show(getChildFragmentManager(), UpdateApplicationModal.TAG);
    }

    public void createRecyclerView() {
        recyclerView.setHasFixedSize(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, true));

    }
}