package com.example.e_voting_system;

import android.content.ContentValues;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class signup_screen extends AppCompatActivity {

    private EditText editTextName, editTextEmail, editTextPassword, editTextConfirmPassword, editTextRecoveryEmail;
    private Button buttonSignup;
    private TextView textViewLoginLink;
    private DatabaseOperations databaseOperations;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_screen);

        editTextName = findViewById(R.id.editTextName);
        editTextEmail = findViewById(R.id.editTextEmailSignup);
        editTextPassword = findViewById(R.id.editTextPasswordSignup);
        editTextConfirmPassword = findViewById(R.id.editTextConfirmPassword);
        editTextRecoveryEmail = findViewById(R.id.editTextRecoveryEmail);
        buttonSignup = findViewById(R.id.buttonSignup);
        textViewLoginLink = findViewById(R.id.textViewLoginLink);

        // Initialize database operations
        databaseOperations = new DatabaseOperations(this);

        buttonSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signup();
            }
        });

        textViewLoginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToLoginScreen();
            }
        });
    }

    private void signup() {
        String name = editTextName.getText().toString().trim();
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        String confirmPassword = editTextConfirmPassword.getText().toString().trim();
        String recoveryEmail = editTextRecoveryEmail.getText().toString().trim();

        if (name.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!password.equals(confirmPassword)) {
            Toast.makeText(this, "Password and Confirm Password do not match", Toast.LENGTH_SHORT).show();
            return;
        }

        // Use AsyncTask to perform database operations in the background
        new SignupTask().execute(email, name, password, recoveryEmail);
    }

    private class SignupTask extends AsyncTask<String, Void, Long> {

        @Override
        protected Long doInBackground(String... params) {
            ContentValues values = new ContentValues();
            values.put("Email", params[0]);
            values.put("Name", params[1]);
            values.put("Password", params[2]);
            values.put("Recovery_Email", params[3]);

            long result = -1;
            try {
                databaseOperations.open();

                result = databaseOperations.insertData("UserCredential", values);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                databaseOperations.close();
            }

            return result;
        }

        @Override
        protected void onPostExecute(Long result) {
            if (result != -1) {
                Toast.makeText(signup_screen.this, "Signup successful", Toast.LENGTH_SHORT).show();
                navigateToLoginScreen();
            } else {
                Toast.makeText(signup_screen.this, "Signup failed. Please try again.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void navigateToLoginScreen() {
        Intent intent = new Intent(signup_screen.this, login_screen.class);
        startActivity(intent);
        finish();
    }
}

