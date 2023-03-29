package com.example.journey.JourneyApp.Profile.Models;

import java.util.ArrayList;
import java.util.UUID;

public class UserModel {
    UUID userID;
    String email;
    String username;
    String firstName;
    String lastName;
    Integer age;
    ArrayList<UUID> applicationsIDs = new ArrayList<>();
    ArrayList<UUID> chatIDs = new ArrayList<>();
    Integer followers = 0;
    Integer following = 0;
    UUID settingsID;
    String profileImage;

    public UserModel(UUID userID, String email) {
        this.userID = userID;
        this.email = email;
        this.settingsID = UUID.randomUUID();
    }

    public UserModel(
            UUID userID,
            String email,
            String username,
            String firstName,
            String lastName,
            Integer age,
            ArrayList<UUID> applicationsIDs,
            ArrayList<UUID> chatIDs,
            Integer followers,
            Integer following,
            UUID settingsID,
            String profileImage
    ) {
        this.userID = userID;
        this.email = email;
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.applicationsIDs = applicationsIDs;
        this.chatIDs = chatIDs;
        this.followers = followers;
        this.following = following;
        this.settingsID = settingsID;
        this.profileImage = profileImage;
    }

    public void addUserNameDetails(String firstName, String lastName) {
        this.firstName= firstName;
        this.lastName = lastName;
    }

    public void addApplicationID(UUID applicationsID) {
        this.applicationsIDs.add(applicationsID);
    }


    public void deleteApplication(UUID applicationsID) {
        this.applicationsIDs.removeIf(uuid -> uuid == applicationsID);
    }


    public void deleteChat(UUID chatID) {
        this.chatIDs.removeIf(uuid -> uuid == chatID);
    }

    public void chatID(UUID chatID) {
        chatIDs.add(chatID );
    }

    public void updateFollowers(boolean increment) {
        if (increment) {
            followers++;
        }

        followers--;
    }

    public void updateFollowing(boolean increment) {
        if (increment) {
            following++;
        }

        following--;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public UUID getUserID() {
        return userID;
    }

    public String getEmail() {
        return email;
    }

    public String getUsername() {
        return username;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Integer getAge() {
        return age;
    }

    public ArrayList<UUID> getApplicationsIDs() {
        return applicationsIDs;
    }

    public ArrayList<UUID> getChatIDs() {
        return chatIDs;
    }

    public Integer getFollowers() {
        return followers;
    }

    public Integer getFollowing() {
        return following;
    }

    public UUID getSettingsID() {
        return settingsID;
    }

    public String getProfileImage() {
        return profileImage;
    }
}
