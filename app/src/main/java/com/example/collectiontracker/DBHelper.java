package com.example.collectiontracker;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "CollectionTracker.db";
    private static final int DATABASE_VERSION = 1;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create CollectionsTable
        db.execSQL("CREATE TABLE IF NOT EXISTS CollectionsTable ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "collection_name TEXT)");

        // Create ItemsTable
        db.execSQL("CREATE TABLE IF NOT EXISTS ItemsTable ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "collection_id INTEGER, "
                + "item_name TEXT, "
                + "item_amount INTEGER, "
                + "item_description TEXT, "
                + "photo_id TEXT, "
                + "FOREIGN KEY(collection_id) REFERENCES CollectionsTable(id))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Handle database upgrades here
    }

    public void addCollection(String collectionName) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("collection_name", collectionName);
        db.insert("CollectionsTable", null, values);
        db.close();
    }

    public void addItem(int collectionId, String itemName, int itemAmount, String itemDescription, String photoId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("collection_id", collectionId);
        values.put("item_name", itemName);
        values.put("item_amount", itemAmount);
        values.put("item_description", itemDescription);
        values.put("photo_id", photoId);  // Save the photo ID
        db.insert("ItemsTable", null, values);
        db.close();
    }

    public List<ItemModel> getItemsForCollection(int collectionId, Context context) {
        List<ItemModel> items = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {"id", "collection_id", "item_name", "item_amount", "item_description", "photo_id"};
        String selection = "collection_id=?";
        String[] selectionArgs = {String.valueOf(collectionId)};

        Cursor cursor = null;

        try {
            cursor = db.query("ItemsTable", columns, selection, selectionArgs, null, null, null);

            if (cursor != null && cursor.moveToFirst()) {
                do {
                    int itemId = cursor.getInt(cursor.getColumnIndex("id"));
                    String itemName = cursor.getString(cursor.getColumnIndex("item_name"));
                    int itemAmount = cursor.getInt(cursor.getColumnIndex("item_amount"));
                    String itemDescription = cursor.getString(cursor.getColumnIndex("item_description"));
                    String photoId = cursor.getString(cursor.getColumnIndex("photo_id"));

                    Bitmap itemImage = getBitmapFromPhotoId(photoId);

                    // Pass the context to the ItemModel constructor
                    ItemModel item = new ItemModel(itemId, collectionId, itemName, itemAmount, itemDescription, photoId, context);
                    items.add(item);
                } while (cursor.moveToNext());
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            db.close();
        }

        return items;
    }

    public List<CollectionModel> getCollections() {
        List<CollectionModel> collections = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM CollectionsTable", null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndex("id"));
                String collectionName = cursor.getString(cursor.getColumnIndex("collection_name"));

                CollectionModel collection = new CollectionModel(id, collectionName);
                collections.add(collection);
            } while (cursor.moveToNext());

            cursor.close();
        }

        db.close();

        return collections;
    }


    public Bitmap getBitmapFromPhotoId(String photoId) {
        // Replace this file path with your actual file storage logic
        String imagePath = "/data/data/com.example.collectiontracker/files/Pictures/" + photoId;


        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.ARGB_8888; // Adjust based on your requirements
        return BitmapFactory.decodeFile(imagePath, options);
    }
}
