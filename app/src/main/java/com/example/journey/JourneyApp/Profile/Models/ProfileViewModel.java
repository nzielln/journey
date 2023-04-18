package com.example.journey.JourneyApp.Profile.Models;

import android.text.format.Time;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.journey.JourneyApp.Main.Database;
import com.firebase.ui.common.ChangeEventType;
import com.firebase.ui.database.ChangeEventListener;
import com.firebase.ui.database.ClassSnapshotParser;
import com.firebase.ui.database.FirebaseArray;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.GenericTypeIndicator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProfileViewModel extends ViewModel {
    FirebaseUser currentUser = Database.FIREBASE_AUTH.getCurrentUser();
    MutableLiveData<UserModel> currentUserModel = new MutableLiveData<>();
    MutableLiveData<ApplicationModel> currentApplication = new MutableLiveData<>();

    public DatabaseReference todoTasksRef = Database.DB_REFERENCE.child(Database.TASKS).child(currentUser.getUid()).child(Database.TODO_TASKS);
    public DatabaseReference completedTasksRef = Database.DB_REFERENCE.child(Database.TASKS).child(currentUser.getUid()).child(Database.COMPLETED_TASKS);
    public DatabaseReference applicationsRef = Database.DB_REFERENCE.child(Database.APPLICATIONS).child(currentUser.getUid());
    public DatabaseReference timelineRef;

    public FirebaseArray<TaskItemModel> tasks = new FirebaseArray<>(todoTasksRef, new ClassSnapshotParser<>(TaskItemModel.class));
    public FirebaseArray<TaskItemModel> completedTasks = new FirebaseArray<>(completedTasksRef, new ClassSnapshotParser<>(TaskItemModel.class));
    public FirebaseArray<TimelineItemObject> timeline;
    public FirebaseArray<ApplicationModel> applications = new FirebaseArray<>(applicationsRef, new ClassSnapshotParser<>(ApplicationModel.class));

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

//                    Map<String, ApplicationModel> applications = (HashMap<String, ApplicationModel>) results.getValue(type);
//                    assert applications != null;
//                    ArrayList<ApplicationModel> applicationModels = new ArrayList<ApplicationModel>(applications.values());
//                    currentApplication.setValue(applicationModels.get(0));

                    timelineRef = Database.DB_REFERENCE
                            .child(Database.APPLICATIONS)
                            .child(currentUser.getUid())
//                            .child(currentApplication.getValue().getPushKey())
                            .child(Database.TIMELINE);

                    timeline = new FirebaseArray<>(timelineRef, new ClassSnapshotParser<>(TimelineItemObject.class));
                    timeline.addChangeEventListener(new ChangeListener());
                }
            }
        });
    }

    public void updateUserBackground(UserModel user) {
        currentUserModel.postValue(user);
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

    public MutableLiveData<UserModel> getCurrentUserModel() {
        return currentUserModel;
    }

    public MutableLiveData<ApplicationModel> getCurrentApplication() {
        return currentApplication;
    }

    public List<String> getApplications() {
        List<String> applicationsAray = new ArrayList<>();
        for (ApplicationModel applicationModel: applications ) {
            applicationsAray.add(applicationModel.getApplicationName());
        }

        return applicationsAray;
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
}
