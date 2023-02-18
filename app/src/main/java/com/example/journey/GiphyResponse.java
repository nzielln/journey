package com.example.journey;

import com.google.gson.annotations.SerializedName;

import java.util.Map;

public class GiphyResponse {

    String title;
    @SerializedName("alt_text")
    String description;
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

    @Override
    public String toString() {
        return "GiphyResponse{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", image=" + images +
                '}';
    }
}


