package com.example.journey;

import android.widget.Toast;
import android.content.Intent;
import android.widget.TextView;
import android.widget.Button;
import android.os.Bundle;
import android.view.View;


import androidx.appcompat.app.AppCompatActivity;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RealtimeDatabaseActivity extends AppCompatActivity {

    private DatabaseReference stickersRef;
    private DatabaseReference userStickersRef;

    private TextView titleTextView;
    private Button sendStickerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_realtimedatabase);

        // Get a reference to the Realtime Database
        FirebaseDatabase database = FirebaseDatabase.getInstance();

        // Create a child node under the root node for stickers
        stickersRef = database.getReference("stickers");

        // Check if the user is signed in
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            // If the user is signed in, create a child node under their user ID for stickers
            String userId = user.getUid();
            userStickersRef = database.getReference("users").child(userId).child("stickers");
        }

        // Connect the views in the XML layout file to the corresponding variables in the Java code
        titleTextView = findViewById(R.id.title_textview);
        sendStickerButton = findViewById(R.id.send_sticker_button);

        // Set an OnClickListener for the "Send Sticker" button
       // Button sendStickerButton = findViewById(R.id.send_sticker_button);
        sendStickerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create a new sticker object
                Sticker sticker = new Sticker("https://example.com/sticker.png", "Happy face");

                // Add the sticker data to the Realtime Database
                String stickerId = stickersRef.push().getKey();
                stickersRef.child(stickerId).setValue(sticker);

                // If the user is signed in, send the sticker to the user
                if (userStickersRef != null) {
                    userStickersRef.child(stickerId).setValue(true);
                }

                // Show a message to the user indicating that the sticker was sent
                Toast.makeText(RealtimeDatabaseActivity.this, "Sticker sent!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
