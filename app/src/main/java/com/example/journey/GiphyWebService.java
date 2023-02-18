package com.example.journey;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;

import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GiphyWebService extends AppCompatActivity {
  String API_KEY = "RiTJ6mX1xCqlaxGegDowwZpJWAnaoVWL";

  private Boolean THREAD_RUNNING = false;
  ImageView image;
  TextView gifTitle;
  TextView gifDescription;
  Retrofit retrofit;
  Retro client;
  String BASE_URL = "http://api.giphy.com/v1/";


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_giphy_web_service);

    Button random = findViewById(R.id.random_button);
    Button trending = findViewById(R.id.trending_button);

    TextInputLayout searchInput = findViewById(R.id.gif_search);

    image = findViewById(R.id.gif_image);
    gifTitle = findViewById(R.id.gif_title);
    gifDescription = findViewById(R.id.gif_desc);

    setUpRetrofit();

    random.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        getTrendingGifTest();
      }
    });

  }

  public void setUpRetrofit() {
    OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
    retrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(httpClient.build())
            .build();

    client = retrofit.create(Retro.class);
  }

  public void getTrendingGifTest() {
    Call<GiphyResponse> retroCall = client.trendingGiphyResponse( 1, API_KEY);

    retroCall.enqueue(new Callback<GiphyResponse>() {
      @Override
      public void onResponse(Call<GiphyResponse> call, Response<GiphyResponse> response) {
        System.out.println("Something");
      }

      @Override
      public void onFailure(Call<GiphyResponse> call, Throwable t) {
        t.printStackTrace();
      }
    });


  }


  public void generateRandomGif(View view) {

  }

  public void generateTrendingGif(View view) {

  }

  public void generateGifFromQuery(View view, String search) {

  }
}