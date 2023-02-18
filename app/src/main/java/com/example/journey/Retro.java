package com.example.journey;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Retro {
    String API_KEY = "RiTJ6mX1xCqlaxGegDowwZpJWAnaoVWL";

    @GET("gifs/trending")
    Call<Data> trendingGiphyResponse(@Query("limit") int limit, @Query("api_key") String api_key);

    @GET("gifs/random?api_key=" + API_KEY + "&random_id={random_id}")
    Call<Data> randomGiphyResponseWithId();

    @GET("gifs/random?api_key=" + API_KEY)
    Call<Data> randomGiphyResponseNoId();

    @GET("randomid?api_key=" + API_KEY)
    Call<Data> randomIdResponse();

    @GET("gifs/search?q={query}&api_key=" + API_KEY + "&limit=1")
    Call<Data> searchGiphyResponse(String query);

}
