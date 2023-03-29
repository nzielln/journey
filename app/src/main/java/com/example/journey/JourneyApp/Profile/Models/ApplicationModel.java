package com.example.journey.JourneyApp.Profile.Models;

import java.time.LocalDateTime;
import java.util.UUID;

public class ApplicationModel {
    UUID applicationID;
    String applicationName;
    ApplicationStatus status;
    String dateCreated;
    String dateComleted;
    Boolean isCompleted;

    public ApplicationModel(UUID applicationID, String applicationName, ApplicationStatus status, String dateCreated, String dateComleted, Boolean isCompleted) {
        this.applicationID = applicationID;
        this.applicationName = applicationName;
        this.status = status;
        this.dateCreated = dateCreated;
        this.dateComleted = dateComleted;
        this.isCompleted = isCompleted;
    }

    public ApplicationModel(UUID applicationID, String applicationName, String dateCreated) {
        this.applicationID = applicationID;
        this.applicationName = applicationName;
        this.dateCreated = dateCreated;
        this.status = ApplicationStatus.UNKNOWN;
        this.isCompleted = false;

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
        isCompleted = completed;
    }

    public UUID getApplicationID() {
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
        return isCompleted;
    }

    static ApplicationModel getMock() {
        return new ApplicationModel(UUID.randomUUID(), "Fake Application", ApplicationStatus.PROCESSING, Date.);
    }
}
