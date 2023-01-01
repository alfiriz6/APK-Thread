package com.example.isk;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.isk.api.APIClient;
import com.example.isk.api.Thread;
import com.example.isk.api.ThreadInterface;
import com.example.isk.api.Thread;
import com.example.isk.api.ThreadInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ThreadActivityAdmin extends AppCompatActivity {

    AlertDialog.Builder builder;
    EditText judul, isi, gambar;
    ImageView kembali;
    Button ubah, hapus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thread_admin);

        getSupportActionBar().hide();

        kembali = findViewById(R.id.tombolKembali);

        judul = findViewById(R.id.judulThread);
        gambar = findViewById(R.id.gambarThread);
        isi = findViewById(R.id.isiThread);

        ubah = findViewById(R.id.tombolUbahThread);
        hapus = findViewById(R.id.tombolHapus);

        builder = new AlertDialog.Builder(ThreadActivityAdmin.this);

        int id = getIntent().getIntExtra("id", 0);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            judul.setText(bundle.getString("judul"));
            isi.setText(bundle.getString("isi"));
            gambar.setText(bundle.getString("gambar"));
        }

        ubah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder.setTitle("Konfirmasi Ubah Thread")
                        .setMessage("Apakah Anda yakin ingin mengubah thread?")
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
                                String judulBaru = judul.getText().toString();
                                String gambarBaru = gambar.getText().toString();
                                String isiBaru = isi.getText().toString();

                                if (judulBaru.isEmpty()) {
                                    Toast.makeText(ThreadActivityAdmin.this, "Judul thread harus diisi!", Toast.LENGTH_LONG).show();
                                } else if (isiBaru.isEmpty()) {
                                    Toast.makeText(ThreadActivityAdmin.this, "Isian thread tidak boleh kosong!", Toast.LENGTH_LONG).show();
                                } else if (gambarBaru.isEmpty()) {
                                    gambarBaru = "https://i.ibb.co/0nQqZ3r/blank.png";
                                    Thread thread = new Thread(judulBaru, gambarBaru, isiBaru);
                                    Log.d("Liat Thread", "Hasil: " + thread);
                                    Log.d("Liat Input Judul", "Hasil: " + thread.getJudul());
                                    Log.d("Liat Input Gambar", "Hasil: " + thread.getGambar());
                                    Log.d("Liat Input Isi", "Hasil: " + thread.getIsi());

                                    ThreadInterface threadInterface = APIClient.getClient().create(ThreadInterface.class);
                                    Call<Thread> call = threadInterface.updateThread(id, thread);

                                    call.enqueue(new Callback<Thread>() {
                                        @Override
                                        public void onResponse(Call<Thread> call, Response<Thread> response) {
                                            if (response.body().getStatus() == 200) {
                                                Toast.makeText(ThreadActivityAdmin.this, "Thread berhasil diubah", Toast.LENGTH_LONG).show();
                                                Intent intent = new Intent(ThreadActivityAdmin.this, AdminBerandaActivity.class);
                                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                                startActivity(intent);
                                                Intent intent1 = new Intent(ThreadActivityAdmin.this, ThreadsActivityAdmin.class);
                                                startActivity(intent1);
                                            } else {
                                                Toast.makeText(ThreadActivityAdmin.this, "Thread gagal diubah", Toast.LENGTH_LONG).show();
                                            }
                                        }

                                        @Override
                                        public void onFailure(Call<Thread> call, Throwable t) {
                                            Toast.makeText(ThreadActivityAdmin.this, "GAGAL", Toast.LENGTH_LONG).show();
                                            Log.d("Mau Cari Error", t.getMessage());
                                        }
                                    });
                                } else {
                                    Thread thread = new Thread(judulBaru, gambarBaru, isiBaru);
                                    Log.d("Liat Thread", "Hasil: " + thread);
                                    Log.d("Liat Input Judul", "Hasil: " + thread.getJudul());
                                    Log.d("Liat Input Gambar", "Hasil: " + thread.getGambar());
                                    Log.d("Liat Input Isi", "Hasil: " + thread.getIsi());

                                    ThreadInterface threadInterface = APIClient.getClient().create(ThreadInterface.class);
                                    Call<Thread> call = threadInterface.updateThread(id, thread);

                                    call.enqueue(new Callback<Thread>() {
                                        @Override
                                        public void onResponse(Call<Thread> call, Response<Thread> response) {
                                            if (response.body().getStatus() == 200) {
                                                Toast.makeText(ThreadActivityAdmin.this, "Thread berhasil diubah", Toast.LENGTH_LONG).show();
                                                Intent intent = new Intent(ThreadActivityAdmin.this, AdminBerandaActivity.class);
                                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                                startActivity(intent);
                                                Intent intent1 = new Intent(ThreadActivityAdmin.this, ThreadsActivityAdmin.class);
                                                startActivity(intent1);
                                            } else {
                                                Toast.makeText(ThreadActivityAdmin.this, "Thread gagal diubah", Toast.LENGTH_LONG).show();
                                            }
                                        }

                                        @Override
                                        public void onFailure(Call<Thread> call, Throwable t) {
                                            Toast.makeText(ThreadActivityAdmin.this, "GAGAL", Toast.LENGTH_LONG).show();
                                            Log.d("Mau Cari Error", t.getMessage());
                                        }
                                    });
                                }
                            }
                        }).show();
            }
        });

        hapus.setOnClickListener(v -> {
            builder.setTitle("Konfirmasi Hapus Thread")
                    .setMessage("Apakah Anda yakin ingin menghapus thread ini?")
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
                            ThreadInterface threadInterface = APIClient.getClient().create(ThreadInterface.class);
                            Call<Thread> call = threadInterface.deleteThread(id);
                            Log.d("Liat ID", "onResponse: " + id);
                            call.enqueue(new Callback<Thread>() {
                                @Override
                                public void onResponse(Call<Thread> call, Response<Thread> response) {
                                    Log.d("Liat Status", "onResponse: " + response.body());
                                    if (response.body().getStatus() == 200) {
                                        Toast.makeText(ThreadActivityAdmin.this, "Thread berhasil dihapus", Toast.LENGTH_LONG).show();
                                        Intent intent = new Intent(ThreadActivityAdmin.this, AdminBerandaActivity.class);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(intent);
                                        Intent intent1 = new Intent(ThreadActivityAdmin.this, ThreadsActivityAdmin.class);
                                        startActivity(intent1);
                                    } else {
                                        Toast.makeText(ThreadActivityAdmin.this, "Thread gagal dihapus", Toast.LENGTH_LONG).show();
                                    }
                                }

                                @Override
                                public void onFailure(Call<Thread> call, Throwable t) {
                                    Toast.makeText(ThreadActivityAdmin.this, "TERJADI KESALAHAN", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }).show();
        });

        kembali.setOnClickListener(v -> {
            finish();
        });
    }
}