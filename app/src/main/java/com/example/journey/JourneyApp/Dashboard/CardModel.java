package com.example.journey.JourneyApp.Dashboard;

import android.widget.ImageView;

import com.example.journey.JourneyApp.Profile.Models.UserModel;
import com.google.firebase.storage.StorageReference;

public class CardModel {

    private String postId;
    private String cardName;
    private String cardDate;
    private String cardPostContentSummary;

    private StorageReference cardImage;

    private Boolean isLiked;


    public CardModel(UserModel cardUser, String cardDate, String cardSummary, StorageReference userPic,String postId, Boolean isLiked) {
        this.cardName = cardUser.getFirstName() + " " + cardUser.getLastName();
        this.cardDate = cardDate;
        this.cardPostContentSummary = cardSummary;
        this.cardImage = userPic;
        this.postId = postId;
        this.isLiked = isLiked;
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

    public String getPostId() {
        return postId;
    }

    public Boolean getLiked() {
        return isLiked;
    }
}

