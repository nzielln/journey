package com.example.journey.JourneyApp.Profile.ViewHolders;

public interface TasksDelegate {

    void taskMarkedComplete(String taskUUID);
    void taskMarkedIncomplete(String taskUUID);

}
