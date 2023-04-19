package com.example.journey.JourneyApp.Dashboard;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.journey.JourneyApp.Main.Database;
import com.example.journey.JourneyApp.Profile.Models.UserModel;
import com.example.journey.R;
import com.example.journey.databinding.DashboardRvBinding;
import com.example.journey.databinding.FragmentDashboardBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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

        Map<String, UserModel> allUsers = new HashMap<>();
        ImageView image = getView().findViewById(R.id.dash_image);
        dbReference.child("users").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()) {
                    /*
                 //   for (final DataSnapshot inner : task.getResult().getChildren()) {
                    //    UserModel user = inner.getValue(UserModel.class);

                     //   allUsers.put(user.getUserID(), user);
//                        Log.d("allusers", user.getProfileImage());
//                        if(user.getProfileImage() == null) {
//                            image.setImageDrawable(ContextCompat.getDrawable(requireActivity(),R.drawable.pick_photo));
//                        }else {
//                            Glide.with(requireActivity()).load(user.getProfileImage()).into(image);
//                            allUsers.put(user.getProfileImage(), user);
//                        }
                    }
                */

                    retrievePost(allUsers);
                }
            }
        });
    }

    private void retrievePost(Map<String, UserModel> users)
    {
        dbReference.child("posts").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            //public void onSuccess(@NonNull Task<DataSnapshot> task) {
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                }
                else {
                    for (final DataSnapshot inner : task.getResult().getChildren()) {
                        NewPost post = inner.getValue(NewPost.class);
                        UserModel user = users.get(post.getAuthorID());
                        //StorageReference userPic = Database.DB_STORAGE_REFERENCE.child(user.getProfileImage());

                       // items.add(new CardModel(user, post.getTimePosted(), post.getPostContent(), userPic));
                    }

                    adapter = new DashboardAdapter(items, requireActivity());
                    recyclerView.setAdapter(adapter);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                }

            }


        });


    }



}
