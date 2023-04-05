package com.example.journey.JourneyApp.Dashboard;

public class CardModel {
    private String cardName;
    private String cardDate;
    private String cardPostContentSummary;

    public CardModel(String cardName, String cardDate, String cardSummary) {
        this.cardName = cardName;
        this.cardDate = cardDate;
        this.cardPostContentSummary = cardSummary;
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
}

