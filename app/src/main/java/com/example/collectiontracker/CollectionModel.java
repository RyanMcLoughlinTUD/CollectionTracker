package com.example.collectiontracker;

public class CollectionModel {
    private int id;
    private String collectionName;

    public CollectionModel(int id, String collectionName) {
        this.id = id;
        this.collectionName = collectionName;
    }

    public int getId() {
        return id;
    }

    public String getCollectionName() {
        return collectionName;
    }

}
