package com.example.collectiontracker;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;
import com.example.collectiontracker.CollectionModel;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.collectiontracker.databinding.ActivityMainBinding;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements CollectionRecyclerAdapter.OnItemClickListener{
    private static final int ADD_COLLECTION_REQUEST_CODE = 1;
    //home page code!!!!
    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;
    private RecyclerView recyclerView;
    private CollectionRecyclerAdapter collectionAdapter;
    private List<CollectionModel> collectionList;
    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        dbHelper = new DBHelper(this);

        recyclerView = findViewById(R.id.recyclerView);
        collectionList = new ArrayList<>();

        collectionAdapter = new CollectionRecyclerAdapter(collectionList, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(collectionAdapter);

        collectionList.addAll(dbHelper.getCollections());
        collectionAdapter.notifyDataSetChanged();

        binding.HomeFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, CollectionAdd.class);
                startActivityForResult(intent, ADD_COLLECTION_REQUEST_CODE);

            }
        });



    }

    @Override
    public void onItemClick(CollectionModel collection) {
        Intent intent = new Intent(MainActivity.this, CollectionItemView.class);
        intent.putExtra("collectionId", collection.getId());
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ADD_COLLECTION_REQUEST_CODE && resultCode == RESULT_OK) {
            // Handle the result, e.g., refresh the list
            refreshCollectionList();
        }
    }

    private void refreshCollectionList() {
        collectionList.clear();
        collectionList.addAll(dbHelper.getCollections());
        collectionAdapter.notifyDataSetChanged();
    }


}