package com.example.journey.JourneyApp.Profile.Models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.example.journey.JourneyApp.Main.Helper;

import java.util.ArrayList;
import java.util.Objects;
import java.util.UUID;

public class UserModel implements Parcelable {
    String userID;
    String email;
    String username;
    String firstName;
    String lastName;
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
        this.chatIDs = chatIDs;
        this.followers = followers;
        this.following = following;
        this.profileImage = profileImage;
        this.imageURL = imageURL;
        this.status = status;

    }

    protected UserModel(Parcel in) {
        userID = in.readString();
        email = in.readString();
        username = in.readString();
        firstName = in.readString();
        lastName = in.readString();
        chatIDs = in.createStringArrayList();
        if (in.readByte() == 0) {
            followers = null;
        } else {
            followers = in.readInt();
        }
        if (in.readByte() == 0) {
            following = null;
        } else {
            following = in.readInt();
        }
        profileImage = in.readString();
    }

    public static final Creator<UserModel> CREATOR = new Creator<UserModel>() {
        @Override
        public UserModel createFromParcel(Parcel in) {
            return new UserModel(in);
        }

        @Override
        public UserModel[] newArray(int size) {
            return new UserModel[size];
        }
    };

    public void addUserNameDetails(String firstName, String lastName) {
        this.firstName= firstName;
        this.lastName = lastName;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(userID);
        dest.writeString(email);
        dest.writeString(username);
        dest.writeString(firstName);
        dest.writeString(lastName);
        dest.writeStringList(chatIDs);
        if (followers == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(followers);
        }
        if (following == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(following);
        }
        dest.writeString(profileImage);
    }
}
