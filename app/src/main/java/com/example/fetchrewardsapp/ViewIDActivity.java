package com.example.fetchrewardsapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class ViewIDActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private Button buttonSortId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_activity);

        // Initializing the RecyclerView
        recyclerView = findViewById(R.id.list_recycler_view);
        recyclerView.setHasFixedSize(true);

        // Initializing the sort buttons
        Button buttonSortId = findViewById(R.id.sort_button_id);
        Button buttonSortName = findViewById(R.id.sort_button_name);

        // Retrieving data from the Intent
        Intent intent = getIntent();
        String jsonArray = intent.getStringExtra("data");

        try {
            // Creating an ArrayList to hold IDEntity objects
            ArrayList<IDEntityActivity> arrayList = new ArrayList<>();

            // Parsing the JSON Array
            JSONArray array = new JSONArray(jsonArray);
            for (int i = 0; i < array.length(); i++) {
                JSONObject jsonObject = array.getJSONObject(i);
                // Check for null values and empty strings
                if (!jsonObject.isNull("id") && !jsonObject.isNull("name")) {
                    String id = jsonObject.getString("id");
                    String name = jsonObject.getString("name");
                    if (!id.isEmpty() && !name.isEmpty()) {
                        // Creating IDEntity object from JSON data and adding to the ArrayList
                        IDEntityActivity idEntityActivity = new IDEntityActivity(id, name);
                        arrayList.add(idEntityActivity);
                    }
                }
            }

            // Creating and setting up the adapter for the RecyclerView
            ViewIDAdapter viewIDAdapter = new ViewIDAdapter(this, arrayList);
            recyclerView.setLayoutManager(new LinearLayoutManager(ViewIDActivity.this, LinearLayoutManager.VERTICAL, false));
            recyclerView.setAdapter(viewIDAdapter);

            // Set click listener for sort by ID button
            buttonSortId.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Sort data by ID
                    Collections.sort(arrayList, new Comparator<IDEntityActivity>() {
                        @Override
                        public int compare(IDEntityActivity o1, IDEntityActivity o2) {
                            // Parse IDs as integers for numerical comparison
                            int id1 = Integer.parseInt(o1.getId());
                            int id2 = Integer.parseInt(o2.getId());
                            // Compare numerical values of IDs
                            return Integer.compare(id1, id2);
                        }
                    });
                    // Update RecyclerView
                    viewIDAdapter.notifyDataSetChanged();
                }
            });

            // Set click listener for sort by name button
            buttonSortName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Sort data by name
                    Collections.sort(arrayList, new Comparator<IDEntityActivity>() {
                        @Override
                        public int compare(IDEntityActivity o1, IDEntityActivity o2) {
                            // Compare names alphabetically
                            return o1.getName().compareToIgnoreCase(o2.getName());
                        }
                    });
                    // Update RecyclerView
                    viewIDAdapter.notifyDataSetChanged();
                }
            });

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
