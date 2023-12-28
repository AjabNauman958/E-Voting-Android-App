package com.example.e_voting_system;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import java.util.regex.Pattern;

import androidx.appcompat.app.AppCompatActivity;

public class registeration_verification extends AppCompatActivity {

    private EditText cnicEditText;
    private Button submitButton, resgisterButton;

    private DatabaseOperations databaseOperations;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registeration_verification);

        // Initialize database operations
        databaseOperations = new DatabaseOperations(this);
        databaseOperations.open();

        cnicEditText = findViewById(R.id.cnicEditText);
        submitButton = findViewById(R.id.submitButton);
        resgisterButton = findViewById(R.id.registerButton);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                verifyRegistration();
            }
        });

        resgisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(registeration_verification.this, voter_registration.class);
                startActivity(intent);
            }
        });
    }

    private void verifyRegistration() {
        String cnic = cnicEditText.getText().toString().trim();

        String cnicRegex = "^[0-9]{5}-[0-9]{7}-[0-9]{1}$";

        if (!Pattern.matches(cnicRegex, cnic)) {
            Toast.makeText(this, "Please enter a valid CNIC in the format xxxxx-xxxxxxx-x", Toast.LENGTH_SHORT).show();
            return;
        }

        if (cnic.isEmpty()) {
            Toast.makeText(this, "Please enter your CNIC", Toast.LENGTH_SHORT).show();
            return;
        }

        boolean isRegistered = databaseOperations.IsRecordExist("VoterDetails", "CNIC=?", new String[]{cnic});

        if (isRegistered) {
            // Check if the user has already voted
            boolean hasVoted = databaseOperations.IsRecordExist("VoterDetails", "CNIC=? AND Vote_Status=?", new String[]{cnic, "1"});

            if (hasVoted) {
                // User has already voted, display a toast and do not allow to cast vote again
                Toast.makeText(this, "You have already cast your vote. Multiple votes are not allowed.", Toast.LENGTH_SHORT).show();
                return;
            } else {
                // User is registered and hasn't voted yet
                Toast.makeText(this, "Registration verified successfully", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, cast_vote.class);
                intent.putExtra("CNIC", cnic);
                startActivity(intent);
                finish();
            }
        } else {
            Toast.makeText(this, "Invalid CNIC. Please register first", Toast.LENGTH_SHORT).show();
        }
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        databaseOperations.close();
    }
}
