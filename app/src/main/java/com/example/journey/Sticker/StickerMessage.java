package com.example.journey.Sticker;

import java.util.Date;

public class StickerMessage {
    private String senderID;
    private String recipientID;
    private Integer message;
    private String time;

    public StickerMessage() {}

    public StickerMessage(String senderID, String recipientID, Integer message, String time) {
        this.senderID = senderID;
        this.recipientID = recipientID;
        this.message = message;
        this.time = time;
    }
    public String getSenderID() {
        return senderID;
    }
    public void setSenderID(String senderID) {
        this.senderID = senderID;
    }
    public String getRecipientID() {
        return recipientID;
    }
    public void setRecipientID(String recipientID) {
        this.recipientID = recipientID;
    }
    public Integer getMessage() {
        return message;
    }
    public void setMessage(Integer message) {
        this.message = message;
    }

    public String getTime() {
        return time;
    }

    @Override
    public String toString() {
        return "StickerMessage{" +
                "senderID='" + senderID + '\'' +
                ", recipientID='" + recipientID + '\'' +
                ", message='" + message + '\'' +
                ", time=" + time +
                '}';
    }
}