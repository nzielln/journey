package com.example.journey.JourneyApp.Dashboard;

import android.widget.ImageView;

import com.example.journey.JourneyApp.Profile.Models.UserModel;
import com.google.firebase.storage.StorageReference;

public class CardModel {
    private String cardName;
    private String cardDate;
    private String cardPostContentSummary;
    private StorageReference cardImage;
    UserModel userModel;

    public CardModel(UserModel cardUser, String cardDate, String cardSummary, StorageReference userPic, UserModel userModel) {
        this.cardName = cardUser.getFirstName() + " " + cardUser.getLastName();
        this.cardDate = cardDate;
        this.cardPostContentSummary = cardSummary;
        this.cardImage = userPic;
        this.userModel = userModel;
    }

    public String getCardName() {
        return cardName;
    }

    public String getCardDate() {
        return cardDate;
    }

    public String getCardSummary() {
        return cardPostContentSummary;
    }

    public StorageReference getCardImage() {
        return cardImage;
    }

    public UserModel getUserModel() {
        return userModel;
    }
}

