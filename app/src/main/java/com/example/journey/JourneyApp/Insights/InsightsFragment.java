package com.example.journey.JourneyApp.Insights;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.journey.JourneyApp.Main.Database;
import com.example.journey.JourneyApp.Profile.ProfileToDoFragment;
import com.example.journey.R;
import com.example.journey.databinding.FragmentInsightsBinding;
import com.example.journey.databinding.FragmentProfileBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link InsightsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class InsightsFragment extends Fragment {
    FirebaseAuth firebaseAuth;
    FragmentInsightsBinding binding;

    public InsightsFragment() {
        // Required empty public constructor
    }

    public static InsightsFragment newInstance(String param1, String param2) {
        InsightsFragment fragment = new InsightsFragment();
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentInsightsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        firebaseAuth.signOut();
        getActivity().onBackPressed();
    }

    public void setUpDatabase() {
        firebaseAuth = FirebaseAuth.getInstance(Database.JOURNEYDB);
    }

}