package com.example.journey.Sticker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.journey.JRealtimeDatabase;
import com.example.journey.R;
import com.example.journey.Sticker.Models.StickerUser;
import com.example.journey.databinding.ActivityMessengerChatViewBinding;
import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseListOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MessengerChatView extends AppCompatActivity {

  private static final String TAG = JRealtimeDatabase.class.getSimpleName();

  ActivityMessengerChatViewBinding bind;
  DatabaseReference myDatabase; //database reference
  private String userReceiver;
  private FirebaseAuth userAuthentication;
  FirebaseUser fbUser;

  //private RadioButton user;

  ArrayList<String> loggedInUsers = new ArrayList<>();
  private ListView user_list_view;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    //setContentView(R.layout.activity_messenger_chat_view);
    bind = ActivityMessengerChatViewBinding.inflate(getLayoutInflater());
    setContentView(bind.getRoot());

    user_list_view = (ListView) findViewById(R.id.user_list_view);
    userAuthentication = FirebaseAuth.getInstance();
    fbUser = userAuthentication.getCurrentUser();
    myDatabase = FirebaseDatabase.getInstance().getReference();
    DatabaseReference allUsers = FirebaseDatabase.getInstance().getReference().child(Constants.USERS_DATABASE_ROOT);

    ArrayList<StickerUser> users = new ArrayList<>();
    allUsers.addValueEventListener(new ValueEventListener() {
      @Override
      public void onDataChange(@NonNull DataSnapshot snapshot) {
        for(DataSnapshot dbUser: snapshot.getChildren()) {
            StickerUser user = dbUser.getValue(StickerUser.class);
          assert user != null;
          if (!Objects.equals(user.getEmail(), fbUser.getEmail())) {
            users.add(user);
          }
        }
        UserAdapter adapter = new UserAdapter(getApplicationContext(), users);
        user_list_view.setAdapter(adapter);

      }

      @Override
      public void onCancelled(@NonNull DatabaseError error) {
        Log.e(TAG, "FAILED TO RETRIEVE ALL USERS FROM DATABASE WITH ERROR: " + error.toException());
      }
    });

//    Query query = myDatabase.child("users");
//    FirebaseListOptions<StickerUser> options =
//            new FirebaseListOptions.Builder<StickerUser>()
//                    .setQuery(query, StickerUser.class)
//                    .setLayout(android.R.layout.simple_list_item_1)
//                    .build();
//
//    firebaseAdapter = new FirebaseListAdapter<StickerUser>(options){
//      @Override
//      protected void populateView(View view, StickerUser person, int position) {
//        Log.d("list populate", person.getEmail() + " " + position);
//        if (!Objects.equals(person.getEmail(), fbUser.getEmail())) {
//          ((TextView) view.findViewById(android.R.id.text1)).setText(person.getEmail());
//        } else {
//        }
//      }
//    };

    user_list_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
      @Override
      public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        TextView tview = view.findViewById(R.id.active_users_text);
        openMessengerActivityToUser(tview.getText().toString());
      }
    });

//    @Override
//    protected void showListView(View view, StickerUser user, int position) {
//      ((TextView) findViewById(android.R.id.tex)
//    }

    // Update image here
    myDatabase.child(Constants.USERS_DATABASE_ROOT).addValueEventListener(new ValueEventListener() {
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
   * The openMessengerActivityToUser() method opens
   * the Chat Activity where users can tap on
   * a sticker and send a sticker to another user.
   * @param email
   */
  public void openMessengerActivityToUser(String email) {
    Intent intent = new Intent(MessengerChatView.this, MessengerActivity.class);
    intent.putExtra(Constants.RECIPIENT, email);

    startActivity(intent);

  }

  @Override
  public void onSaveInstanceState(Bundle outState) {
    super.onSaveInstanceState(outState);
    outState.putStringArrayList("LoggedInUsers", loggedInUsers);
  }

  @Override
  public void onRestoreInstanceState(Bundle savedInstanceState) {
    super.onRestoreInstanceState(savedInstanceState);
    loggedInUsers = savedInstanceState.getStringArrayList("LoggedInUsers");
  }

}