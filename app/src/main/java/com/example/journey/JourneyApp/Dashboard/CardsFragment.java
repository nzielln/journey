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
        //return getView();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        //recyclerView = binding.dashboardRvContaine;
        recyclerView = binding.dashboardRecyclerView;

        //adapter = new DashboardAdapter(items);
        adapter = new DashboardAdapter(items);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

    }
        //RecyclerView recyclerV = (RecyclerView) findViewById(R.id.dashboard_recycler_view);
//        ArrayList<CardModel> recyclerVItems = new ArrayList<>();
//        DashboardAdapter adapter = new DashboardAdapter();
//        //recyclerV.setAdapter(adapter);
//
//        userAuth = FirebaseAuth.getInstance();
//        user = userAuth.getCurrentUser();
//        database = FirebaseDatabase.getInstance().getReference();
//
//        //database.child("posts").get(DataSnapshot);
//        database = database.child("posts");

//        adapter = new DashboardAdapter();
//
//
//        View view = inflater.inflate(R.layout.fragment_dashboard,container, false);
        //recyclerView.;


}
