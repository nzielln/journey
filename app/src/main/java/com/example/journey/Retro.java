package com.example.journey;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Retro {

    @GET("gifs/trending")
    Call<GiphyResponse> trendingGiphyResponse(@Query("limit") int limit, @Query("api_key") String api_key);

    @GET("gifs/random")
    Call<GiphyResponse> randomGiphyResponseWithId();

    @GET("gifs/random")
    Call<GiphyResponse> randomGiphyResponseNoId();

    @GET("randomid")
    Call<GiphyResponse> randomIdResponse();

    @GET("gifs/search")
    Call<GiphyResponse> searchGiphyResponse(String query);

}
