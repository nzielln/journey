package com.example.journey.Giphy;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Handler;
import android.content.Context;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.journey.R;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GiphyWebService extends AppCompatActivity {
  String API_KEY = "RiTJ6mX1xCqlaxGegDowwZpJWAnaoVWL";
  String defaultusername = "No username provided";
  ImageView image;
  TextView gifTitle;
  TextView gifUsername;
  Retrofit retrofit;
  Retro client;
  GiphyWebService g = this;

  ProgressBar loadingBar;

  TextInputLayout searchInput;
  final Handler GIPHY_HANDLER = new Handler();
  String BASE_URL = "https:api.giphy.com/v1/";   // changed http to https because of cleartext error
  Boolean RUN_THREAD = true;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_giphy_web_service);

    Button random = findViewById(R.id.random_button);
    Button searchButton = findViewById(R.id.search_button);
    Button trending = findViewById(R.id.trending_button);

    searchInput = findViewById(R.id.gif_search);
    image = findViewById(R.id.gif_image);
    gifTitle = findViewById(R.id.gif_title);
    gifUsername = findViewById(R.id.gif_desc);

    loadingBar = findViewById(R.id.loadingCircle);

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

    searchButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        generateSearchingGif(v, String.valueOf(searchInput.getEditText().getText()));
      }
    });

    searchInput.getEditText().setOnEditorActionListener(new EditText.OnEditorActionListener() {
      @Override
      public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_DONE) {
          searchButton.performClick();
          return false;
        }
        return false;
      }
  }
    );
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
    showLoadingView();

    RandomThread thread = new RandomThread();
    new Thread(thread).start();
  }

  class RandomThread implements Runnable {
    @Override
    public void run() {
      Call<GiphyResponseRandom> retroCall = client.randomGiphyResponseWithId(API_KEY,"pg");

      retroCall.enqueue(new Callback<GiphyResponseRandom>() {
        @Override
        public void onResponse(Call<GiphyResponseRandom> call, Response<GiphyResponseRandom> response) {
          GiphyResponse data = response.body().data;
          GiphyResponse res = data;

          GIPHY_HANDLER.post(new Runnable() {
            @Override
            public void run() {
              if(res.getTitle() != null) {
                loadingBar.setVisibility(View.GONE);
              }
              try {
                Thread.sleep(1000
                );
              } catch (InterruptedException e) {
                e.printStackTrace();
              }
              setViewComponents(res.getTitle(), res.getUsername(), res.getImages().getOriginal().getUrl());
            }
          });
        }

        @Override
        public void onFailure(Call<GiphyResponseRandom> call, Throwable t) {
          t.printStackTrace();
        }
      });
    }
  }

  /**
   * generateRandomGif - Gets a giphy response for a Random gif
   * @param view
   */
  public void generateTrendingGif(View view) {
    RUN_THREAD = true;
    showLoadingView();

    TrendingThread thread = new TrendingThread();
    new Thread(thread).start();
  }

  class TrendingThread implements Runnable {
    @Override
    public void run() {
      while (RUN_THREAD) {
        Call<GiphyResponseTrending> retroCall = client.trendingGiphyResponse(1, API_KEY, getOffset());

        retroCall.enqueue(new Callback<GiphyResponseTrending>() {
          @Override
          public void onResponse(Call<GiphyResponseTrending> call, Response<GiphyResponseTrending> response) {
            GiphyResponseTrending data = response.body();
            if (data.data.size() <= 0) {
              return;
            }

            GiphyResponse res = data.data.get(0);

            GIPHY_HANDLER.post(new Runnable() {
              @Override
              public void run() {
                if(res.getTitle() != null) {
                  loadingBar.setVisibility(View.GONE);
                }
                try {
                  Thread.sleep(1000
                  );
                } catch (InterruptedException e) {
                  e.printStackTrace();
                }
                setViewComponents(res.getTitle(), res.getUsername(), res.getImages().getOriginal().getUrl());
              }
            });

          }
          @Override
          public void onFailure(Call<GiphyResponseTrending> call, Throwable t) {
            t.printStackTrace();
          }
        });
        RUN_THREAD = false;
      }
    }
  }

  /**
   * generateRandomGif - Gets a giphy response for a Random gif
   * @param view
   */
  public void generateSearchingGif(View view, String search) {
    Objects.requireNonNull(searchInput.getEditText()).clearFocus();
    InputMethodManager imm =(InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

    showLoadingView();

    SearchThread thread = new SearchThread(search);
    new Thread(thread).start();
  }

  public void showLoadingView() {
    image.setImageDrawable(null);
    loadingBar.setVisibility(View.VISIBLE);
  }

  class SearchThread implements Runnable {
    String search;

    public SearchThread(String search) {
      this.search = search;
    }
    @Override
    public void run() {
       Call<GiphyResponseSearch> retroCall = client.searchGiphyResponse(API_KEY, search, 1, getOffset());

       retroCall.enqueue(new Callback<GiphyResponseSearch>() {
         @Override
         public void onResponse(Call<GiphyResponseSearch> call, Response<GiphyResponseSearch> response) {
           GiphyResponseSearch data = response.body();
           if (data == null || data.data.isEmpty()) {
             runOnUiThread(new Runnable() {
               @Override
               public void run() {
                 Toast.makeText(getApplicationContext(), "No GIFs found for the search query: " + search, Toast.LENGTH_SHORT).show();
               }
             });
             return;
           }
           GiphyResponse res = data.data.get(0);

           GIPHY_HANDLER.post(new Runnable() {
             @Override
             public void run() {
               if(res.getTitle() != null) {
                 loadingBar.setVisibility(View.GONE);
               }
               try {
                 Thread.sleep(1000
                 );
               } catch (InterruptedException e) {
                 e.printStackTrace();
               }
               setViewComponents(res.getTitle(), res.getUsername(), res.getImages().getOriginal().getUrl());
             }
           });
         }
         @Override
         public void onFailure(Call<GiphyResponseSearch> call, Throwable t) {
           t.printStackTrace();
         }
       });

    }
  }

  public int getOffset() {
    Random rand = new Random();
    return rand.nextInt(4999);

  }

  public void setViewComponents(String title, String username, String url) {
    gifTitle.setText(title);
    gifUsername.setText(username != null && username.length() > 0 ? username : defaultusername);
    Glide.with(g).load(url).into(image);
  }
}