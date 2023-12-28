package com.example.e_voting_system;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class cast_vote extends AppCompatActivity {

    private List<Party> partyList;
    String cnic;
    private PartyAdapter partyAdapter;
    private DatabaseOperations databaseOperations;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cast_vote);

        // Retrieve CNIC from Intent
        cnic = getIntent().getStringExtra("CNIC");

        // Initialize RecyclerView and PartyAdapter
        RecyclerView recyclerView = findViewById(R.id.recyclerViewParties);
        partyList = generatePartyList();
        partyAdapter = new PartyAdapter(partyList, this);

        // Initialize DatabaseOperations
        databaseOperations = new DatabaseOperations(this);

        // Set up RecyclerView
        recyclerView.setAdapter(partyAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Set up Cast Vote Button
        Button castVoteButton = findViewById(R.id.buttonCastVote);
        castVoteButton.setOnClickListener(view -> {
            // Get the selected party
            Party selectedParty = partyAdapter.getSelectedParty();

            if (selectedParty != null) {
                // Save vote in the "Results" table
                if(saveVote(selectedParty)){
                    // Notify the user
                    Toast.makeText(this, "Vote Casted.", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(cast_vote.this, home_screen.class);
                    startActivity(intent);
                    finish();
                }
                else{
                    Toast.makeText(this, "Error updating votes.", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "Please select a party.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean saveVote(Party selectedParty) {
        databaseOperations.open();

        // Check if the party already has votes in the "Results" table
        if (databaseOperations.IsRecordExist("Results", "Party_Name = ?", new String[]{selectedParty.getName()})) {
            // Update the vote count
            ContentValues values = new ContentValues();
            int currentVotes = getPartyVotes(selectedParty.getName());
            if (currentVotes != -1) {
                values.put("Votes", currentVotes + 1);
                databaseOperations.updateData("Results", values, "Party_Name = ?", new String[]{selectedParty.getName()});
                updateVoterDetails();
            } else {
                return false;
            }
        } else {
            // Insert a new record
            ContentValues values = new ContentValues();
            values.put("Party_Name", selectedParty.getName());
            values.put("Votes", 1);
            databaseOperations.insertData("Results", values);
            updateVoterDetails();
        }

        databaseOperations.close();
        return true;
    }

    private void updateVoterDetails(){
        // Update the Vote_Status in the "VoterDetails" table
        ContentValues voterValues = new ContentValues();
        voterValues.put("Vote_Status", 1);
        databaseOperations.updateData("VoterDetails", voterValues, "CNIC = ?", new String[]{cnic});
    }
    private int getPartyVotes(String partyName) {
        // Retrieve the current vote count for the party
        Cursor cursor = databaseOperations.getData("Results", new String[]{"Votes"}, "Party_Name = ?", new String[]{partyName});
        int votes = 0;

        if (cursor != null && cursor.moveToFirst()) {
            int columnIndex = cursor.getColumnIndex("Votes");

            // Check if the column exists in the result set
            if (columnIndex != -1) {
                votes = cursor.getInt(columnIndex);
            } else {
                // Handle the case where the column is not found
                Toast.makeText(this, "Error: Column 'Votes' not found.", Toast.LENGTH_SHORT).show();
            }

            cursor.close();
        }

        return votes;
    }

    // Method to generate a list of sample parties
    private List<Party> generatePartyList() {
        List<Party> parties = new ArrayList<>();
        parties.add(new Party(false, R.drawable.ppp_logo, "Pakistan People Party"));
        parties.add(new Party(false, R.drawable.pml_logo, "Pakistan Muslim League Noon"));
        parties.add(new Party(false, R.drawable.pti_logo, "Pakistan Tahreek e Insaaf"));
        parties.add(new Party(false, R.drawable.ji_logo, "Jamat e Islami"));
        return parties;
    }
}
