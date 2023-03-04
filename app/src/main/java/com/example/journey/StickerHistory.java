package com.example.journey;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.example.journey.Sticker.HistoryContact;
import com.example.journey.Sticker.HistoryContactAdapter;

import java.util.ArrayList;

public class StickerHistory extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_view);

        RecyclerView history = (RecyclerView) findViewById(R.id.historyView);

        ArrayList<HistoryContact> historyItems = new ArrayList<>();
        historyItems.add(new HistoryContact("janedoe@email.com", "love", "2/23/2023 10:00PM"));
        historyItems.add(new HistoryContact("test@email.com", "tired", "2/23/2023 10:00PM"));
        historyItems.add(new HistoryContact("janedoe@email.com", "boring", "2/23/2023 10:00PM"));

        HistoryContactAdapter adapter = new HistoryContactAdapter(this,historyItems);
        history.setAdapter(adapter);

        history.setLayoutManager(new LinearLayoutManager(this));
    }
}