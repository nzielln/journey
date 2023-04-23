package com.example.journey.JourneyApp.Profile.Models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.example.journey.JourneyApp.Main.Helper;

import java.util.ArrayList;
import java.util.Objects;
import java.util.UUID;

public class UserModel {
    String userID;
    String email;
    String firstName;
    String lastName;
    ArrayList<String> chatIDs = new ArrayList<>();
    Integer followers = 0;
    Integer following = 0;
    ArrayList<UserModel> followersList = new ArrayList<>();
    ArrayList<UserModel> followingList = new ArrayList<>();
    String profileImage;
    String status;
    private String lastMessage;
    private Long lastMessageTimeStamp;

    public UserModel() {
    }

    public UserModel(String userID, String email) {
        this.userID = userID;
        this.email = email;
    }

    public UserModel(
            String userID,
            String email,
            String firstName,
            String lastName,
            Integer followers,
            Integer following,
            String profileImage,
            String status
    ) {
        this.userID = userID;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.followers = followers;
        this.following = following;
        this.profileImage = profileImage;
        this.status = status;

    }

    public UserModel(
            String userID,
            String email,
            String firstName,
            String lastName,
            Integer followers,
            Integer following,
            String profileImage
    ) {
        this.userID = userID;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.followers = followers;
        this.following = following;
        this.profileImage = profileImage;

    }

    public UserModel(
            String userID,
            String email,
            String firstName,
            String lastName,
            ArrayList<String> chatIDs,
            Integer followers,
            Integer following,
            String profileImage,
            String status

    ) {
        this.userID = userID;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.chatIDs = chatIDs;
        this.followers = followers;
        this.following = following;
        this.profileImage = profileImage;
        this.status = status;

    }

    //added new UserModel with lastMessage and lastMessageTimestamp
    public UserModel(
            String userID,
            String email,
            String firstName,
            String lastName,
            //Integer followers,
            //Integer following,
            String profileImage,
            String status,
            String lastMessage,
            Long lastMessageTimeStamp
    ) {
        this.userID = userID;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        //this.followers = followers;
        //this.following = following;
        this.profileImage = profileImage;
        this.status = status;
        this.lastMessage = lastMessage;
        this.lastMessageTimeStamp = lastMessageTimeStamp;
    }

    public UserModel(
            String userID,
            String email,
            String firstName,
            String lastName,
            ArrayList<String> chatIDs,
            Integer followers,
            Integer following,
            String profileImage
    ) {
        this.userID = userID;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.chatIDs = chatIDs;
        this.followers = followers;
        this.following = following;
        this.profileImage = profileImage;
    }

    public UserModel(
            String userID,
            String email,
            String firstName,
            String lastName,
            ArrayList<String> chatIDs,
            Integer followers,
            Integer following,
            ArrayList<UserModel> followersList,
            ArrayList<UserModel> followingList,
            String profileImage,
            String status
    ) {
        this.userID = userID;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.chatIDs = chatIDs;
        this.followers = followers;
        this.following = following;
        this.followersList = followersList;
        this.followingList = followingList;
        this.profileImage = profileImage;
        this.status = status;

    }

    public UserModel(
            String userID,
            String email,
            String firstName,
            String lastName,
            ArrayList<String> chatIDs,
            Integer followers,
            Integer following,
            ArrayList<UserModel> followersList,
            String profileImage,
            String status
    ) {
        this.userID = userID;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.chatIDs = chatIDs;
        this.followers = followers;
        this.following = following;
        this.followersList = followersList;
        this.profileImage = profileImage;
        this.status = status;

    }

    public UserModel(
            String userID,
            String email,
            String firstName,
            String lastName,
            ArrayList<String> chatIDs,
            Integer followers,
            Integer following,
            String profileImage,
            ArrayList<UserModel> followingList,
            String status
    ) {
        this.userID = userID;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.chatIDs = chatIDs;
        this.followers = followers;
        this.following = following;
        this.followingList = followingList;
        this.profileImage = profileImage;
        this.status = status;
    }

    public UserModel(
            String userID,
            String email,
            String firstName,
            String lastName,
            ArrayList<String> chatIDs,
            Integer followers,
            Integer following,
            ArrayList<UserModel> followersList,
            ArrayList<UserModel> followingList,
            String profileImage) {
        this.userID = userID;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.chatIDs = chatIDs;
        this.followers = followers;
        this.following = following;
        this.followersList = followersList;
        this.followingList = followingList;
        this.profileImage = profileImage;
    }

    public UserModel(
            String userID,
            String email,
            String firstName,
            String lastName,
            ArrayList<String> chatIDs,
            Integer followers,
            Integer following,
            ArrayList<UserModel> followersList,
            String profileImage) {
        this.userID = userID;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.chatIDs = chatIDs;
        this.followers = followers;
        this.following = following;
        this.followersList = followersList;
        this.profileImage = profileImage;
    }

    public UserModel(
            String userID,
            String email,
            String firstName,
            String lastName,
            ArrayList<String> chatIDs,
            Integer followers,
            Integer following,
            String profileImage,
            ArrayList<UserModel> followingList) {
        this.userID = userID;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.chatIDs = chatIDs;
        this.followers = followers;
        this.following = following;
        this.followingList = followingList;
        this.profileImage = profileImage;
    }

    public void addUserNameDetails(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public void deleteChat(String chatID) {
        this.chatIDs.removeIf(uuid -> Objects.equals(uuid, chatID));
    }

    public void chatID(String chatID) {
        chatIDs.add(chatID);
    }

    public void updateFollowers(boolean increment, UserModel user) {
        if (increment) {
            followers += 1;
        } else {
            followers -= 1;
        }

        updateFollowersList(increment, user);
    }

    public void updateFollowing(boolean increment, UserModel user) {
        if (increment) {
            following += 1;
        } else {
            following -= 1;
        }

        updateFollowingsList(increment, user);
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

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
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

    public void setFollowersList(ArrayList<UserModel> followersList) {
        this.followersList = followersList;
    }

    public void setFollowingList(ArrayList<UserModel> followingList) {
        this.followingList = followingList;
    }

    public ArrayList<UserModel> getFollowersList() {
        return followersList;
    }

    public ArrayList<UserModel> getFollowingList() {
        return followingList;
    }

    public void updateFollowersList(boolean update, UserModel userModel) {
        if (update) {
            followersList.add(userModel);
        } else {
            followersList.removeIf(user -> user.getUserID().equals(userModel.getUserID()));
        }
    }

    public void updateFollowingsList(boolean update, UserModel userModel) {
        if (update) {
            followingList.add(userModel);
        } else {
            followingList.removeIf(user -> user.getUserID().equals(userModel.getUserID()));
        }
    }

    public String getProfileImage() {
        return profileImage;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public Long getLastMessageTimeStamp() {
        return lastMessageTimeStamp;
    }

}
