package com.example.journey.Sticker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.journey.JRealtimeDatabase;
import com.example.journey.R;
import com.example.journey.Sticker.Models.StickerUser;
import com.example.journey.databinding.ActivityMessengerChatViewBinding;
import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseListOptions;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MessengerChatView extends AppCompatActivity {

  private static final String TAG = JRealtimeDatabase.class.getSimpleName();

  ActivityMessengerChatViewBinding bind;
  DatabaseReference myDatabase; //database reference
  private String userReceiver;

  //private RadioButton user;

  ArrayList<String> loggedInUsers = new ArrayList<>();
  private ListView ListView1;
  private FirebaseListAdapter<StickerUser> firebaseAdapter;

  @Override
  protected void onStart() {
    super.onStart();
    firebaseAdapter.startListening();
  }

  @Override
  protected void onStop() {
    super.onStop();
    firebaseAdapter.stopListening();
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    //setContentView(R.layout.activity_messenger_chat_view);
    bind = ActivityMessengerChatViewBinding.inflate(getLayoutInflater());
    setContentView(bind.getRoot());

    ListView1 = (ListView) findViewById(R.id.ListView1);

    myDatabase = FirebaseDatabase.getInstance().getReference();
    DatabaseReference allUsers = FirebaseDatabase.getInstance().getReference().child("users");

    Query query = myDatabase.child("users");

    FirebaseListOptions<StickerUser> options =
            new FirebaseListOptions.Builder<StickerUser>()
                    .setQuery(query, StickerUser.class)
                    .setLayout(android.R.layout.simple_list_item_1)
                    .build();
    firebaseAdapter = new FirebaseListAdapter<StickerUser>(options){
      // Populate the view with all logged users.
      @Override
      protected void populateView(View view, StickerUser person, int position) {
        Log.d("list populate", person.getEmail() + " " + position);
        ((TextView) view.findViewById(android.R.id.text1)).setText(person.getEmail());
      }
    };

    ListView1.setAdapter(firebaseAdapter);

    ListView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
      @Override
      public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        TextView tview = view.findViewById(android.R.id.text1);
        openMessengerActivityToUser(tview.getText().toString());
      }
    });

//    @Override
//    protected void showListView(View view, StickerUser user, int position) {
//      ((TextView) findViewById(android.R.id.tex)
//    }

    // Update image here
    myDatabase.child("users").addValueEventListener(new ValueEventListener() {
      @Override
      public void onDataChange(DataSnapshot dataSnapshot) {
        if (dataSnapshot == null) {
          return;
        }

        for (DataSnapshot user : dataSnapshot.getChildren()) {
          loggedInUsers.add(user.child("email").getValue().toString());
        }
        Log.d("logged in users", String.join(", ", loggedInUsers));
      }
      @Override
      public void onCancelled(@NonNull DatabaseError error) {

      }
    });
  }

  /**
   * This method adds/send a sticker to user.
   */
  public void addSticker() {

  }

  public void openMessengerActivityToUser(String email) {
    Intent intent = new Intent(MessengerChatView.this, MessengerActivity.class);
    intent.putExtra(Constants.RECIPIENT, email);

    startActivity(intent);

  }
}