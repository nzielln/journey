package com.example.journey.JourneyApp.Dashboard;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.journey.JourneyApp.Main.Database;
import com.example.journey.R;
import com.example.journey.databinding.FragmentDashboardBinding;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DashboardFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DashboardFragment extends Fragment {

    FragmentManager fragmentManager;
    FirebaseUser currentUser;
    RecyclerView recyclerView;
    FragmentDashboardBinding binding;
    static DatabaseReference dbReference;
    DashboardAdapter adapter;


    public DashboardFragment() {
        // Required empty public constructor
    }

    public static DashboardFragment newInstance(String param1, String param2) {
        DashboardFragment fragment = new DashboardFragment();
        getDB();
        return fragment;
    }
     public static void getDB(){
         dbReference = FirebaseDatabase.getInstance(Database.JOURNEYDB).getReference();
     }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        currentUser = Database.FIREBASE_AUTH.getCurrentUser();

        adapter = new DashboardAdapter();
        //recyclerView.setAdapter(adapter);
        //recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

    }
    public void displayFragment(Fragment fragment){
        FragmentTransaction transaction = fragmentManager.beginTransaction();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        //TextView wName = (TextView)findViewById(R.id.welcomeTv);
        //wName.setText(currentUser.getDisplayName());
        return inflater.inflate(R.layout.fragment_dashboard, container, false);
    }

    // This event is triggered soon after onCreateView().
    // Any view setup should occur here.
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){

    }

}