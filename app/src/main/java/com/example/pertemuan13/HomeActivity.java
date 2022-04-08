package com.example.pertemuan13;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class HomeActivity extends AppCompatActivity {
    EditText url_et;
    EditText desc_et;
    Button view_bt;
    GalleryData galleryData;
    String url_str, desc_str;
    FirebaseDatabase database;
    DatabaseReference myRef;

    View.OnClickListener view = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            url_str = url_et.getText().toString();
            desc_str = desc_et.getText().toString();
            galleryData.setDescription(desc_str);
            galleryData.setUrl(url_str);
            if(checker()){
                database = FirebaseDatabase.getInstance();
                myRef = database.getReference("gallery_data");
                myRef.setValue(galleryData);

                Intent toGallery = new Intent(getApplicationContext(), GalleryActivity.class);
                startActivity(toGallery);
            }

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        url_et = findViewById(R.id.url_et);
        desc_et = findViewById(R.id.desc_et);
        view_bt = findViewById(R.id.viewgallery_bt);
        galleryData = new GalleryData();

        view_bt.setOnClickListener(view);

    }

    public boolean checker(){
        boolean flag = true;
        String error = "Cannot register because error:\n";

        if(url_str.isEmpty()){
            flag = false;
            error = error + "- url is empty\n";
        }
        if(desc_str.isEmpty()){
            flag = false;
            error = error + "- desc is empty\n";
        }

        if(flag == false){
            Toast.makeText(HomeActivity.this, error, Toast.LENGTH_SHORT).show();
        }

        return flag;
    }
}