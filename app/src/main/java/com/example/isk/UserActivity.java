package com.example.isk;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.isk.api.APIClient;
import com.example.isk.api.User;
import com.example.isk.api.UserInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserActivity extends AppCompatActivity {

    AlertDialog.Builder builder;
    ImageView kembali;
    TextView nama, username, email;
    Button hapus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        getSupportActionBar().hide();

        kembali = findViewById(R.id.tombolKembali);

        nama = findViewById(R.id.nama);
        username = findViewById(R.id.username);
        email = findViewById(R.id.email);

        hapus = findViewById(R.id.tombolHapus);

        builder = new AlertDialog.Builder(UserActivity.this);

        int id = getIntent().getIntExtra("id", 0);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            nama.setText(bundle.getString("nama"));
            username.setText(bundle.getString("username"));
            email.setText(bundle.getString("email"));
        }

        hapus.setOnClickListener(v -> {
            builder.setTitle("Konfirmasi Hapus Akun")
                    .setMessage("Apakah Anda yakin ingin menghapus akun ini?")
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
                            UserInterface userInterface = APIClient.getClient().create(UserInterface.class);
                            Call<User> call = userInterface.deleteUser(id);
                            Log.d("Liat ID", "onResponse: " + id);
                            call.enqueue(new Callback<User>() {
                                @Override
                                public void onResponse(Call<User> call, Response<User> response) {
                                    Log.d("Liat Status", "onResponse: " + response.body());
                                    if (response.body().getStatus() == 200) {
                                        Toast.makeText(UserActivity.this, "User berhasil dihapus", Toast.LENGTH_LONG).show();
                                        Intent intent = new Intent(UserActivity.this, AdminBerandaActivity.class);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(intent);
                                        Intent intent1 = new Intent(UserActivity.this, UsersActivity.class);
                                        startActivity(intent1);
                                    } else {
                                        Toast.makeText(UserActivity.this, "User gagal dihapus", Toast.LENGTH_LONG).show();
                                    }
                                }

                                @Override
                                public void onFailure(Call<User> call, Throwable t) {
                                    Toast.makeText(UserActivity.this, "TERJADI KESALAHAN", Toast.LENGTH_SHORT).show();
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