package com.example.journey;

import androidx.appcompat.app.AppCompatActivity;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView.OnEditorActionListener;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Collection;


import com.bumptech.glide.Glide;
import com.google.android.material.textfield.TextInputLayout;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.ResponseBody;
import okio.Buffer;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import com.google.android.material.textfield.TextInputEditText;



public class GiphyWebService extends AppCompatActivity {
  String API_KEY = "RiTJ6mX1xCqlaxGegDowwZpJWAnaoVWL";

  private Boolean THREAD_RUNNING = false;
  ImageView image;
  TextView gifTitle;
  TextView gifDescription;
  Retrofit retrofit;
  Retro client;
  GiphyWebService g = this;

  // Base url for accessing the API --> we pass this to retrofit
  //String BASE_URL = "http://api.giphy.com/v1/";

  String BASE_URL = "https:api.giphy.com/v1/";   // changed http to https because of cleartext error
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_giphy_web_service);

    Button random = findViewById(R.id.random_button);
    Button trending = findViewById(R.id.trending_button);
    TextInputLayout searchInput = findViewById(R.id.gif_search);
    //TextInputEditText searchInput = findViewById(R.id.gif_search);
    //EditText searchInput = findViewById(R.id.gif_search);

    image = findViewById(R.id.gif_image);
    gifTitle = findViewById(R.id.gif_title);
    gifDescription = findViewById(R.id.gif_desc);

    setUpRetrofit();


    trending.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        generateTrendingGif(v);

      }
    });

    random.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        generateRandomGif(v);

      }
    });

    /*searchInput.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        String query = searchInput.getEditText().getText().toString();
        generateGifFromQuery(v, query);

      }
    });
    */

    searchInput.getEditText().setOnEditorActionListener(new TextView.OnEditorActionListener() {
      @Override
      public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
          String query = searchInput.getEditText().getText().toString();
         // generateGifFromQuery(getWindow().getDecorView().getRootView(), query);
          generateGifFromQuery(v, query);
          return true;
        }
        return false;
      }
    });

    searchInput.getEditText().addTextChangedListener(new TextWatcher() {
      @Override
      public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        // This method is called to notify you that, within s, the count characters beginning
        // at start are about to be replaced by new text with length after.
      }

      @Override
      public void onTextChanged(CharSequence s, int start, int before, int count) {
        // This method is called to notify you that, within s, the count characters beginning
        // at start have just replaced old text that had length before.
      }

      @Override
      public void afterTextChanged(Editable s) {
        // This method is called to notify you that, somewhere within s, the text has been changed.
        String searchQuery = s.toString().trim();
        // Now you can use searchQuery to perform a search
        // e.g., you could call a method to initiate a search with this query
        // performSearch(searchQuery);
      }
    });
  }

  /**
   * Retrofit Builder - A HTTP client for Android
   */
  public void setUpRetrofit() {
    retrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    client = retrofit.create(Retro.class);

  }

  /**
   * generateRandomGif - Gets a giphy response for a Random gif
   * @param view
   */
  public void generateRandomGif(View view) {
    Call<GiphyResponseRandom> retroCall = client.randomGiphyResponseWithId(API_KEY,"pg");
    //Log.d("RetroCall", retroCall.toString());
    retroCall.enqueue(new Callback<GiphyResponseRandom>() {
      @Override
      public void onResponse(Call<GiphyResponseRandom> call, Response<GiphyResponseRandom> response) {
        GiphyResponse data = response.body().data;
        //Log.d("data",data.toString());

        GiphyResponse res = data;
        //Log.d("GiphyResponse", res.getImages().getOriginal().getUrl());
        gifTitle.setText(res.getTitle());
        gifDescription.setText(res.getDescription());
        String url = res.getImages().getOriginal().getUrl();
        //Log.d("URL",url.toString());
        Glide.with(g).load(url).into(image);
      }

      @Override
      public void onFailure(Call<GiphyResponseRandom> call, Throwable t) {
        t.printStackTrace();
      }
    });

  }
  /**
   * generateTrendingGif - Gets a giphy response for the Top trending gif
   * @param view
   */

  public void generateTrendingGif(View view) {
    Call<GiphyResponseTrending> retroCall = client.trendingGiphyResponse( 1, API_KEY);
    //Log.d("RetroCall", retroCall.toString());
    retroCall.enqueue(new Callback<GiphyResponseTrending>() {
      @Override
      public void onResponse(Call<GiphyResponseTrending> call, Response<GiphyResponseTrending> response) {

        GiphyResponseTrending data = response.body();
        GiphyResponse res = data.data.get(0);

        //Log.d("GiphyResponseTrending", res.getImages().getOriginal().getUrl());
        gifTitle.setText(res.getTitle());
        gifDescription.setText(res.getDescription());
        String url = res.getImages().getOriginal().getUrl();
        //Log.d("URL",url.toString());
        Glide.with(g).load(url).into(image);
      }
      @Override
      public void onFailure(Call<GiphyResponseTrending> call, Throwable t) {
        t.printStackTrace();
      }
    });
  }

  public void generateGifFromQuery(View view, String search) {
    //Call<Put Something Here> retroCall = client.searchGiphyResponse(searchInput);
    Call<GiphyResponseSearch> retroCall = client.searchGiphyResponse(API_KEY, search, 1);
    retroCall.enqueue(new Callback<GiphyResponseSearch>() {
      @Override
      public void onResponse(Call<GiphyResponseSearch> call, Response<GiphyResponseSearch> response) {
        GiphyResponseSearch data = response.body();
        if (data == null || data.data.isEmpty()) {
          // if (data == null || data.data.size() == 0) {
          runOnUiThread(new Runnable() {
            @Override
            public void run() {
              Toast.makeText(getApplicationContext(), "No GIFs found for the search query: " + search, Toast.LENGTH_SHORT).show();
            }
          });
          return;
        }

        GiphyResponse res = data.data.get(0);
        gifTitle.setText(res.getTitle());
        gifDescription.setText(res.getUsername());
        String url = res.getImages().getOriginal().getUrl();
        Glide.with(g).load(url).into(image);
      }

      @Override
      public void onFailure(Call<GiphyResponseSearch> call, Throwable t) {
        t.printStackTrace();
      }
    });
  }

}