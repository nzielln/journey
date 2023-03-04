package com.example.journey;

/*
Reference/Source: Simple Chat App In Android Studio Using Firebase Realtime Database
Android Studio | Code The World - Develop with Ishfaq
 */

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.journey.Giphy.GiphyWebService;
import com.example.journey.databinding.ActivityChatBinding;
import com.example.journey.databinding.ActivityMainBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    private String BASE_URL = "https://jsonplaceholder.typicode.com/";
    ActivityChatBinding binding;
    DatabaseReference databaseReference;

    UserAdapter userAdapter;
    String users;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityChatBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        userAdapter=new UserAdapter(this);
        binding.recycler.setAdapter(userAdapter);
        binding.recycler.setLayoutManager(new LinearLayoutManager(this));

        databaseReference = FirebaseDatabase.getInstance().getReference(users);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Object clear = userAdapter.clear;
                for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                    String uid=dataSnapshot.getKey();
                    if(!uid.equals(FirebaseAuth.getInstance().getUid())){
                        UserModel userModel =dataSnapshot.child(uid).getValue(UserModel.class);
                        userAdapter.add(userModel);


                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


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

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
       /* MainInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);*/
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item){

        if (item.getItemId()==R.id.logout) {
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(MainActivity.this, AuthenticationActivity.class));
            finish();
            return true;
        }
        return false;
    }



    /**
     * The startGiphyService() method opens up a new activity.
     */
    public void startGiphyService(View view) {
        startActivity(new Intent(MainActivity.this, GiphyWebService.class));
    }


    /**
     * The onAbout() method opens up the About Activity.
     */
    public void onAbout(View view) {
        startActivity(new Intent(MainActivity.this, About.class));
    }

}
