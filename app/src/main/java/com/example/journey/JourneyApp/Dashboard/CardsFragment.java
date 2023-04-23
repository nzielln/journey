package com.example.journey.JourneyApp.Dashboard;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.journey.JourneyApp.Main.Database;
import com.example.journey.JourneyApp.Profile.Models.ProfileViewModel;
import com.example.journey.JourneyApp.Profile.Models.UserModel;
import com.example.journey.JourneyApp.Profile.ProfileFragment;
import com.example.journey.JourneyApp.Profile.ProfileState;
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
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class CardsFragment extends Fragment {
    private DatabaseReference dbReference;
    private FirebaseAuth userAuth;

    private UserModel user;
    DashboardRvBinding binding;
    private DashboardAdapter adapter;
    RecyclerView recyclerView;
    FirebaseUser currentUser;
    ArrayList<CardModel> items = new ArrayList<>();
    ProfileViewModel profileViewModel;
    SearchViewModel searchModel;


    public CardsFragment() {
        super(R.layout.dashboard_rv);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        profileViewModel = new ViewModelProvider(requireActivity()).get(ProfileViewModel.class);
        profileViewModel.updateProfileState(ProfileState.PERSONAL, null);
        user = profileViewModel.getCurrentUserModel().getValue();
        dbReference = FirebaseDatabase.getInstance(Database.JOURNEYDB).getReference();
        currentUser = Database.FIREBASE_AUTH.getCurrentUser();

        searchModel = new ViewModelProvider(requireActivity()).get(SearchViewModel.class);

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
        user = profileViewModel.getCurrentUserModel().getValue();

        Map<String, UserModel> allUsers = new HashMap<>();
        Map<String, UserModel> usersFollowing = new HashMap<>();


        dbReference.child("users").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()) {
                    for (final DataSnapshot inner : task.getResult().getChildren()) {
                        UserModel user = inner.getValue(UserModel.class);

                        allUsers.put(user.getUserID(), user);
                    }
                    retrieveNewPost(allUsers);
                }
            }
        });
    }

    private void retrieveNewPost(Map<String, UserModel> users) {
        dbReference.child("posts").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            //public void onSuccess(@NonNull Task<DataSnapshot> task) {
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                } else {
                    for (final DataSnapshot inner : task.getResult().getChildren()) {
                        NewPost post = inner.getValue(NewPost.class);
                        UserModel user = users.get(post.getAuthorID());
                        //Log.d("user Info", user.getUserID());
                        StorageReference userPic;
                        if (user.getProfileImage() == null ){
                            userPic= null;
                        } else {
                            userPic = Database.DB_STORAGE_REFERENCE.child(user.getProfileImage());
                        }

                        Boolean isPostLikedByUser = false;

                        if (post.liked.containsKey(currentUser.getUid()) && post.liked.get(currentUser.getUid()) == true) {
                            isPostLikedByUser = true;
                        }

                       items.add(new CardModel(user, post.getTimePosted(), post.getPostContent(), userPic,post.getPostID(),isPostLikedByUser, user));

                    }

                    Collections.reverse(items);

                    adapter = new DashboardAdapter(items, requireActivity(), getParentFragmentManager());
                    adapter.setListener(new CardClickListener() {
                        @Override
                        public void onPositionCLicked(int position) {
                            CardModel cardModel = items.get(position);

                            if (!Objects.equals(cardModel.getUserModel().getUserID(), currentUser.getUid())) {
                                profileViewModel.updateProfileState(ProfileState.PUBLIC, cardModel.getUserModel());
                            }

                            FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                            FragmentTransaction transaction = fragmentManager.beginTransaction();

                            ProfileFragment profileFragment = new ProfileFragment();
                            transaction.replace(R.id.journey_fragment_container, profileFragment).commit();
                        }

                        @Override
                        public void onLongClicked(int position) {

                        }
                    });
                    recyclerView.setAdapter(adapter);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

                    Observer<String> searchObserver = new Observer<String>() {
                        @Override
                        public void onChanged(String s) {
                            if (s.isEmpty()) {
                                adapter.setItems(items);
                                adapter.notifyDataSetChanged();
                            } else {

                                ArrayList<CardModel> filteredList = new ArrayList<>();
                                for (CardModel item : items) {
                                    if (item.getCardSummary().toLowerCase().contains(s.toLowerCase())) {
                                        filteredList.add(item);
                                    }
                                }

                                adapter.setItems(filteredList);
                                adapter.notifyDataSetChanged();
                            }
                        }
                    };

                    searchModel.getSearch().observe(requireActivity(), searchObserver);
                }
            }
        });

    }

}
