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
    ArrayList<String> applicationsIDs = new ArrayList<>();
    ArrayList<String> chatIDs = new ArrayList<>();
    Integer followers = 0;
    Integer following = 0;
    //String profileImage = "";
    String profileImage;
    String imageURL;
    String status;

    //Constructors
    public UserModel() { }

    public UserModel(String userID, String email) {
        this.userID = userID;
        this.email = email;
    }

    public UserModel(
            String userID,
            String email,
            String username,
            String firstName,
            String lastName,
            Integer followers,
            Integer following,
            String profileImage,
            String imageURL,
            String status
    ) {
        this.userID = userID;
        this.email = email;
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.followers = followers;
        this.following = following;
        this.profileImage = profileImage;
        this.imageURL = imageURL;
        this.status = status;

    }

    public UserModel(
            String userID,
            String email,
            String username,
            String firstName,
            String lastName,
            ArrayList<String> applicationsIDs,
            ArrayList<String> chatIDs,
            Integer followers,
            Integer following,
            String profileImage,
            String imageURL,
            String status
    ) {
        this.userID = userID;
        this.email = email;
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.applicationsIDs = applicationsIDs;
        this.chatIDs = chatIDs;
        this.followers = followers;
        this.following = following;
        this.profileImage = profileImage;
        this.imageURL = imageURL;
        this.status = status;

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


    public String getProfileImage() {
        return profileImage;
    }

    public String getImageURL(){
        return imageURL;
    }

    public void setImageURL(String imageURL){
        this.imageURL = imageURL;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public static UserModel getMockUser() {
        //return new UserModel(Helper.MOCK_USER_ID, "mock@email.com", "jjones", "Jessica", "Jones", 121, 221, "https://firebasestorage.googleapis.com/v0/b/journey-c6761.appspot.com/o/sample.jpeg?alt=media&token=85c5d95e-1c4f-428a-a417-8d119c438ac3");
        return new UserModel(Helper.MOCK_USER_ID, "mock@email.com", "jjones", "Jessica", "Jones", 121, 221, "", "https://firebasestorage.googleapis.com/v0/b/journey-c6761.appspot.com/o/sample.jpeg?alt=media&token=85c5d95e-1c4f-428a-a417-8d119c438ac3", "");
    }
}
