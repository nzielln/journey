package com.example.journey.JourneyApp.Chat.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.journey.JourneyApp.Chat.Adapter.UserAdapter;
import com.example.journey.JourneyApp.Chat.Model.ChatList;
//import com.example.journey.JourneyApp.Chat.Model.Users;
import com.example.journey.JourneyApp.Main.Database;
import com.example.journey.JourneyApp.Profile.Models.UserModel;
import com.example.journey.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ChatListFragment newInstance} factory method to
 * create an instance of this fragment.
 */
public class ChatListFragment extends Fragment {

    private UserAdapter userAdapter;
    private List<UserModel> mUsers;
    FirebaseUser currentUser;

    private List<ChatList> usersList;
    //private List<Users> mUsers = new ArrayList<>();

    RecyclerView recyclerView;

    public ChatListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        currentUser = Database.FIREBASE_AUTH.getCurrentUser();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_chat_list, container, false);
        recyclerView = view.findViewById(R.id.recycler_view2);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        usersList = new ArrayList<>();

        Database.DB_REFERENCE.child(Database.CHAT_LIST).child(currentUser.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                usersList.clear();

                //Loop for all users
                for (DataSnapshot snapshot: dataSnapshot.getChildren() ){
                    ChatList chatlist = snapshot.getValue(ChatList.class);
                    usersList.add(chatlist);
                }
                chatList();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return view;
    }

    public void chatList(){
        //Getting all recent chats
        mUsers = new ArrayList<>();

        //reference = Database.DB_REFERENCE.child("MyUsers");
        Database.DB_REFERENCE.child(Database.USERS).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mUsers.clear();
                for (DataSnapshot snapshot: dataSnapshot.getChildren() ){
                    UserModel user = snapshot.getValue(UserModel.class);
                    for (ChatList chatlist : usersList){
                        assert user != null;
                        if (user.getUserID().equals(chatlist.getId())){
                            mUsers.add(user);
                        }
                    }
                }
                userAdapter = new UserAdapter(getContext(), mUsers, true);
                recyclerView.setAdapter(userAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}