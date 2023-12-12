package com.example.collectiontracker;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

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
                + "photo_id INTEGER, "
                + "FOREIGN KEY(collection_id) REFERENCES CollectionsTable(id))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Handle database upgrades here
        // Example:
        // db.execSQL("DROP TABLE IF EXISTS TableName");
        // onCreate(db);
    }

    public void addCollection(String collectionName) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("collection_name", collectionName);
        db.insert("CollectionsTable", null, values);
        db.close();
    }
    public void addItem(int collectionId, String itemName, int itemAmount, String itemDescription, int photoId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("collection_id", collectionId);
        values.put("item_name", itemName);
        values.put("item_amount", itemAmount);
        values.put("item_description", itemDescription);
        values.put("photo_id", photoId);
        db.insert("ItemsTable", null, values);
        db.close();
    }
}
