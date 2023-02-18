package com.example.journey;

import com.google.gson.annotations.SerializedName;

import java.util.Map;

public class GiphyImage {
    @SerializedName("preview_gif")
    Map<String, Preview> preview;

    public Preview getPreview() {
        return (Preview) preview;
    }

    @Override
    public String toString() {
        return "GiphyImage{" +
                "preview=" + preview +
                '}';
    }
}




