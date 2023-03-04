package com.example.journey.Giphy;

import com.example.journey.Giphy.GiphyResponse;

import java.util.List;

// This is just an object to prepresent a reponse from the API

public class GiphyResponseTrending {

    public List<GiphyResponse> data;

    @Override
    public String toString() {
        return "Data{" +
                "data=" + data +
                '}';
    }
}
