package com.example.journey.JourneyApp.Dashboard;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.journey.JourneyApp.Main.Database;
import com.example.journey.JourneyApp.Profile.Models.UserModel;
import com.example.journey.R;
import com.example.journey.databinding.FragmentDashboardBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.StorageReference;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DashboardFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DashboardFragment extends Fragment {

    FragmentManager fragmentManager;
    DatabaseReference dbReference;
    FirebaseUser currentUser;
    FragmentDashboardBinding binding;
    UserModel currentUserModel;
    ShapeableImageView image;
    DashboardAdapter adapter;
    //DatabaseReference dbReference;
    //DashboardAdapter adapter;
    //ArrayList<CardModel> items;
    SearchView searchInput;



    public DashboardFragment() {
        super(R.layout.fragment_dashboard);
        // Required empty public constructor


    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }
    public void displayFragment(Fragment fragment){
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.dashboard_rv_container, fragment).commit();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        fragmentManager = getChildFragmentManager();

        //View view = inflater.inflate(R.layout.fragment_dashboard,container, false);
        displayFragment(new CardsFragment());
        return binding.getRoot();
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        dbReference = FirebaseDatabase.getInstance(Database.JOURNEYDB).getReference();
        currentUser = Database.FIREBASE_AUTH.getCurrentUser();

        dbReference.child("users").child(currentUser.getUid()).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                }
                else {
                    DataSnapshot results = task.getResult();

                    currentUserModel = results.getValue(UserModel.class);
                    ImageView image = getView().findViewById(R.id.dash_image);
                    TextView wName = getView().findViewById (R.id.welcomeTv);
                    wName.setText("Hi, " + currentUserModel.getFirstName() + "!");

                    if(currentUserModel.getProfileImage() == null) {
                        image.setImageDrawable(ContextCompat.getDrawable(requireActivity(),R.drawable.pick_photo));
                    }else{
                        StorageReference profileURL = Database.DB_STORAGE_REFERENCE.child(currentUserModel.getProfileImage());
                        Glide.with(requireActivity()).load(profileURL).into(image);
                        image.setClickable(false);
                    }


                    Log.d("firebase", String.valueOf(task.getResult().getValue()));
                }
            }
        });

    }
//    public boolean onSearchChange() {
//        searchInput = (SearchView) getView().findViewById(R.id.search_view_dashboard);
//        //SearchView searchView = (SearchView) searchInput.getActionView();
//
//       // searchInput.setOnQueryTextListener();
//        return true;
//    }
//
//    public boolean onQueryTextChange(String query){
//        return false;
//    }
//    public boolean onQueryTextSubmit(String query){
//        return false;
//    }

}