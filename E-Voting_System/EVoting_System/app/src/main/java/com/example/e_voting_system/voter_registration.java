package com.example.e_voting_system;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class voter_registration extends AppCompatActivity {

    private EditText editTextCNIC, editTextFirstName, editTextLastName,
            editTextDOB, editTextFatherName, editTextAddress;
    private Spinner spinnerCountry, spinnerProvince, spinnerDistrict;
    private Button buttonSubmit;
    private DatabaseOperations databaseOperations;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voter_registration);

        editTextCNIC = findViewById(R.id.editTextCNIC);
        editTextFirstName = findViewById(R.id.editTextFirstName);
        editTextLastName = findViewById(R.id.editTextLastName);
        editTextDOB = findViewById(R.id.editTextDOB);
        editTextFatherName = findViewById(R.id.editTextFatherName);
        editTextAddress = findViewById(R.id.editTextAddress);

        spinnerCountry = findViewById(R.id.spinnerCountry);
        spinnerProvince = findViewById(R.id.spinnerProvince);
        spinnerDistrict = findViewById(R.id.spinnerDistrict);

        buttonSubmit = findViewById(R.id.buttonSubmit);

        // Initialize database operations
        databaseOperations = new DatabaseOperations(this);

        // Set up spinners with dummy data
        setUpCountrySpinner();
        setUpProvinceSpinner();
        setUpDistrictSpinner();

        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validateData()) {
                    showConfirmationDialog();
                }
            }
        });
    }

    private void setUpCountrySpinner() {
        List<String> countries = new ArrayList<>();
        countries.add("Pakistan");
        countries.add("China");
        countries.add("Germany");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, countries);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerCountry.setAdapter(adapter);
    }

    private void setUpProvinceSpinner() {
        List<String> provinces = new ArrayList<>();
        provinces.add("Punjab");
        provinces.add("Sindh");
        provinces.add("Balochistan");
        provinces.add("KPK");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, provinces);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerProvince.setAdapter(adapter);
    }

    private void setUpDistrictSpinner() {
        List<String> districts = new ArrayList<>();
        districts.add("Khushab");
        districts.add("Attock");
        districts.add("Faisalabad");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, districts);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerDistrict.setAdapter(adapter);
    }


    private boolean validateData() {
        String cnic = editTextCNIC.getText().toString().trim();
        String firstName = editTextFirstName.getText().toString().trim();
        String lastName = editTextLastName.getText().toString().trim();
        String dateOfBirth = editTextDOB.getText().toString().trim();
        String fatherName = editTextFatherName.getText().toString().trim();
        String address = editTextAddress.getText().toString().trim();

        String cnicRegex = "^[0-9]{5}-[0-9]{7}-[0-9]{1}$";
        String dobRegex = "^[0-9]{2}-[0-9]{2}-[0-9]{4}$";

        if (cnic.isEmpty() || firstName.isEmpty() || lastName.isEmpty() || dateOfBirth.isEmpty() || fatherName.isEmpty() || address.isEmpty()) {
            Toast.makeText(voter_registration.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (!Pattern.matches(cnicRegex, cnic)) {
            Toast.makeText(this, "Valid CNIC format is xxxxx-xxxxxxx-x", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (!Pattern.matches(dobRegex, dateOfBirth)) {
            Toast.makeText(this, "Valid Date format is DD-MM-YYYY", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (spinnerCountry.getSelectedItemPosition() == -1) {
            Toast.makeText(voter_registration.this, "Please select a country", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (spinnerProvince.getSelectedItemPosition() == -1) {
            Toast.makeText(voter_registration.this, "Please select a province", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (spinnerDistrict.getSelectedItemPosition() == -1) {
            // District not selected
            Toast.makeText(voter_registration.this, "Please select a district", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    private void showConfirmationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(voter_registration.this);
        builder.setTitle("Confirmation")
                .setMessage("Are you sure you want to submit the data?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        long result = storeDataInDatabase();

                        if (result != -1) {
                            Toast.makeText(voter_registration.this, "Data Submitted", Toast.LENGTH_SHORT).show();
                            showVotingOrMenuDialog();
                        } else {
                            Toast.makeText(voter_registration.this, "Failed to submit data. Please try again.", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                })
                .show();
    }

    private long storeDataInDatabase() {
        String cnic = editTextCNIC.getText().toString().trim();
        String firstName = editTextFirstName.getText().toString().trim();
        String lastName = editTextLastName.getText().toString().trim();
        String dateOfBirth = editTextDOB.getText().toString().trim();
        String fatherName = editTextFatherName.getText().toString().trim();
        String address = editTextAddress.getText().toString().trim();
        String country = spinnerCountry.getSelectedItem().toString();
        String province = spinnerProvince.getSelectedItem().toString();
        String district = spinnerDistrict.getSelectedItem().toString();
        int VotedCasted = 0;

        databaseOperations.open();
        // Check if the record already exists in the database
        if (databaseOperations.IsRecordExist("VoterDetails", "CNIC=?", new String[]{cnic})) {
            Toast.makeText(voter_registration.this, "This CNIC is already registered.", Toast.LENGTH_SHORT).show();
            databaseOperations.close();
            return -1;
        }

        // Insert data into the database
        ContentValues values = new ContentValues();
        values.put("CNIC", cnic);
        values.put("First_Name", firstName);
        values.put("Last_Name", lastName);
        values.put("DOB", dateOfBirth);
        values.put("Father_Name", fatherName);
        values.put("House_Address", address);
        values.put("Country", country);
        values.put("Province", province);
        values.put("City", district);
        values.put("Vote_Status", VotedCasted);

        long result = databaseOperations.insertData("VoterDetails", values);

        // Close the database
        databaseOperations.close();

        return result;
    }

    private void showVotingOrMenuDialog() {
        String cnic = editTextCNIC.getText().toString().trim();
        AlertDialog.Builder builder = new AlertDialog.Builder(voter_registration.this);
        builder.setTitle("Choose Action")
                .setMessage("Do you want to vote or go to the menu?")
                .setPositiveButton("Vote", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(voter_registration.this, cast_vote.class);
                        intent.putExtra("CNIC", cnic);
                        startActivity(intent);
                        finish();
                    }
                })
                .setNegativeButton("Menu", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(voter_registration.this, home_screen.class);
                        startActivity(intent);
                        finish();
                    }
                })
                .show();
    }

}
