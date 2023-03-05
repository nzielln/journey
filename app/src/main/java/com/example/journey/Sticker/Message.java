package com.example.journey.Sticker;

public class Message {
    private String senderID;
    private String recipientID;
    String senderEmail;
    Integer image;
    String dateTime;

    public Message() {}

    public Message(String email, Integer image, String dateTime, String senderEmailID, String recipientID){
        this.senderEmail = email;
        this.image = image;
        this.dateTime = dateTime;
        this.senderID = senderID;
        this.recipientID = recipientID;
    }

    public String getSenderEmail() {
        return senderEmail;
    }

    public String getSenderID() {
        return senderID;
    }

    public String getRecipientID() {
        return recipientID;
    }

    public Integer getImage() {
        return image;
    }

    public String getDateTime() {
        return dateTime;
    }
}
