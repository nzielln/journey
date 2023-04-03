package com.example.journey.JourneyApp.Profile.Models;

import com.example.journey.JourneyApp.Main.Helper;

import java.util.UUID;

public class TaskItemModel {
    String id;
    String title;
    String dateAdded; // ISO String
    Boolean isCompleted;

    public TaskItemModel() {}
    public TaskItemModel(String id, String title, String dateAdded, Boolean isCompleted) {
        this.id = id;
        this.title = title;
        this.dateAdded = dateAdded;
        this.isCompleted = isCompleted;
    }

    public void setIsCompleted(Boolean isCompleted) {
        this.isCompleted = isCompleted;
    }

    public Boolean getCompleted() {
        return isCompleted;
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

    public static TaskItemModel getMockTask(Boolean isCompleted) {
        String taskTitle = "Mock Tak title with random ID-" + UUID.randomUUID().toString();
        return new TaskItemModel(UUID.randomUUID().toString(), taskTitle, Helper.getLongDate(), isCompleted);
    }
}
