package com.example.isk;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadActivityUser extends AppCompatActivity {

    URL ImageUrl;
    InputStream is;
    Bitmap bmImg;
    ProgressDialog p;

    TextView judul, isi, tanggal;
    ImageView kembali, gambar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thread_user);
        getSupportActionBar().hide();

        kembali = findViewById(R.id.tombolKembali);

        judul = findViewById(R.id.judulThread);
        gambar = findViewById(R.id.gambarThread);
        isi = findViewById(R.id.isiThread);
        tanggal = findViewById(R.id.tanggalThread);

        ExecutorService executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());

        int id = getIntent().getIntExtra("id", 0);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            judul.setText(bundle.getString("judul"));
            isi.setText(bundle.getString("isi"));
            tanggal.setText(bundle.getString("tanggal"));

            String gambarURL = bundle.getString("gambar");
            Log.d("Lihat Gambar", "Hasil Gambar: " + gambarURL);

            if (gambarURL != null) {
                p = new ProgressDialog(ThreadActivityUser.this);
                p.setMessage("Downloading...");
                p.setIndeterminate(false);
                p.setCancelable(false);
                p.show();

                executor.execute(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            ImageUrl = new URL(gambarURL);
                            HttpURLConnection conn = (HttpURLConnection) ImageUrl.openConnection();
                            conn.setDoInput(true);
                            conn.connect();
                            is = conn.getInputStream();
                            BitmapFactory.Options options = new BitmapFactory.Options();
                            options.inPreferredConfig = Bitmap.Config.RGB_565;
                            bmImg = BitmapFactory.decodeStream(is, null, options);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                if (gambar != null) {
                                    p.hide();
                                    gambar.setImageBitmap(bmImg);
                                } else {
                                    p.show();
                                }
                            }
                        });
                    }
                });
            } else {
                gambar.setImageResource(R.drawable.telescope);
            }
        }

        kembali.setOnClickListener(v -> {
            finish();
        });
    }
}