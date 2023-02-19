package com.example.journey;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Retro {
    String API_KEY = "RiTJ6mX1xCqlaxGegDowwZpJWAnaoVWL";

    // GET requests to get date from API. https://developers.giphy.com/docs/api/schema/#gif-object has more info on what exaclty is happening
    // I'm too tired to type it all out :_ /
    @GET("gifs/trending?")
    Call<GiphyResponseTrending> trendingGiphyResponse(@Query("limit") int limit, @Query("api_key") String api_key);

    // Might need to rewrite these to take in queries instead of concatenating them into the URL
    //@GET("gifs/random?api_key=" + API_KEY + "&random_id={random_id}")
    @GET("gifs/random?")
    Call<GiphyResponseRandom> randomGiphyResponseWithId(@Query("api_key") String api_key, @Query("rating") String rating);

    @GET("gifs/random?api_key=" + API_KEY)
    Call<GiphyResponseTrending> randomGiphyResponseNoId();

    @GET("randomid?api_key=" + API_KEY)
    Call<GiphyResponseTrending> randomIdResponse();

    @GET("gifs/search?q={query}&api_key=" + API_KEY + "&limit=1")
    Call<GiphyResponseTrending> searchGiphyResponse(String query);

}
