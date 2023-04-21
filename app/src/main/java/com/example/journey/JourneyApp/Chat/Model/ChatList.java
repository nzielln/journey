package com.example.journey.JourneyApp.Chat.Model;

public class ChatList {
    private String id;

    public ChatList (String id){
        this.id = id;
    }

    public ChatList (){
        //Empty Constructor
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}