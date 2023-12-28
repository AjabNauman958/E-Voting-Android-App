package com.example.e_voting_system;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class view_results extends AppCompatActivity {

    private ResultsAdapter resultsAdapter;
    private DatabaseOperations databaseOperations;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_results);

        // Initialize RecyclerView
        RecyclerView recyclerView = findViewById(R.id.recyclerViewResults);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Initialize ResultsAdapter
        resultsAdapter = new ResultsAdapter(null, this);
        recyclerView.setAdapter(resultsAdapter);

        // Initialize DatabaseOperations
        databaseOperations = new DatabaseOperations(this);

        // Set up an adapter for the RecyclerView and populate it with election results data
        getElectionResults();
    }

    private void getElectionResults() {
        List<Result> resultList = new ArrayList<>();
        databaseOperations.open();

        // Retrieve the election results from the "Results" table
        try {
            Cursor cursor = databaseOperations.getData("Results", null, null, null);

            if (cursor != null && cursor.moveToFirst()) {
                do {
                    int partyNameIndex = cursor.getColumnIndex("Party_Name");
                    int votesIndex = cursor.getColumnIndex("Votes");

                    if (partyNameIndex != -1 && votesIndex != -1) {
                        String partyName = cursor.getString(partyNameIndex);
                        int votes = cursor.getInt(votesIndex);
                        Result result = new Result(partyName, votes);
                        resultList.add(result);
                    } else {
                        Log.e("getElectionResults", "Column not found in the result set");
                    }
                } while (cursor.moveToNext());

                cursor.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            databaseOperations.close();
        }
        resultsAdapter.setResults(resultList);
    }


    public void goBack(View view) {
        Intent intent = new Intent(view_results.this, home_screen.class);
        startActivity(intent);
        finish();
    }
}
