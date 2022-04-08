package com.example.pertemuan13;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class GalleryActivity extends AppCompatActivity {
    ImageView gallery_iv;
    TextView gallery_desc_tv;
    FirebaseDatabase database;
    DatabaseReference myRef;
    GalleryData galleryData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

        gallery_iv = findViewById(R.id.gallery_iv);
        gallery_desc_tv = findViewById(R.id.gallery_desc_tv);

        galleryData = new GalleryData();

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("gallery_data");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                galleryData = snapshot.getValue(GalleryData.class);
                gallery_desc_tv.setText(galleryData.description);
                Glide.with(GalleryActivity.this).load(galleryData.url).into(gallery_iv);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(GalleryActivity.this, "Tidak bisa meload data", Toast.LENGTH_SHORT);
            }
        });

    }
}