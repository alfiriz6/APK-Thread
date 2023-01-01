package com.example.isk;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.widget.TextView;

public class UserBerandaActivity extends AppCompatActivity {

    AlertDialog.Builder builder;
    SharedPreferences pref;
    CardView thread, profil, about, keluar;
    TextView tvTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_beranda);
        getSupportActionBar().hide();

        tvTitle = findViewById(R.id.tvTitle);

        pref = getSharedPreferences("token", MODE_PRIVATE);

        //decode token
        String token = pref.getString("token", null);
        String[] split = token.split("\\.");
        String payload = split[1];
        String payloadDecode = new String(Base64.decode(payload, Base64.DEFAULT));
        String[] splitPayload = payloadDecode.split(",");
        String uid = splitPayload[2].substring(7, splitPayload[2].length() - 1);
        String nama = splitPayload[5].substring(8, splitPayload[5].length() - 1);

        Log.d("Liat ID", "Hasil: " + uid);
        tvTitle.setText("Selamat datang, " + nama);

        builder = new AlertDialog.Builder(UserBerandaActivity.this);

        thread = findViewById(R.id.tombolThread);
        about = findViewById(R.id.tombolAbout);
        keluar = findViewById(R.id.tombolKeluar);
        profil = findViewById(R.id.tombolProfil);

        thread.setOnClickListener(v -> {
            Intent intent = new Intent(UserBerandaActivity.this, ThreadsActivityUser.class);
            startActivity(intent);
        });

        profil.setOnClickListener(v -> {
            Intent intent = new Intent(UserBerandaActivity.this, ProfilActivity.class);
            startActivity(intent);
        });

        about.setOnClickListener(v -> {
            Intent intent = new Intent(UserBerandaActivity.this, TentangActivity.class);
            startActivity(intent);
        });

        keluar.setOnClickListener(v -> {
            builder.setTitle("Konfirmasi Keluar")
                    .setMessage("Apakah Anda yakin ingin keluar?")
                    .setCancelable(false)
                    .setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    })
                    .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            @SuppressLint("CommitPrefEdits")
                            SharedPreferences.Editor editor = pref.edit();
                            editor.clear();
                            editor.apply();

                            Intent intent = new Intent(UserBerandaActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }).show();
        });
    }
}