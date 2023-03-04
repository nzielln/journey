package com.example.journey;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.view.View;

import com.example.journey.databinding.ActivityChatBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.security.cert.PolicyNode;
import java.util.UUID;

public class ChatActivity extends AppCompatActivity {
    ActivityChatBinding binding;
    String receiverId;
    String senderRoom, receiverRoom;
    DatabaseReference databaseReferenceSender, databaseReferenceReceiver;
    MessageAdapter messageAdapter;
    View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityChatBinding.inflate(getLayoutInflater());
        setContentView(R.layout.activity_chat);

        receiverId = getIntent().getStringExtra("id");

        senderRoom= FirebaseAuth.getInstance().getUid();
        receiverRoom= receiverId+FirebaseAuth.getInstance().getUid();

        messageAdapter=new MessageAdapter(this);
        binding.recycler.setAdapter(messageAdapter);
        binding.recycler.setLayoutManager(new LinearLayoutManager(this));

        databaseReferenceSender = FirebaseDatabase.getInstance().getReference("chats").child(senderRoom);
        databaseReferenceReceiver = FirebaseDatabase.getInstance().getReference("chats").child(receiverRoom);


        databaseReferenceSender.addValueEventListener(new ValueEventListener(){
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot){
                messageAdapter.clear();
                for (DataSnapshot dataSnapShot: snapshot.getChildren()){
                    MessageModel messageModel = dataSnapShot.getValue(MessageModel.class);
                    messageAdapter.add(messageModel);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        binding.sendMessage.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                String message = binding.messageEd.getText().toString();
                if (message.trim().length()>0){
                    sendMessage(message);
                }
            }
        });
    }

    public void sendMessage(String message){
        String messageId= UUID.randomUUID().toString();
        MessageModel messageModel = new MessageModel(messageId, FirebaseAuth.getInstance().getUid(), message);

        messageAdapter.add(messageModel);
        databaseReferenceSender
                .child(messageId)
                .setValue(messageModel);

        databaseReferenceReceiver
                .child(messageId)
                .setValue(messageModel);


    }
}