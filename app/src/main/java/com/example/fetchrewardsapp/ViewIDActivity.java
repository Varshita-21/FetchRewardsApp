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

public class ViewIDActivity extends AppCompatActivity {
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_activity);

        // Initializing the RecyclerView
        recyclerView = findViewById(R.id.list_recycler_view);
        recyclerView.setHasFixedSize(true);

        // Retrieving data from the Intent
        Intent intent = getIntent();
        String jsonArray = intent.getStringExtra("data");

        try {
            // Creating an ArrayList to hold IDEntity objects
            ArrayList<IDEntity> arrayList = new ArrayList<>();

            // Parsing the JSON Array
            JSONArray array = new JSONArray(jsonArray);
            for (int i = 0; i < array.length(); i++) {
                JSONObject jsonObject = array.getJSONObject(i);
                // Creating IDEntity object from JSON data and adding to the ArrayList
                IDEntity idEntity = new IDEntity(jsonObject.getString("id"), jsonObject.getString("name"));
                arrayList.add(idEntity);
            }

            // Creating and setting up the adapter for the RecyclerView
            ViewIDAdapter viewIDAdapter = new ViewIDAdapter(this, arrayList);
            recyclerView.setLayoutManager(new LinearLayoutManager(ViewIDActivity.this, LinearLayoutManager.VERTICAL, false));
            recyclerView.setAdapter(viewIDAdapter);

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}

