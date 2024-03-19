package com.example.fetchrewardsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //initializing the button from the view
        button = (Button)findViewById(R.id.getData);
        button.setOnClickListener(new View.OnClickListener() {
            //when the button is clicked
            @Override
            public void onClick(View v) {
                //start a new activity
                Intent intent = new Intent(MainActivity.this, SummaryActivity.class);
                startActivity(intent);
            }
        });
    }
}
