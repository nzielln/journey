package com.example.journey.JourneyApp.Profile.Models;

import java.util.UUID;

public class ProfileTimelineItemModel {
    String title;
    UUID applicationId;
    UUID id;
    String dateAdded; // ISO String
    Integer rank;

    public ProfileTimelineItemModel(){}
    public ProfileTimelineItemModel(String title, UUID applicationId, UUID id, String dateAdded, Integer rank) {
        this.title = title;
        this.applicationId = applicationId;
        this.id = id;
        this.dateAdded = dateAdded;
        this.rank = rank;
    }


    public UUID getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public UUID getApplicationId() {
        return applicationId;
    }

    public String getDateAdded() {
        return dateAdded;
    }

    public Integer getRank() {
        return rank;
    }
}
