package com.example.isk;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.isk.api.APIClient;
import com.example.isk.api.User;
import com.example.isk.api.UserInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfilActivity extends AppCompatActivity {

    AlertDialog.Builder builder;
    EditText nama, email;
    Button ubahProfil;
    ImageView kembali;
    SharedPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);
        getSupportActionBar().hide();

        kembali = findViewById(R.id.tombolKembali);

        nama = findViewById(R.id.nama);
        email = findViewById(R.id.email);
        ubahProfil = findViewById(R.id.tombolUbahProfil);

        builder = new AlertDialog.Builder(ProfilActivity.this);

        pref = getSharedPreferences("token", MODE_PRIVATE);

        //decode token
        String token = pref.getString("token", null);
        String[] split = token.split("\\.");
        String payload = split[1];
        String payloadDecode = new String(Base64.decode(payload, Base64.DEFAULT));
        String[] splitPayload = payloadDecode.split(",");
        String uid = splitPayload[2].substring(7, splitPayload[2].length() - 1);
        String emailLoad = splitPayload[3].substring(9, splitPayload[3].length() - 1);
        String namaLoad = splitPayload[5].substring(8, splitPayload[5].length() - 1);

        int id = Integer.parseInt(uid);
        Log.d("Liat ID", "Hasil: " + id);
        nama.setText(namaLoad);
        email.setText(emailLoad);

        ubahProfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder.setTitle("Konfirmasi Ubah Profil")
                        .setMessage("Apakah Anda yakin ingin mengubah profil Anda?")
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
                                String namaBaru = nama.getText().toString();
                                String emailBaru = email.getText().toString();
                                String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";


                                if (namaBaru.isEmpty()) {
                                    Toast.makeText(ProfilActivity.this, "Nama harus diisi!", Toast.LENGTH_LONG).show();
                                } else if (emailBaru.isEmpty()) {
                                    Toast.makeText(ProfilActivity.this, "Email harus diisi!", Toast.LENGTH_LONG).show();
                                } else if (emailBaru.matches(emailPattern)) {
                                    User user = new User(namaBaru, emailBaru);
                                    Log.d("Liat User", "Hasil: " + user);
                                    Log.d("Liat Input Nama", "Hasil: " + user.getNama());
                                    Log.d("Liat Input Email", "Hasil: " + user.getEmail());

                                    UserInterface userInterface = APIClient.getClient().create(UserInterface.class);
                                    Call<User> call = userInterface.updateUser(id, user);

                                    call.enqueue(new Callback<User>() {
                                        @Override
                                        public void onResponse(Call<User> call, Response<User> response) {
                                            if (response.body().getStatus() == 200) {
                                                SharedPreferences.Editor editor = pref.edit();
                                                editor.putString("token", response.body().getToken());
                                                editor.apply();

                                                Toast.makeText(ProfilActivity.this, "Profil berhasil diubah", Toast.LENGTH_LONG).show();
                                                Intent intent = new Intent(ProfilActivity.this, MainActivity.class);
                                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                                startActivity(intent);
                                                Intent intent1 = new Intent(ProfilActivity.this, UserBerandaActivity.class);
                                                startActivity(intent1);
                                            } else {
                                                String error = "TERJADI KESALAHAN:";
                                                for (int i = 0; i < response.body().getError().size(); i++) {
                                                    error += "\n" + (i + 1) + ". " + response.body().getError().get(i);
                                                }
                                                Toast.makeText(ProfilActivity.this, error, Toast.LENGTH_LONG).show();
                                            }
                                        }

                                        @Override
                                        public void onFailure(Call<User> call, Throwable t) {
                                            Toast.makeText(ProfilActivity.this, "Profil gagal berubah", Toast.LENGTH_LONG).show();
                                            Log.d("Mau Cari Error", t.getMessage());
                                        }
                                    });
                                } else {
                                    Toast.makeText(ProfilActivity.this, "Email harus valid!", Toast.LENGTH_LONG).show();
                                }
                            }
                        }).show();
            }
        });

        kembali.setOnClickListener(v -> {
            finish();
        });
    }
}