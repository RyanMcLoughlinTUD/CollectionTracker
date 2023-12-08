package com.example.collectiontracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class CollectionItemView extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection_item_view);
        FloatingActionButton AddItemFab = findViewById(R.id.AddItemFab);

        AddItemFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Assuming you are in the current activity
                Intent intent = new Intent(CollectionItemView.this, AddItem.class);
                startActivity(intent);

            }
        });

    }
}