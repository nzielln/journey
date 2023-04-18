package com.example.journey.JourneyApp.Dashboard;

import android.widget.ImageView;

import com.example.journey.JourneyApp.Profile.Models.UserModel;
import com.google.firebase.storage.StorageReference;

public class CardModel {
    private String cardName;
    private String cardDate;
    private String cardPostContentSummary;

    private StorageReference cardImage;

    public CardModel(UserModel cardUser, String cardDate, String cardSummary, StorageReference userPic) {
        this.cardName = cardUser.getFirstName() + " " + cardUser.getLastName();
        this.cardDate = cardDate;
        this.cardPostContentSummary = cardSummary;
        this.cardImage = userPic;
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
}

