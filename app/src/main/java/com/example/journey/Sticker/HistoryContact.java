package com.example.journey.Sticker;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class HistoryContact {
    String senderEmail;
    String image;
    String dateTime;

    public HistoryContact() {
        senderEmail = "Default email";
        image = "Default image";
        dateTime = "Default dateTime";
    }

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
