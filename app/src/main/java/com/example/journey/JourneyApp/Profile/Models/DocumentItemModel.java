package com.example.journey.JourneyApp.Profile.Models;

import java.util.UUID;

public class DocumentItemModel {
    UUID ID;
    UUID userID;
    String title;
    String dateAdded; // ISO String

    public DocumentItemModel() {}
    public DocumentItemModel(String title, String dateAdded) {
        this.title = title;
        this.dateAdded = dateAdded;
    }

    public String getTitle() {
        return title;
    }

    public String getDateAdded() {
        return dateAdded;
    }

    public UUID getID() {
        return ID;
    }
}
