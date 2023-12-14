package com.example.collectiontracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.Manifest;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class AddItem extends AppCompatActivity {
    private DBHelper dbHelper;
    private int collectionId;
    private String itemName;
    private int itemAmount;
    private String itemDescription;
    private String imageFileName;

    static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int REQUEST_CAMERA_PERMISSION = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        dbHelper = new DBHelper(this);

        EditText itemNameEditText = findViewById(R.id.ItemName);
        EditText itemDescriptionEditText = findViewById(R.id.ItemDescription);
        EditText itemAmountEditText = findViewById(R.id.itemAmount);
        Button commitItemButton = findViewById(R.id.commitCollection);
        Button takePhotoButton = findViewById(R.id.btnTakePhoto);

        takePhotoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Call a method to handle photo capture
                dispatchTakePictureIntent();
            }
        });

        // Assuming collectionId is passed from the previous activity
        collectionId = getIntent().getIntExtra("collectionId", -1);

        commitItemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemName = itemNameEditText.getText().toString();
                itemDescription = itemDescriptionEditText.getText().toString();
                String itemAmountStr = itemAmountEditText.getText().toString();

                if (itemName.isEmpty() || itemAmountStr.isEmpty()) {
                    Toast.makeText(AddItem.this, "Please enter item name and amount", Toast.LENGTH_SHORT).show();
                    return;
                }

                itemAmount = Integer.parseInt(itemAmountStr);

                if (collectionId != -1) {
                    // Check if a photo has been captured before attempting to add the item
                    if (itemDescription != null && imageFileName != null) { // Add this condition
                        // Add item to the database
                        dbHelper.addItem(collectionId, itemName, itemAmount, itemDescription, imageFileName);

                        Toast.makeText(AddItem.this, "Item added successfully", Toast.LENGTH_SHORT).show();
                        setResult(RESULT_OK, new Intent().putExtra("collectionId", collectionId));

                        finish();
                    } else {
                        Toast.makeText(AddItem.this, "Please capture a photo before committing", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(AddItem.this, "Invalid collection ID", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void dispatchTakePictureIntent() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA_PERMISSION);
            return;
        }

        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create a file to save the image
            File photoFile = createImageFile();
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(
                        this,
                        "com.example.collectiontracker.fileprovider",
                        photoFile
                );
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        }
    }

    private File createImageFile() {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        imageFileName = "JPEG_" + timeStamp + "_";
        // Append "Pictures" to the path
        File storageDir = new File(getFilesDir(), "Pictures");
        if (!storageDir.exists()) {
            storageDir.mkdirs();
        }
        try {
            File imageFile = File.createTempFile(imageFileName, ".jpg", storageDir);
            Log.d("FileCreation", "Image file path: " + imageFile.getAbsolutePath());
            imageFileName = imageFile.getName();
            return imageFile;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CAMERA_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, proceed to open the camera
                dispatchTakePictureIntent();
            } else {
                // Permission denied, show a message or handle accordingly
                Toast.makeText(this, "Camera permission is required to take a photo", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            // Handle the captured photo
            Toast.makeText(this, "Photo captured successfully", Toast.LENGTH_SHORT).show();
        }
    }

}
