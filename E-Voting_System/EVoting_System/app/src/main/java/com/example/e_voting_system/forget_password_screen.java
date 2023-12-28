package com.example.e_voting_system;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class forget_password_screen extends AppCompatActivity {

    private EditText editTextForgotPasswordEmail;
    private TextView textViewPassword;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password_screen);

        editTextForgotPasswordEmail = findViewById(R.id.editTextForgotPasswordEmail);
        textViewPassword = findViewById(R.id.textViewPassword);
    }

    public void onResetPasswordClick(View view) {
        DatabaseOperations db = new DatabaseOperations(this);
        db.open();
        String email = editTextForgotPasswordEmail.getText().toString().trim();

        // Validating the email field
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Please enter your email", Toast.LENGTH_SHORT).show();
            return;
        }

        boolean isRegistered = db.IsRecordExist("UserCredential", "Email=?", new String[]{email});
        // Check if the email is registered
        if (!isRegistered) {
            textViewPassword.setText("Email is not registered.");
            findViewById(R.id.buttonSignUp).setVisibility(View.VISIBLE);
        }
        else{
            String password = getPasswordFromDatabase(email, db);
            textViewPassword.setText("Your Password is: " + password);
            findViewById(R.id.buttonBackToLogin).setVisibility(View.VISIBLE);
        }
    }

    private String getPasswordFromDatabase(String email, DatabaseOperations db) {
        String password = null;
        Cursor cursor = db.getData(
                "UserCredential", new String[]{"Password"}, "Email = ?", new String[]{email});
        if (cursor != null) {
            try {
                if (cursor.moveToFirst()) {
                    int columnIndex = cursor.getColumnIndex("Password");
                    if (columnIndex != -1) {
                        password = cursor.getString(columnIndex);
                    } else {
                        // Handle the case where the column index is not found
                        Toast.makeText(this, "Error: Column 'Password' not found.", Toast.LENGTH_SHORT).show();
                    }
                }
            } finally {
                cursor.close();
            }
        }
        db.close();
        return password;
    }

    public void onBackToLoginClick(View view) {
        Intent intent = new Intent(this, login_screen.class);
        startActivity(intent);
        finish();
    }

    public void onSignUpClick(View view) {
        Intent intent = new Intent(this, signup_screen.class);
        startActivity(intent);
    }
}

