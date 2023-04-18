package com.example.journey.JourneyApp.Profile;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.journey.JourneyApp.Main.Database;
import com.example.journey.JourneyApp.Profile.Adapters.ProfileDocumentRecyclerViewAdapter;
import com.example.journey.JourneyApp.Profile.Models.DocumentItemModel;
import com.example.journey.R;
import com.example.journey.databinding.FragmentProfileDocumentsBinding;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileDocumentsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileDocumentsFragment extends Fragment {

    FragmentProfileDocumentsBinding binding;
    RecyclerView recyclerView;
    ProfileDocumentRecyclerViewAdapter adapter;

    ArrayList<DocumentItemModel> documents = new ArrayList<>();
    DatabaseReference databaseReference;

    public ProfileDocumentsFragment() {
        super(R.layout.fragment_profile_documents);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setUpDatabase();
        Bundle bundle = new Bundle();

    }

    public void setUpDatabase() {
        databaseReference = FirebaseDatabase.getInstance(Database.JOURNEYDB).getReference();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentProfileDocumentsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = binding.documentsRecyclerView;
        createMockDocuments();
        createRecyclerView();
    }

    private void createMockDocuments() {
        documents.add(DocumentItemModel.getMockDocument());
        documents.add(DocumentItemModel.getMockDocument());
        documents.add(DocumentItemModel.getMockDocument());
        documents.add(DocumentItemModel.getMockDocument());
    }

    public void createRecyclerView() {
        adapter = new ProfileDocumentRecyclerViewAdapter(documents);

        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }
}