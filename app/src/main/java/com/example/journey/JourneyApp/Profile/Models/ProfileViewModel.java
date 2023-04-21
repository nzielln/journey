package com.example.journey.JourneyApp.Profile.Models;

import android.text.format.Time;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.journey.JourneyApp.Dashboard.CardModel;
import com.example.journey.JourneyApp.Main.Database;
import com.example.journey.JourneyApp.Profile.ProfileState;
import com.firebase.ui.common.ChangeEventType;
import com.firebase.ui.database.ChangeEventListener;
import com.firebase.ui.database.ClassSnapshotParser;
import com.firebase.ui.database.FirebaseArray;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.Query;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProfileViewModel extends ViewModel {
    FirebaseUser currentUser = Database.FIREBASE_AUTH.getCurrentUser();
    MutableLiveData<UserModel> currentUserModel = new MutableLiveData<>();
    MutableLiveData<UserModel> activeUserModel = new MutableLiveData<>();
    MutableLiveData<ApplicationModel> currentApplication = new MutableLiveData<>();
    MutableLiveData<ApplicationModel> activeUserApplication = new MutableLiveData<>();
    MutableLiveData<ProfileState> currentProfileState = new MutableLiveData<>(ProfileState.PERSONAL);
    MutableLiveData<Boolean> isFollowing = new MutableLiveData<>(Boolean.FALSE);

    public DatabaseReference todoTasksRef = Database.DB_REFERENCE.child(Database.TASKS).child(currentUser.getUid()).child(Database.TODO_TASKS);
    public DatabaseReference completedTasksRef = Database.DB_REFERENCE.child(Database.TASKS).child(currentUser.getUid()).child(Database.COMPLETED_TASKS);
    public DatabaseReference applicationsRef = Database.DB_REFERENCE.child(Database.APPLICATIONS).child(currentUser.getUid());
    public Query followingRef = Database.DB_REFERENCE.child(Database.USERS).child(currentUser.getUid()).child(Database.FOLLOWING).orderByChild(Database.USER_ID);
    public DatabaseReference timelineRef;

    public FirebaseArray<TaskItemModel> tasks = new FirebaseArray<>(todoTasksRef, new ClassSnapshotParser<>(TaskItemModel.class));
    public FirebaseArray<TaskItemModel> completedTasks = new FirebaseArray<>(completedTasksRef, new ClassSnapshotParser<>(TaskItemModel.class));
    public FirebaseArray<TimelineItemObject> timeline;
    public FirebaseArray<ApplicationModel> applications = new FirebaseArray<>(applicationsRef, new ClassSnapshotParser<>(ApplicationModel.class));

    // Active user (user clicked on)
    public FirebaseArray<TimelineItemObject> activeUserTimeline;
    public FirebaseArray<CardModel> activeUserPosts;
    public DatabaseReference activeUserTimelineRef;
    public Query postsRef;

    public ProfileViewModel() {
        tasks.addChangeEventListener(new ChangeListener());
        completedTasks.addChangeEventListener(new ChangeListener());
        applications.addChangeEventListener(new ChangeListener());
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        tasks.removeChangeEventListener(new ChangeListener());
        completedTasks.removeChangeEventListener(new ChangeListener());
        timeline.removeChangeEventListener(new ChangeListener());
        applications.removeChangeEventListener(new ChangeListener());
    }

    public void updateCurrentUser(UserModel user) {
        currentUserModel.setValue(user);
        Database.DB_REFERENCE.child(Database.APPLICATIONS).child(currentUser.getUid()).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()) {
                    DataSnapshot results = task.getResult();
                    GenericTypeIndicator<HashMap<String, ApplicationModel>> type = new GenericTypeIndicator<HashMap<String, ApplicationModel>>() {};

                    Map<String, ApplicationModel> applications = (HashMap<String, ApplicationModel>) results.getValue(type);
                    assert applications != null;
                    ArrayList<ApplicationModel> applicationModels = new ArrayList<ApplicationModel>(applications.values());
                    currentApplication.setValue(applicationModels.get(0));

                    timelineRef = Database.DB_REFERENCE
                            .child(Database.APPLICATIONS)
                            .child(currentUser.getUid())
                            .child(currentApplication.getValue().getPushKey())
                            .child(Database.TIMELINE);

                    timeline = new FirebaseArray<>(timelineRef, new ClassSnapshotParser<>(TimelineItemObject.class));
                    timeline.addChangeEventListener(new ChangeListener());
                }
            }
        });
    }

    public void updateProfileState(ProfileState state, @Nullable UserModel userModel) {
        currentProfileState.postValue(state);
        if (userModel != null) {
            updateActiveUserBackground(userModel);
        }
    }

    public MutableLiveData<ProfileState> getCurrentProfileState() {
        return currentProfileState;
    }

    public MutableLiveData<UserModel> getActiveUserModel() {
        return activeUserModel;
    }

    public MutableLiveData<ApplicationModel> getActiveUserApplication() {
        return activeUserApplication;
    }

    public MutableLiveData<Boolean> getIsFollowing() {
        return isFollowing;
    }

    public void updateUserBackground(UserModel user) {
        currentUserModel.postValue(user);
    }
    public void updateActiveUserBackground(UserModel user) {
        activeUserModel.postValue(user);
        postsRef = Database.DB_REFERENCE.child("posts").orderByChild("authorID").equalTo(user.getUserID());
        postsRef.addChildEventListener(new ChildListener());
        activeUserPosts = new FirebaseArray<>(postsRef, new ClassSnapshotParser<>(CardModel.class));

        Database.DB_REFERENCE.child(Database.APPLICATIONS).child(user.getUserID()).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()) {
                    DataSnapshot results = task.getResult();
                    GenericTypeIndicator<HashMap<String, ApplicationModel>> type = new GenericTypeIndicator<HashMap<String, ApplicationModel>>() {};

                    Map<String, ApplicationModel> applications = (HashMap<String, ApplicationModel>) results.getValue(type);
                    assert applications != null;
                    ArrayList<ApplicationModel> applicationModels = new ArrayList<ApplicationModel>(applications.values());
                    activeUserApplication.setValue(applicationModels.get(0));

                    activeUserTimelineRef = Database.DB_REFERENCE
                            .child(Database.APPLICATIONS)
                            .child(user.getUserID())
                            .child(activeUserApplication.getValue().getPushKey())
                            .child(Database.TIMELINE);

                    activeUserTimeline = new FirebaseArray<>(activeUserTimelineRef, new ClassSnapshotParser<>(TimelineItemObject.class));
                    activeUserTimeline.addChangeEventListener(new ChangeListener());
                }
            }
        });

        followingRef.equalTo(user.getUserID()).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()) {
                    if (task.getResult().getValue() != null) {
                        isFollowing.postValue(Boolean.TRUE);
                    } else {
                        isFollowing.postValue(Boolean.FALSE);
                    }

                }
            }
        });
    }

    public void updateCurrentApplication(ApplicationModel applicationModel) {
        currentApplication.postValue(applicationModel);
        timelineRef = Database.DB_REFERENCE
                .child(Database.APPLICATIONS)
                .child(currentUser.getUid())
                .child(applicationModel.getPushKey())
                .child(Database.TIMELINE);

        timeline = new FirebaseArray<>(timelineRef, new ClassSnapshotParser<>(TimelineItemObject.class));
        timeline.addChangeEventListener(new ChangeListener());
    }

    public void updateActiveApplication(ApplicationModel applicationModel) {
        activeUserApplication.postValue(applicationModel);
        activeUserTimelineRef = Database.DB_REFERENCE
                .child(Database.APPLICATIONS)
                .child(activeUserModel.getValue().getUserID())
                .child(applicationModel.getPushKey())
                .child(Database.TIMELINE);

        activeUserTimeline = new FirebaseArray<>(activeUserTimelineRef, new ClassSnapshotParser<>(TimelineItemObject.class));
        activeUserTimeline.addChangeEventListener(new ChangeListener());
    }

    public MutableLiveData<UserModel> getCurrentUserModel() {
        return currentUserModel;
    }

    public MutableLiveData<ApplicationModel> getCurrentApplication() {
        return currentApplication;
    }

    public List<String> getApplications() {
        List<String> applicationsArray = new ArrayList<>();
        for (ApplicationModel applicationModel: applications ) {
            applicationsArray.add(applicationModel.getApplicationName());
        }

        return applicationsArray;
    }

    class ChangeListener implements ChangeEventListener {

        @Override
        public void onChildChanged(@NonNull ChangeEventType type, @NonNull DataSnapshot snapshot, int newIndex, int oldIndex) {

        }

        @Override
        public void onDataChanged() {

        }

        @Override
        public void onError(@NonNull DatabaseError databaseError) {

        }
    }

    class ChildListener implements ChildEventListener {

        @Override
        public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

        }

        @Override
        public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

        }

        @Override
        public void onChildRemoved(@NonNull DataSnapshot snapshot) {

        }

        @Override
        public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {

        }
    }
}
