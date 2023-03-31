package com.example.journey.JourneyApp.Profile.Models;

import com.example.journey.JourneyApp.Main.Helper;

import java.util.UUID;

public class DocumentItemModel {
    String documentID;
    String userID;
    String title;
    String dateAdded; // ISO String

    public DocumentItemModel() {}
    public DocumentItemModel(String documentID, String title, String dateAdded) {
        this.documentID = documentID;
        this.title = title;
        this.dateAdded = dateAdded;

    }

    public String getTitle() {
        return title;
    }

    public String getDateAdded() {
        return dateAdded;
    }

    public String getDocumentID() {
        return documentID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public static DocumentItemModel getMockDocument() {
        String documentTitle = "Some fake document title and random ID-" + UUID.randomUUID().toString();
        DocumentItemModel document = new DocumentItemModel(UUID.randomUUID().toString(), documentTitle, Helper.getShortDate());
        document.setUserID(Helper.MOCK_USER_ID);
        return document;
    }
}
