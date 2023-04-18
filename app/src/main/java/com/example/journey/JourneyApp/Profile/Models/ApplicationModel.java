package com.example.journey.JourneyApp.Profile.Models;

import com.example.journey.JourneyApp.Main.Helper;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

public class ApplicationModel {
    String applicationID;
    String applicationName;
    ApplicationStatus status;
    String dateCreated;
    String dateComleted;
    Boolean completed;
    Map<String, TimelineItemObject> timeline = new HashMap<>();
    String pushKey;

    public ApplicationModel() {}

    public ApplicationModel(String applicationID,
                            String applicationName,
                            String status,
                            String dateCreated,
                            String dateComleted,
                            Boolean completed,
                            String pushKey) {
        this.applicationID = applicationID;
        this.applicationName = applicationName;
        this.status = ApplicationStatus.valueOf(status);
        this.dateCreated = dateCreated;
        this.dateComleted = dateComleted;
        this.completed = completed;
        this.timeline = new HashMap<>();
        this.pushKey = pushKey;
    }
    public ApplicationModel(String applicationID,
                            String applicationName,
                            String status,
                            String dateCreated) {
        this.applicationID = applicationID;
        this.applicationName = applicationName;
        this.status = ApplicationStatus.valueOf(status);;
        this.dateCreated = dateCreated;
        this.timeline = new HashMap<>();
    }

    public ApplicationModel(String applicationID,
                            String applicationName,
                            String dateCreated) {
        this.applicationID = applicationID;
        this.applicationName = applicationName;
        this.dateCreated = dateCreated;
        this.status = ApplicationStatus.UNKNOWN;
        this.completed = false;
        this.timeline = new HashMap<>();

    }

    public ApplicationModel(String applicationID,
                            String applicationName,
                            String status,
                            String dateCreated,
                            Boolean completed,
                            String pushKey) {
        this.applicationID = applicationID;
        this.applicationName = applicationName;
        this.status = ApplicationStatus.valueOf(status);;
        this.dateCreated = dateCreated;
        this.completed = completed;
        this.pushKey = pushKey;
    }

    public ApplicationModel(String applicationID,
                            String applicationName,
                            String status,
                            String dateCreated,
                            String dateComleted,
                            Boolean completed,
                            Map<String, TimelineItemObject> timeline,
                            String pushKey) {
        this.applicationID = applicationID;
        this.applicationName = applicationName;
        this.status = ApplicationStatus.valueOf(status);;
        this.dateCreated = dateCreated;
        this.dateComleted = dateComleted;
        this.completed = completed;
        this.timeline = timeline;
        this.pushKey = pushKey;
    }

    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }

    public void setStatus(ApplicationStatus status) {
        this.status = status;
    }

    public void setDateComleted(String dateComleted) {
        this.dateComleted = dateComleted;
    }

    public void setCompleted(Boolean completed) {
        this.completed = completed;
    }

    public String getApplicationID() {
        return applicationID;
    }

    public String getApplicationName() {
        return applicationName;
    }

    public ApplicationStatus getStatus() {
        return status;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public String getDateComleted() {
        return dateComleted;
    }

    public Boolean getCompleted() {
        return completed;
    }

    public void setPushKey(String pushKey) {
        this.pushKey = pushKey;
    }

    public String getPushKey() {
        return pushKey;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    public static ApplicationModel getMock() {
        Date date = new Date();
        String applicationName = "Fake Application ID-" + UUID.randomUUID().toString();
        return new ApplicationModel(UUID.randomUUID().toString(), applicationName, ApplicationStatus.PROCESSING.name(), Helper.getLongDateTime());
    }

    public Map<String, TimelineItemObject> getTimeline() {
        return timeline;
    }

    @Override
    public String toString() {
        return applicationName;
    }
}
