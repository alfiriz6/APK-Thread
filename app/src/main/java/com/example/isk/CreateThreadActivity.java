package com.example.isk;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.isk.api.APIClient;
import com.example.isk.api.Thread;
import com.example.isk.api.ThreadInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateThreadActivity extends AppCompatActivity {

    AlertDialog.Builder builder;
    EditText judul, gambar, isi;
    Button simpan;
    ImageView kembali;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_thread);

        getSupportActionBar().hide();

        judul = findViewById(R.id.judulThread);
        gambar = findViewById(R.id.gambarThread);
        isi = findViewById(R.id.isiThread);
        simpan = findViewById(R.id.tombolSimpan);
        kembali = findViewById(R.id.tombolKembali);

        builder = new AlertDialog.Builder(CreateThreadActivity.this);

        simpan.setOnClickListener(v -> {
            String judulThread = judul.getText().toString();
            String gambarThread = gambar.getText().toString();
            String isiThread = isi.getText().toString();

            if (judulThread.isEmpty()) {
                Toast.makeText(CreateThreadActivity.this, "Judul thread harus diisi!", Toast.LENGTH_LONG).show();
            } else if (isiThread.isEmpty()) {
                Toast.makeText(CreateThreadActivity.this, "Isian thread tidak boleh kosong!", Toast.LENGTH_LONG).show();
            } else if (gambarThread.isEmpty()) {
                gambarThread = "https://i.ibb.co/0nQqZ1F/blank.png";
                ThreadInterface threadInterface = APIClient.getClient().create(ThreadInterface.class);
                Call<Thread> call = threadInterface.postThread(judulThread, gambarThread, isiThread);
                call.enqueue(new Callback<Thread>() {
                    @Override
                    public void onResponse(Call<Thread> call, Response<Thread> response) {
                        if (response.body().getStatus() == 201) {
                            builder.setTitle("Sukses Tersimpan")
                                    .setMessage("Thread berhasil disimpan")
                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            Intent intent = new Intent(CreateThreadActivity.this, AdminBerandaActivity.class);
                                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                            startActivity(intent);
                                            Intent intent1 = new Intent(CreateThreadActivity.this, ThreadsActivityAdmin.class);
                                            startActivity(intent1);
                                        }
                                    }).show();
                        } else {
                            Toast.makeText(CreateThreadActivity.this, "Gagal menyimpan thread", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Thread> call, Throwable t) {
                        Toast.makeText(CreateThreadActivity.this, "TERJADI KESALAHAN", Toast.LENGTH_LONG).show();
                        Log.d("Mau Cari Error", t.getMessage());
                    }
                });
            } else {
                ThreadInterface threadInterface = APIClient.getClient().create(ThreadInterface.class);
                Call<Thread> call = threadInterface.postThread(judulThread, gambarThread, isiThread);
                call.enqueue(new Callback<Thread>() {
                    @Override
                    public void onResponse(Call<Thread> call, Response<Thread> response) {
                        if (response.body().getStatus() == 201) {
                            builder.setTitle("Sukses Tersimpan")
                                    .setMessage("Thread berhasil disimpan")
                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            Intent intent = new Intent(CreateThreadActivity.this, AdminBerandaActivity.class);
                                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                            startActivity(intent);
                                            Intent intent1 = new Intent(CreateThreadActivity.this, ThreadsActivityAdmin.class);
                                            startActivity(intent1);
                                        }
                                    }).show();
                        } else {
                            Toast.makeText(CreateThreadActivity.this, "Gagal menyimpan thread", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Thread> call, Throwable t) {
                        Toast.makeText(CreateThreadActivity.this, "TERJADI KESALAHAN", Toast.LENGTH_LONG).show();
                        Log.d("Mau Cari Error", t.getMessage());
                    }
                });
            }
        });

        kembali.setOnClickListener(v -> {
            finish();
        });
    }
}