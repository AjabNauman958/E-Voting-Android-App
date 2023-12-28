package com.example.e_voting_system;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class login_screen extends AppCompatActivity {

    private EditText editTextEmail;
    private EditText editTextPassword;
    private DatabaseOperations databaseOperations;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);

        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
    }

    public void onLoginClick(View view) {
        databaseOperations = new DatabaseOperations(this);

        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
            showToast("Please enter both email and password");
            return;
        }
        databaseOperations.open();
        boolean isUserExist = databaseOperations.IsRecordExist("UserCredential", "Email = ? AND Password = ?", new String[]{email, password});

        if (isUserExist) {
            showToast("Login Successfully.");
            Intent intent = new Intent(this, home_screen.class);
            startActivity(intent);
            finish();
        } else {
            showToast("Incorrect Email or Password");
        }
    }

    public void onSignupClick(View view) {
        Intent intent = new Intent(this, signup_screen.class);
        startActivity(intent);
    }

    public void onForgotPasswordClick(View view) {
        Intent intent = new Intent(this, forget_password_screen.class);
        startActivity(intent);
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (databaseOperations != null) {
            databaseOperations.close();
        }
    }
}