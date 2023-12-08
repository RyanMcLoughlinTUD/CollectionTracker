package com.example.collectiontracker;

import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import androidx.core.view.WindowCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.collectiontracker.databinding.ActivityCollectionAddBinding;

public class CollectionAdd extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityCollectionAddBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityCollectionAddBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


    }
}