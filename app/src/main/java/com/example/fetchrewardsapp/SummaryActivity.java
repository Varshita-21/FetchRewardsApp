package com.example.fetchrewardsapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class SummaryActivity extends AppCompatActivity implements IDRecyclerViewAdapter.OnIDRecyclerViewClickListener {
    private RecyclerView recyclerView;
    private ArrayList<String> listIDs = new ArrayList<>();
    private ArrayList<JSONArray> groupedJSONOnID = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.summary_activity);

        // Initialize RecyclerView
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);

        // URL to fetch data from
        String url ="https://fetch-hiring.s3.amazonaws.com/hiring.json";
        // Fetch data from the URL
        fetchData(url);
    }

    // Method to fetch data from the specified URL
    private void fetchData(String url) {
        // Instantiate the RequestQueue
        RequestQueue queue = Volley.newRequestQueue(this);

        // Request a string response from the provided URL
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            // Parse JSON response
                            JSONArray jsonArray = new JSONArray(response);
                            // Process the JSON data
                            processJSONData(jsonArray);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            // Show error message if JSON parsing fails
                            Toast.makeText(SummaryActivity.this, "Error parsing JSON", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Show error message if data fetching fails
                Toast.makeText(SummaryActivity.this, "Data Fetch Failed", Toast.LENGTH_SHORT).show();
            }
        });

        // Add the request to the RequestQueue
        queue.add(stringRequest);
    }

    // Method to process JSON data
    private void processJSONData(JSONArray jsonArray) {
        // Clear existing lists
        listIDs.clear();
        groupedJSONOnID.clear();

        // Create list of list IDs
        createListIDs(jsonArray);
        // Sort list IDs
        Collections.sort(listIDs);

        try {
            // Sort JSON array by name
            JSONArray sortedJsonArray = sortJson(jsonArray, "name");
            // Group data by list ID
            groupDataByListID(sortedJsonArray);

            // Create and set RecyclerView adapter
            IDRecyclerViewAdapter idRecyclerViewAdapter = new IDRecyclerViewAdapter(SummaryActivity.this, listIDs, SummaryActivity.this);
            recyclerView.setLayoutManager(new LinearLayoutManager(SummaryActivity.this));
            recyclerView.setAdapter(idRecyclerViewAdapter);
        } catch (JSONException e) {
            e.printStackTrace();
            // Show error message if JSON processing fails
            Toast.makeText(SummaryActivity.this, "Error processing JSON", Toast.LENGTH_SHORT).show();
        }
    }

    // Method to create list of list IDs
    private void createListIDs(JSONArray jsonArray) {
        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String listId = jsonObject.getString("listId");
                if (!listIDs.contains(listId)) {
                    listIDs.add(listId);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    // Method to group data by list ID
    private void groupDataByListID(JSONArray sortedJson) throws JSONException {
        for (String listid : listIDs) {
            JSONArray listIDJsonArray = new JSONArray();
            for (int i = 0; i < sortedJson.length(); i++) {
                JSONObject jsonObject = sortedJson.getJSONObject(i);
                String idInList = jsonObject.getString("listId");
                if (listid.equals(idInList)) {
                    String nameInList = jsonObject.optString("name");
                    if (!nameInList.trim().isEmpty()) {
                        listIDJsonArray.put(jsonObject);
                    }
                }
            }
            groupedJSONOnID.add(listIDJsonArray);
        }
    }

    // RecyclerView item click listener
    @Override
    public void OnClick(int pos) {
        JSONArray jsonArray = groupedJSONOnID.get(pos);
        Intent intent = new Intent(this, ViewIDActivity.class);
        intent.putExtra("data", jsonArray.toString());
        startActivity(intent);
    }

    // Method to sort JSON array by specified key
    private JSONArray sortJson(JSONArray jsonArraylab, String type) {
        final String value = type;
        JSONArray sortedJsonArray = new JSONArray();
        List<JSONObject> jsonValues = new ArrayList<JSONObject>();

        for (int i = 0; i < jsonArraylab.length(); i++) {
            try {
                jsonValues.add(jsonArraylab.getJSONObject(i));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        // Sort the JSON objects based on the specified key
        Collections.sort(jsonValues, new Comparator<JSONObject>() {
            @Override
            public int compare(JSONObject a, JSONObject b) {
                String valA = "";
                String valB = "";
                try {
                    valA = a.getString(value);
                    valB = b.getString(value);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return valA.compareTo(valB);
            }
        });

        // Create a sorted JSON array from sorted list of JSON objects
        for (int i = 0; i < jsonValues.size(); i++) {
            sortedJsonArray.put(jsonValues.get(i));
        }

        return sortedJsonArray;
    }
}
