package com.example.journey;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Retro {
    // GET requests to get date from API. https://developers.giphy.com/docs/api/schema/#gif-object has more info on what exaclty is happening
    @GET("gifs/trending?")
    Call<GiphyResponseTrending> trendingGiphyResponse(@Query("limit") int limit, @Query("api_key") String api_key, @Query("offset") int offset);


    @GET("gifs/random?")
    Call<GiphyResponseRandom> randomGiphyResponseWithId(@Query("api_key") String api_key, @Query("rating") String rating);

    @GET("gifs/search")
    Call<GiphyResponseSearch> searchGiphyResponse(@Query("api_key") String apiKey,
                                                  @Query("q") String searchQuery,
                                                  @Query("limit") int limit,
                                                  @Query("offset") int offset);

}
