package com.example.journey.JourneyApp.Profile;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
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
import com.example.journey.JourneyApp.Profile.Listeners.TimelineDelegate;
import com.example.journey.JourneyApp.Profile.Listeners.TimelineOnClickListener;
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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class ProfileTimelineFragment extends Fragment implements Parcelable, TimelineDelegate {
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
    String TAG = ProfileTimelineFragment.class.toGenericString();


    TimelineOnClickListener onClickListener;

    public ProfileTimelineFragment() {
        super(R.layout.fragment_timeline);
    }

    protected ProfileTimelineFragment(Parcel in) {
        currentUser = in.readParcelable(FirebaseUser.class.getClassLoader());
        currentUserModel = in.readParcelable(UserModel.class.getClassLoader());
    }

    public static final Creator<ProfileTimelineFragment> CREATOR = new Creator<ProfileTimelineFragment>() {
        @Override
        public ProfileTimelineFragment createFromParcel(Parcel in) {
            return new ProfileTimelineFragment(in);
        }

        @Override
        public ProfileTimelineFragment[] newArray(int size) {
            return new ProfileTimelineFragment[size];
        }
    };

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

        onClickListener = new TimelineOnClickListener() {
            @Override
            public void itemTapped(int position) {
                TimelineItemObject timelineItemObject = adapter.getItem(position);

                AlertDialog builder = new MaterialAlertDialogBuilder(getContext())
                        .setTitle("Update timeline status")
                        .setMessage("Mark " + timelineItemObject.getTitle() + " as complete?")
                        .setPositiveButton(getContext().getResources().getString(R.string.complete), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                timelineItemObject.setCompleted(true);
                                Database.DB_REFERENCE
                                        .child(Database.APPLICATIONS)
                                        .child(currentUserModel.getUserID())
                                        .child(currentApplication.getPushKey())
                                        .child(Database.TIMELINE)
                                        .child(timelineItemObject.getPushKey()).setValue(timelineItemObject).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    Log.i(TAG, "SUCCESSFULLY UPDATED ITEM");
                                                }
                                            }
                                        });
                            }
                        })
                        .setNeutralButton(getContext().getResources().getString(R.string.dismiss), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        })
                        .setNegativeButton(getContext().getResources().getString(R.string.incomplete), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                timelineItemObject.setCompleted(false);
                                Database.DB_REFERENCE
                                        .child(Database.APPLICATIONS)
                                        .child(currentUserModel.getUserID())
                                        .child(currentApplication.getPushKey())
                                        .child(Database.TIMELINE)
                                        .child(timelineItemObject.getPushKey()).setValue(timelineItemObject).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    Log.i(TAG, "SUCCESSFULLY UPDATED ITEM");
                                                }
                                            }
                                        });
                            }
                        })
                        .show();

            }
        };

        adapter = new ProfileTimelineRecyclerViewAdapter(timelineOptions, getContext());
        adapter.setOnClickListener(onClickListener);

        applicationsOptions.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ApplicationModel applicationModel = (ApplicationModel) parent.getItemAtPosition(position);
                profileViewModel.updateCurrentApplication(applicationModel);
                FirebaseRecyclerOptions<TimelineItemObject> timelineOptions = new FirebaseRecyclerOptions.Builder<TimelineItemObject>()
                        .setSnapshotArray(profileViewModel.timeline)
                        .setLifecycleOwner(ProfileTimelineFragment.this)
                        .build();

                adapter = new ProfileTimelineRecyclerViewAdapter(timelineOptions, getContext());
                adapter.setOnClickListener(onClickListener);
                recyclerView.swapAdapter(adapter, true);
            }
        });

        updateApplication.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openUpdateApplicationModal();
            }
        });

        recyclerView.setAdapter(adapter);

    }

    public void openUpdateApplicationModal() {
        UpdateApplicationModal updateApplicationModal = new UpdateApplicationModal();
        Bundle bundle = new Bundle();
        bundle.putParcelable(Helper.TIMELINE_DELEGATE, this);
        updateApplicationModal.setArguments(bundle);
        updateApplicationModal.show(getChildFragmentManager(), UpdateApplicationModal.TAG);
    }

    public void createRecyclerView() {
        recyclerView.setHasFixedSize(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, true));

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeParcelable(currentUser, flags);
        dest.writeParcelable(currentUserModel, flags);
    }

    @Override
    public void newItemAdded() {
        recyclerView.swapAdapter(adapter, true);
    }
}