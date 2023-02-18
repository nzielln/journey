package com.example.journey;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Retro {
    String API_KEY = "RiTJ6mX1xCqlaxGegDowwZpJWAnaoVWL";

    // GET requests to get date from API. https://developers.giphy.com/docs/api/schema/#gif-object has more info on what exaclty is happening
    // I'm too tired to type it all out :_ /
    @GET("gifs/trending")
    Call<Data> trendingGiphyResponse(@Query("limit") int limit, @Query("api_key") String api_key);

    // Might need to rewrite these to take in queries instead of concatenating them into the URL
    @GET("gifs/random?api_key=" + API_KEY + "&random_id={random_id}")
    Call<Data> randomGiphyResponseWithId();

    @GET("gifs/random?api_key=" + API_KEY)
    Call<Data> randomGiphyResponseNoId();

    @GET("randomid?api_key=" + API_KEY)
    Call<Data> randomIdResponse();

    @GET("gifs/search?q={query}&api_key=" + API_KEY + "&limit=1")
    Call<Data> searchGiphyResponse(String query);

}
