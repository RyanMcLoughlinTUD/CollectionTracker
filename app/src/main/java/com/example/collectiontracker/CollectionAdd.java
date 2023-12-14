package com.example.collectiontracker;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.core.view.WindowCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.collectiontracker.databinding.ActivityCollectionAddBinding;

public class CollectionAdd extends AppCompatActivity {
    //this adds a new collection
    private AppBarConfiguration appBarConfiguration;
    private ActivityCollectionAddBinding binding;

    public String CollectionName = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCollectionAddBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        DBHelper database = new DBHelper(this);
        Button addCollection = findViewById(R.id.commitCollection);
        EditText collectionName = findViewById(R.id.ItemName);

        addCollection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get the collection name from the EditText
                String collectionNameText = collectionName.getText().toString();

                // Check if the collection name is not empty
                if (!collectionNameText.isEmpty()) {
                    // Add the collection to the database
                    database.addCollection(collectionNameText);

                    // Set the result to indicate success
                    setResult(RESULT_OK);

                    // Show a success message
                    Snackbar.make(view, "Collection added: " + collectionNameText, Snackbar.LENGTH_SHORT).show();

                    // Finish the activity
                    finish();
                } else {
                    // Show an error message if the collection name is empty
                    Snackbar.make(view, "Please enter a collection name", Snackbar.LENGTH_SHORT).show();
                }
            }
        });


    }
}