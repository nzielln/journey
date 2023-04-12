package com.example.journey.JourneyApp.Profile.Models;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.journey.JourneyApp.Main.Database;
import com.firebase.ui.common.ChangeEventType;
import com.firebase.ui.database.ChangeEventListener;
import com.firebase.ui.database.ClassSnapshotParser;
import com.firebase.ui.database.FirebaseArray;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

public class ProfileViewModel extends ViewModel {
    FirebaseUser currentUser = Database.FIREBASE_AUTH.getCurrentUser();
    MutableLiveData<UserModel> currentUserModel = new MutableLiveData<>();
    public DatabaseReference todoUserTasksRef = Database.DB_REFERENCE.child(Database.TASKS).child(currentUser.getUid()).child(Database.TODO_TASKS);
    public DatabaseReference completedTasksRef = Database.DB_REFERENCE.child(Database.TASKS).child(currentUser.getUid()).child(Database.COMPLETED_TASKS);

    public FirebaseArray<TaskItemModel> tasks = new FirebaseArray<>(todoUserTasksRef, new ClassSnapshotParser<>(TaskItemModel.class));
    public FirebaseArray<TaskItemModel> completedTasks = new FirebaseArray<>(completedTasksRef, new ClassSnapshotParser<>(TaskItemModel.class));

    public ProfileViewModel() {
        tasks.addChangeEventListener(new ChangeListener());
        completedTasks.addChangeEventListener(new ChangeListener());

    }

    @Override
    protected void onCleared() {
        super.onCleared();
        tasks.removeChangeEventListener(new ChangeListener());
        completedTasks.removeChangeEventListener(new ChangeListener());
    }

    public void updateCurrentUser(UserModel user) {
        currentUserModel.setValue(user);
    }

    public MutableLiveData<UserModel> getCurrentUserModel() {
        return currentUserModel;
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
