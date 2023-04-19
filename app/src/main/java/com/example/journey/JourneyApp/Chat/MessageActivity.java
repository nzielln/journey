package com.example.journey.JourneyApp.Chat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.journey.JourneyApp.Chat.Adapter.MessageAdapter;
import com.example.journey.JourneyApp.Chat.Model.Chat;
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
import java.util.HashMap;
import java.util.List;

public class MessageActivity extends AppCompatActivity {

    TextView username;
    ImageView imageView;

    RecyclerView recyclerViewy;
    EditText msg_editText;
    ImageButton sendBtn;


    FirebaseUser currentUser;
    DatabaseReference reference;
    Intent intent;

    MessageAdapter messageAdapter;
    List<Chat> mChat;
    RecyclerView recyclerView;

    String userid;

    ValueEventListener seenListener;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);


        //Widgets
        imageView = findViewById(R.id.imageview_profile);
        username = findViewById(R.id.user_name);
        sendBtn = findViewById(R.id.btn_send);
        msg_editText = findViewById(R.id.text_send);

        //Recycler View
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);


        intent = getIntent();
        userid = intent.getStringExtra("userid");

        //fuser = FirebaseAuth.getInstance().getCurrentUser();
        //reference = FirebaseDatabase.getInstance().getReference("MyUsers").child(userid);
        currentUser = Database.FIREBASE_AUTH.getCurrentUser();

        Database.DB_REFERENCE.child(Database.USERS).child(currentUser.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                UserModel userModel = dataSnapshot.getValue(UserModel.class);
                username.setText(userModel.getUsername());

                if (userModel.getProfileImage().equals("default")){
                    imageView.setImageResource(R.drawable.person_image);
                } else{
                    Glide.with(MessageActivity.this).load(userModel.getProfileImage()).into(imageView);
                }

                readMessages(currentUser.getUid(), userid, userModel.getProfileImage());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        sendBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                String msg = msg_editText.getText().toString();
                if (!msg.equals("")){
                    sendMessage(currentUser.getUid(), userid, msg);
                } else{
                    Toast.makeText(MessageActivity.this, "Please send a non empty message", Toast.LENGTH_SHORT).show();
                }
                msg_editText.setText("");
            }
        });

        SeenMessage(userid);
    }



    private void SeenMessage(String userid){

        reference = FirebaseDatabase.getInstance().getReference("Chats");
        seenListener = reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot snapshot: dataSnapshot.getChildren()){
                    Chat chat = snapshot.getValue(Chat.class);

                    if (chat.getReceiver().equals(currentUser.getUid()) && chat.getSender().equals(userid)){
                        HashMap<String, Object> hashMap = new HashMap<>();
                        hashMap.put("isseen", true);
                        snapshot.getRef().updateChildren(hashMap);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    private void sendMessage(String sender, String receiver, String message){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("sender", sender);
        hashMap.put("receiver", receiver);
        hashMap.put("message", message);
        hashMap.put("isseen", false);



        reference.child("Chats").push().setValue(hashMap);

        //Adding user to chat fragment: latest chat with contacts
        final DatabaseReference chatRef = FirebaseDatabase.getInstance().getReference("ChatList")
                .child(currentUser.getUid())
                .child(userid);

        chatRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (!dataSnapshot.exists()){
                    chatRef.child("id").setValue(userid);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void readMessages(String myid, String userid, String imageurl){
        mChat = new ArrayList<>();
        reference = FirebaseDatabase.getInstance().getReference("Chats");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mChat.clear();
                for (DataSnapshot snapshot: dataSnapshot.getChildren()){
                    Chat chat = snapshot.getValue(Chat.class);

                    if (chat.getReceiver().equals(myid) && chat.getSender().equals(userid) ||
                            chat.getReceiver().equals(userid) && chat.getSender().equals(myid)){
                        mChat.add(chat);
                    }
                    messageAdapter = new MessageAdapter(MessageActivity.this, mChat, imageurl);
                    recyclerView.setAdapter(messageAdapter);
                }
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void CheckStatus(String status){
        reference = FirebaseDatabase.getInstance().getReference("users").child(currentUser.getUid());

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("status", status);

        reference.updateChildren(hashMap);


    }

    @Override
    protected void onResume(){
        super.onResume();
        CheckStatus("online");

    }
    @Override
    protected void onPause(){
        super.onPause();
        reference.removeEventListener(seenListener);
        CheckStatus("Offline");
    }

}