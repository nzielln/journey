package com.example.journey.JourneyApp.Profile.Models;

import com.example.journey.JourneyApp.Main.Helper;

import java.util.UUID;

public class TimelineItemObject {
    String title;
    String applicationId;
    String id;
    String dateAdded; // ISO String
    Integer rank;
    Boolean completed;
    String pushKey;

    public TimelineItemObject(){}
    public TimelineItemObject(String id, String title, String applicationId, String dateAdded, Integer rank, String pushKey) {
        this.id = id;
        this.title = title;
        this.applicationId = applicationId;
        this.dateAdded = dateAdded;
        this.rank = rank;
        this.completed = false;
        this.pushKey = pushKey;
    }

    public TimelineItemObject(String title, String applicationId, String id, String dateAdded, Integer rank, Boolean completed, String pushKey) {
        this.title = title;
        this.applicationId = applicationId;
        this.id = id;
        this.dateAdded = dateAdded;
        this.rank = rank;
        this.completed = completed;
        this.pushKey = pushKey;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getApplicationId() {
        return applicationId;
    }

    public String getDateAdded() {
        return dateAdded;
    }

    public Integer getRank() {
        return rank;
    }

    public Boolean getCompleted() {
        return completed;
    }

    public void setCompleted(Boolean completed) {
        this.completed = completed;
    }

    public String getPushKey() {
        return pushKey;
    }

    public void setPushKey(String pushKey) {
        this.pushKey = pushKey;
    }

    public static TimelineItemObject getMockTimelineItem(Boolean isCompleted) {
        String timelineTitle = "Mock timeline object with random ID-" + UUID.randomUUID().toString();
        Integer rank = Helper.RANK_NUMBER;
        Helper.RANK_NUMBER++;

        return new TimelineItemObject(UUID.randomUUID().toString(), timelineTitle, UUID.randomUUID().toString(), Helper.getLongDateTime(), rank, isCompleted, "");
    }
}
