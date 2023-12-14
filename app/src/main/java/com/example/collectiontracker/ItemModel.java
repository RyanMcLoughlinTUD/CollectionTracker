package com.example.collectiontracker;

import android.content.Context;
import android.graphics.Bitmap;

public class ItemModel {
    private int id;
    private int collectionId;
    private String itemName;
    private int itemAmount;
    private String itemDescription;
    private String photoId;
    private Bitmap itemImage;
    private DBHelper dbHelper;

    // Constructor
    public ItemModel(int id, int collectionId, String itemName, int itemAmount, String itemDescription, String photoId, Context context) {
        this.id = id;
        this.collectionId = collectionId;
        this.itemName = itemName;
        this.itemAmount = itemAmount;
        this.itemDescription = itemDescription;
        this.photoId = photoId;
        this.dbHelper = new DBHelper(context); // Replace 'context' with the actual context

        // Load the image when creating the ItemModel
        this.itemImage = dbHelper.getBitmapFromPhotoId(photoId);

    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCollectionId() {
        return collectionId;
    }

    public void setCollectionId(int collectionId) {
        this.collectionId = collectionId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public int getItemAmount() {
        return itemAmount;
    }

    public void setItemAmount(int itemAmount) {
        this.itemAmount = itemAmount;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }

    public Bitmap getItemImage() {
        return itemImage;
    }

    public void setItemImage(Bitmap itemImage) {
        this.itemImage = itemImage;
    }
}
