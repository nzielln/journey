package com.example.journey.JourneyApp.Profile.Models;

import com.example.journey.JourneyApp.Main.Helper;

import java.util.ArrayList;
import java.util.Objects;
import java.util.UUID;

public class UserModel {
    String userID;
    String email;
    String username;
    String firstName;
    String lastName;
    Integer age;
    ArrayList<String> applicationsIDs = new ArrayList<>();
    ArrayList<String> chatIDs = new ArrayList<>();
    Integer followers = 0;
    Integer following = 0;
    String settingsID;
    String profileImage;

    public UserModel(String userID, String email) {
        this.userID = userID;
        this.email = email;
        this.settingsID = UUID.randomUUID().toString();
    }

    public UserModel(
            String userID,
            String email,
            String username,
            String firstName,
            String lastName,
            Integer age,
            Integer followers,
            Integer following,
            String profileImage
    ) {
        this.userID = userID;
        this.email = email;
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.followers = followers;
        this.following = following;
        this.profileImage = profileImage;
        this.settingsID = UUID.randomUUID().toString();

    }

    public UserModel(
            String userID,
            String email,
            String username,
            String firstName,
            String lastName,
            Integer age,
            ArrayList<String> applicationsIDs,
            ArrayList<String> chatIDs,
            Integer followers,
            Integer following,
            String settingsID,
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

    public void addApplicationID(String applicationsID) {
        this.applicationsIDs.add(applicationsID);
    }


    public void deleteApplication(String applicationsID) {
        this.applicationsIDs.removeIf(uuid -> Objects.equals(uuid, applicationsID));
    }


    public void deleteChat(String chatID) {
        this.chatIDs.removeIf(uuid -> Objects.equals(uuid, chatID));
    }

    public void chatID(String chatID) {
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

    public String getUserID() {
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

    public ArrayList<String> getApplicationsIDs() {
        return applicationsIDs;
    }

    public ArrayList<String> getChatIDs() {
        return chatIDs;
    }

    public Integer getFollowers() {
        return followers;
    }

    public Integer getFollowing() {
        return following;
    }

    public String getSettingsID() {
        return settingsID;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public static UserModel getMockUser() {

        return new UserModel(Helper.MOCK_USER_ID, "mock@email.com", "jjones", "Jessica", "Jones", 25, 121, 221, "https://firebasestorage.googleapis.com/v0/b/journey-c6761.appspot.com/o/sample.jpeg?alt=media&token=85c5d95e-1c4f-428a-a417-8d119c438ac3");
    }
}
