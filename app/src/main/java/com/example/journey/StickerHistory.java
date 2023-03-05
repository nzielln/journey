package com.example.journey;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.ConditionVariable;
import android.util.Log;

import com.example.journey.Sticker.Constants;
import com.example.journey.Sticker.Message;
import com.example.journey.Sticker.MessageHistoryAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class StickerHistory extends AppCompatActivity {


    private DatabaseReference database;
    private FirebaseAuth userAuthentication;
    private FirebaseUser fbUser;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_view);

        RecyclerView history = (RecyclerView) findViewById(R.id.historyView);

        ArrayList<Message> historyItems = new ArrayList<>();
//        historyItems.add(new HistoryContact("janedoe@email.com", "love", "2/23/2023 10:00PM"));
//        historyItems.add(new HistoryContact("test@email.com", "tired", "2/23/2023 10:00PM"));
//        historyItems.add(new HistoryContact("janedoe@email.com", "boring", "2/23/2023 10:00PM"));

        MessageHistoryAdapter adapter = new MessageHistoryAdapter(this,historyItems);
        history.setAdapter(adapter);

        history.setLayoutManager(new LinearLayoutManager(this));

        userAuthentication = FirebaseAuth.getInstance();
        fbUser = userAuthentication.getCurrentUser();
        database = FirebaseDatabase.getInstance().getReference();
        //DatabaseReference allUsers = FirebaseDatabase.getInstance().getReference().child("users");
        database.child(Constants.USERS_DATABASE_ROOT).child(fbUser.getUid()).child(Constants.MESSAGES_DATABASE_ROOT).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                Log.d("result", "onComplete " + task.isSuccessful());
                if(task.isSuccessful()){
                    DataSnapshot result = task.getResult();
                    for(DataSnapshot dbUser: result.getChildren()) {
                        Log.d("results",dbUser.getValue().toString());
                        Message historyItem = dbUser.getValue(Message.class);
                        historyItems.add(historyItem);
                    }
                    adapter.notifyDataSetChanged();

                }
            }
        });
    }
}