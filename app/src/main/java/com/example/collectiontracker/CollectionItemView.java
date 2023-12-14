package com.example.collectiontracker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import com.example.collectiontracker.databinding.ActivityCollectionItemViewBinding;

import java.util.List;

public class CollectionItemView extends AppCompatActivity {
    private DBHelper dbHelper;
    private List<ItemModel> itemList;
    private RecyclerView recyclerView;
    private ItemRecyclerAdapter itemAdapter;
    private int collectionId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityCollectionItemViewBinding binding = ActivityCollectionItemViewBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        dbHelper = new DBHelper(this);

        Intent intent = getIntent();
        collectionId = intent.getIntExtra("collectionId", -1);  // Use the class-level variable

        if (collectionId != -1) {
            // Create and start a new Thread with FetchDataRunnable
            FetchDataRunnable fetchDataRunnable = new FetchDataRunnable(collectionId);
            new Thread(fetchDataRunnable).start();
        } else {
            Log.e("CollectionItemView", "Invalid collectionId");
        }

        binding.AddItemFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CollectionItemView.this, AddItem.class);
                intent.putExtra("collectionId", collectionId);
                startActivityForResult(intent, 1);
            }
        });
    }
    private class FetchDataRunnable implements Runnable {
        private int collectionId;

        FetchDataRunnable(int collectionId) {
            this.collectionId = collectionId;
        }

        @Override
        public void run() {
            // Perform background operation (fetch data)
            final List<ItemModel> result = dbHelper.getItemsForCollection(collectionId, CollectionItemView.this);

            // Post the result back to the main thread using a Handler
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    // Update UI on the main thread
                    itemList = result;
                    recyclerView = findViewById(R.id.recyclerViewItems);
                    itemAdapter = new ItemRecyclerAdapter(itemList);
                    recyclerView.setLayoutManager(new LinearLayoutManager(CollectionItemView.this));
                    recyclerView.setAdapter(itemAdapter);
                }
            });
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK) {
            if (data != null && data.hasExtra("collectionId")) {
                collectionId = data.getIntExtra("collectionId", -1);
                refreshItemList();
            }
        }
    }
    private void refreshItemList() {
        itemList.clear();
        itemList.addAll(dbHelper.getItemsForCollection(collectionId, this));
        itemAdapter.notifyDataSetChanged();
    }

}
