package com.example.journey.JourneyApp.Profile.Models;

import com.example.journey.JourneyApp.Main.Helper;

import java.text.DateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

public class ApplicationModel {
    String applicationID;
    String applicationName;
    ApplicationStatus status;
    String dateCreated;
    String dateComleted;
    Boolean isCompleted;

    public ApplicationModel(String applicationID, String applicationName, ApplicationStatus status, String dateCreated, String dateComleted, Boolean isCompleted) {
        this.applicationID = applicationID;
        this.applicationName = applicationName;
        this.status = status;
        this.dateCreated = dateCreated;
        this.dateComleted = dateComleted;
        this.isCompleted = isCompleted;
    }
    public ApplicationModel(String applicationID, String applicationName, ApplicationStatus status, String dateCreated) {
        this.applicationID = applicationID;
        this.applicationName = applicationName;
        this.status = status;
        this.dateCreated = dateCreated;
    }

    public ApplicationModel(String applicationID, String applicationName, String dateCreated) {
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
        return isCompleted;
    }

    public static ApplicationModel getMock() {
        Date date = new Date();
        String applicationName = "Fake Application ID-" + UUID.randomUUID().toString();
        return new ApplicationModel(UUID.randomUUID().toString(), applicationName, ApplicationStatus.PROCESSING, Helper.getLongDateTime());
    }
}
