package com.example.journey;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private String BASE_URL = "https://jsonplaceholder.typicode.com/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Retro retro = retrofit.create(Retro.class);

        Call<List<Todo>> todoCall = retro.listTodos();

        todoCall.enqueue(new Callback<List<Todo>>() {
            @Override
            public void onResponse(Call<List<Todo>> call, Response<List<Todo>> response) {
                List<Todo> todos = response.body();
                System.out.println(todos.size());

                for (int i = 0; i < todos.size(); i++) {
                    System.out.println(todos.get(i));
                }
            }

            @Override
            public void onFailure(Call<List<Todo>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Something went wrong", Toast.LENGTH_LONG)
                        .show();
            }
        });

         */

    }

    /**
     * The startGiphyService() method opens up a new activity.
     */
    public void startGiphyService(View view) {
        startActivity(new Intent(MainActivity.this, GiphyWebService.class));
    }

    /**
     * The startRealtimeDatabase() method opens up the RealtimeDatabase Activity.
     */
    public void startRealtimeDatabase(View view) {
        startActivity(new Intent(MainActivity.this, SigninAuthenticate.class));
    }

}