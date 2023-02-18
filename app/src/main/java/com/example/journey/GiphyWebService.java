package com.example.journey;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
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

    trending.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        getTrendingGifTest();
      }
    });

  }

  public void setUpRetrofit() {

  }

  public void getTrendingGifTest() {
    retrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    client = retrofit.create(Retro.class);

    Call<Data> retroCall = client.trendingGiphyResponse( 1, API_KEY);

    retroCall.enqueue(new Callback<Data>() {
      @Override
      public void onResponse(Call<Data> call, Response<Data> response) {
        Data data = response.body();
        GiphyResponse res = data.data.get(0);

        gifTitle.setText(res.getTitle());
        image.setImageURI(Uri.parse(res.getImages().getOriginal().url));

      }

      @Override
      public void onFailure(Call<Data> call, Throwable t) {
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