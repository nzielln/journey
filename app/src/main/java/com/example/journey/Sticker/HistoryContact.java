package com.example.journey.Sticker;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class HistoryContact {
    private String senderEmail;
    private String image;
    private String dateTime;

    public HistoryContact(String email, String image, String dateTime){
        this.senderEmail = email;
        this.image = image;
        this.dateTime = dateTime;
    }
    public String getSenderEmail(){
        return senderEmail;
    }
    public String getImage(){
        return image;
    }
    public String getDateTime(){
        return dateTime;
    }
}
