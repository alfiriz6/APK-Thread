package com.example.isk;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.isk.api.APIClient;
import com.example.isk.api.LoginResponse;
import com.example.isk.api.UserInterface;

import retrofit2.Call;
import retrofit2.Callback;

public class MainActivity extends AppCompatActivity {

    AlertDialog.Builder builder;
    EditText username, password;
    Button masuk;
    TextView daftar;
    SharedPreferences pref;
    boolean passwordVisible;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        pref = getSharedPreferences("token", MODE_PRIVATE);

        if (pref.getInt("role", 99) == 0) {
            Intent intent = new Intent(MainActivity.this, AdminBerandaActivity.class);
            startActivity(intent);
            finish();
        } else if (pref.getInt("role", 99) == 1) {
            Intent intent = new Intent(MainActivity.this, UserBerandaActivity.class);
            startActivity(intent);
            finish();
        } else {
            setContentView(R.layout.activity_main);
            getSupportActionBar().hide();

            username = findViewById(R.id.username);
            password = findViewById(R.id.password);
            masuk = findViewById(R.id.tombolMasuk);
            daftar = findViewById(R.id.tombolDaftar);

            builder = new AlertDialog.Builder(MainActivity.this);

            password.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    final int DRAWABLE_RIGHT = 2;

                    if (event.getAction() == MotionEvent.ACTION_UP) {
                        if (event.getRawX() >= (password.getRight() - password.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                            if (passwordVisible) {
                                password.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.ic_baseline_visibility_24, 0);
                                password.setTransformationMethod(new PasswordTransformationMethod());
                                passwordVisible = false;
                            } else {
                                password.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.ic_baseline_visibility_off_24, 0);
                                password.setTransformationMethod(null);
                                passwordVisible = true;
                            }
                            return true;
                        }
                    }
                    return false;
                }
            });

            masuk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (username.getText().toString().length() == 0) {
                        Toast.makeText(MainActivity.this, "Username atau email harus diisi!", Toast.LENGTH_LONG).show();
                    } else if (password.getText().toString().length() == 0) {
                        Toast.makeText(MainActivity.this, "Password harus diisi!", Toast.LENGTH_LONG).show();
                    } else {
                        UserInterface userInterface = APIClient.getClient().create(UserInterface.class);
                        Call<LoginResponse> call = userInterface.login(username.getText().toString(), password.getText().toString());
                        call.enqueue(new Callback<LoginResponse>() {
                            @Override
                            public void onResponse(Call<LoginResponse> call, retrofit2.Response<LoginResponse> response) {
                                if (response.body().getStatus() == 200) {
                                    //Shared Preferences
                                    SharedPreferences.Editor editor = pref.edit();
                                    editor.putString("token", response.body().getToken());
                                    editor.apply();
                                    editor.putInt("role", response.body().getRole());
                                    editor.apply();

                                    Log.d("Liat Pref", "onResponse: " + pref.getInt("role", 99));

                                    builder.setTitle("Login Berhasil")
                                            .setMessage("Selamat datang, " + response.body().getNama() + " di Intergalactic Science Kingdom")
                                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialogInterface, int i) {
                                                    if (response.body().getRole() == 0) {
                                                        Intent intent = new Intent(MainActivity.this, AdminBerandaActivity.class);
                                                        startActivity(intent);
                                                        finish();
                                                    } else if (response.body().getRole() == 1) {
                                                        Intent intent = new Intent(MainActivity.this, UserBerandaActivity.class);
                                                        startActivity(intent);
                                                        finish();
                                                    }
                                                }
                                            }).show();
                                } else {
                                    Toast.makeText(MainActivity.this, response.body().getError(), Toast.LENGTH_LONG).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<LoginResponse> call, Throwable t) {
                                Toast.makeText(MainActivity.this, "Login gagal", Toast.LENGTH_LONG).show();
                                Log.d("Mau Cari Error", t.getMessage());
                            }
                        });
                    }
                }
            });

            daftar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
            });
        }
    }
}