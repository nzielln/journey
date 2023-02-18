package com.example.journey;

import com.google.gson.annotations.SerializedName;

import java.util.Map;

public class GiphyImage {
    Original original;

    public Original getOriginal() {
        return original;
    }

    @Override
    public String toString() {
        return "GiphyImage{" +
                "preview=" + original +
                '}';
    }
}




