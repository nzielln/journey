package com.example.journey.JourneyApp.Dashboard;

public class CardModel {
    private String cardName;
    private String cardDate;
    private String cardSummary;

    public CardModel(String cardName, String cardDate, String cardSummary) {
        this.cardName = cardName;
        this.cardDate = cardDate;
        this.cardSummary = cardSummary;
    }

    public String getCardName() {
        return cardName;
    }

    public String getCardDate() {
        return cardDate;
    }

    public String getCardSummary() {
        return cardSummary;
    }
}

