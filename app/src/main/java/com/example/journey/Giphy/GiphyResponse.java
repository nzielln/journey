package com.example.journey.Giphy;

import com.example.journey.Giphy.GiphyImage;
import com.google.gson.annotations.SerializedName;

// This is just an object to prepresent a reponse from the API - we only care about the title, desc and associated image
public class GiphyResponse {

    String title;
    @SerializedName("alt_text")
    String description;

    @SerializedName("username")
    String username;
    @SerializedName("images")
    GiphyImage images;


    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public GiphyImage getImages() {
        return images;
    }

    public String getUsername() {
        return username;
    }

    @Override
    public String toString() {
        return "GiphyResponse{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", image=" + images +
                '}';
    }
}


