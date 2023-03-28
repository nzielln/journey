package com.example.journey.JourneyApp.Profile.Models;

import java.util.UUID;

public class TaskItemModel {
    UUID id;
    String title;
    String dateAdded; // ISO String
    Boolean isCompleted;

    public TaskItemModel() {}
    public TaskItemModel(String title, String dateAdded, Boolean isCompleted) {
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

    public UUID getId() {
        return id;
    }
}
