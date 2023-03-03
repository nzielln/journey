package com.example.journey;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {
    private String BASE_URL = "https://jsonplaceholder.typicode.com/";
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();

        // Check if the user is signed in
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser == null) {
            // User is not signed in, go to SignInActivity
            startActivity(new Intent(MainActivity.this, SignInActivity.class));
            finish();
        }

    //    });

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
    public void startSignIn(View view) {
        startActivity(new Intent(MainActivity.this, SigninAuthenticate.class));
    }

    /**
     * The startRealtimeDatabase() method opens up the RealtimeDatabase Activity.
     */
    public void startRealtimeDatabase(View view) {
        startActivity(new Intent(MainActivity.this, RealtimeDatabaseActivity.class));
    }

    /**
     * The onAbout() method opens up the About Activity.
     */
    public void onAbout(View view) {
        startActivity(new Intent(MainActivity.this, About.class));
    }

}
