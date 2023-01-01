package com.example.isk;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;

public class TentangActivity extends AppCompatActivity {

    ImageView kembali;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tentang);
        getSupportActionBar().hide();

        kembali = findViewById(R.id.tombolKembali);

        kembali.setOnClickListener(v -> {
            finish();
        });
    }
}