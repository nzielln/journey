package com.example.journey.JourneyApp.Profile.Models;

import com.example.journey.JourneyApp.Main.Helper;

import java.util.UUID;

public class TaskItemModel {
    String id;
    String title;
    String dateAdded; // ISO String
    Boolean completed;
    String pushKey;

    public TaskItemModel() {}
    public TaskItemModel(String id, String title, String dateAdded, Boolean completed) {
        this.id = id;
        this.title = title;
        this.dateAdded = dateAdded;
        this.completed = completed;
    }

    public TaskItemModel(String id, String title, String dateAdded, Boolean completed, String key) {
        this.id = id;
        this.title = title;
        this.dateAdded = dateAdded;
        this.completed = completed;
        this.pushKey = key;
    }

    public void setIsCompleted(Boolean completed) {
        this.completed = completed;
    }

    public Boolean getCompleted() {
        return completed;
    }

    public String getTitle() {
        return title;
    }

    public String getDateAdded() {
        return dateAdded;
    }

    public String getId() {
        return id;
    }

    public void setPushKey(String key) {
        this.pushKey = key;
    }

    public String getPushKey() {
        return pushKey;
    }

    public static TaskItemModel getMockTask(Boolean isCompleted) {
        String taskTitle = "Mock Tak title with random ID-" + UUID.randomUUID().toString();
        return new TaskItemModel(UUID.randomUUID().toString(), taskTitle, Helper.getLongDate(), isCompleted);
    }
}
