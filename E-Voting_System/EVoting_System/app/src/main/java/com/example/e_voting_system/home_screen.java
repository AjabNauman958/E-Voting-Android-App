package com.example.e_voting_system;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

public class home_screen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        // Find views by ID
        ImageView menuIcon = findViewById(R.id.imageViewMenu);
        ImageView profileIcon = findViewById(R.id.imageViewProfile);

        LinearLayout registerLayout = findViewById(R.id.registerLayout);
        LinearLayout voteLayout = findViewById(R.id.voteLayout);

        // Set click listeners
        menuIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSideMenu();
            }
        });

        profileIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openUserProfile();
            }
        });

        registerLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openRegistrationScreen();
            }
        });

        voteLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                castVoteScreen();
            }
        });

    }

    public void logout(View view) {
        Intent intent = new Intent(this, login_screen.class);
        startActivity(intent);
        finish();
    }

    private void openSideMenu() {
        Toast.makeText(this, "Side Menu Open", Toast.LENGTH_SHORT).show();
    }

    private void openUserProfile() {
        Toast.makeText(this, "User Profile Open", Toast.LENGTH_SHORT).show();
    }

    private void openRegistrationScreen() {
        Intent intent = new Intent(this, voter_registration.class);
        startActivity(intent);
    }

    public void openViewResultsScreen(View view) {
        Intent intent = new Intent(this, view_results.class);
        startActivity(intent);
    }

    private void castVoteScreen(){
        Intent intent = new Intent(this, registeration_verification.class);
        startActivity(intent);
    }
}
