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

  // Base url for accessing the API --> we pass this to retrofit
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

    // This is test for handling the trendinf button, the rest of the handling functions should be about the same
    // the only difference is we'll probably need to run them in a thread because API calls use the network and
    // we can't always be confident that we'llg get a response so we can't block the main thread
    trending.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        getTrendingGifTest();
      }
    });



  }

  public void setUpRetrofit() {

  }

  // Setting up retrofit. Ideally, lines 76 -> 80 should only need to be set up once in the setUpRetrofit() method
  // so we can move it there to reduce repetition
  public void getTrendingGifTest() {
    retrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    client = retrofit.create(Retro.class);

    // To call an endpoint in our retro class ("client") for trending, we need to pass a limit and an API_KEY
    Call<Data> retroCall = client.trendingGiphyResponse( 1, API_KEY);

    /*
    HTTP request use a concept that people tend to refer to as a promise/future, basically, an async operation.
    We send a request and then we wait for hear a response back (either success - which means we get our response, or failure - which means we get back an error)
    Usually, when an async operation is running, you want to be able to run other things in your program while you wait for a reponse,
    hence, the enqueue method (don't qoute me on this, not completely sure that this is what's exactly happening in this method but based on how
    async operations are handled in other languages, it should be pretty similar)

    enqueue is basically waiting for a response from the api and then, once it gets a response, it runs the different callbacks we provide for onResponse (success) or onFailure

    When we get an actual response that's not a failure (aka 200) -> we can retrieve the data that we expect to be contained in the response (we know what this is because we've already told our retrofit client what
    information to actually return back. everything else is ignored because we don't need it)

    Then, we can take that response and inject it into our view

    Data -> had a data property -> which is a list of GiphyResponse
    GiphyResponse -> has a title, description?, and images, which is a GiphyImage
    GiphyImage -> has an original (Original) property, which has a height, width and url of our gif. We can use these URL for our image view to adjust the size
    or just stick to the default (i think we should use the h and w returned from the response)

    On failure, right now we're just printing the trace stack but ideal we would want to add a Toast letting the user know that something went wrong and to try again

    THAT'S BASICALLY WHAT'S HAPPENING HERE

     */
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