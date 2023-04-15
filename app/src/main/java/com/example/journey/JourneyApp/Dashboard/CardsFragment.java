package com.example.journey.JourneyApp.Dashboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.journey.JourneyApp.Main.Database;
import com.example.journey.R;
import com.example.journey.databinding.DashboardRvBinding;
import com.example.journey.databinding.FragmentDashboardBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class CardsFragment extends Fragment {
    private DatabaseReference dbReference;
    private FirebaseAuth userAuth;

    private FirebaseUser user;
    DashboardRvBinding binding;
    private DashboardAdapter adapter;
    RecyclerView recyclerView;
    ArrayList<CardModel> items = new ArrayList<>();

    public CardsFragment() {
        super(R.layout.dashboard_rv);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbReference = FirebaseDatabase.getInstance(Database.JOURNEYDB).getReference();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = DashboardRvBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        recyclerView = binding.dashboardRecyclerView;
        //dbReference = dbReference.child("post");
        items.add(new CardModel("Samantha Greene","Monday 6:30PM","Hi this is my first post"));
        items.add(new CardModel("Boe Jones","Monday 6:31PM","Hi this is my first post"));
        items.add(new CardModel("Tasha Mac","Monday 7:00PM","Hi this is my first post"));

        adapter = new DashboardAdapter(items);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

    }

}
