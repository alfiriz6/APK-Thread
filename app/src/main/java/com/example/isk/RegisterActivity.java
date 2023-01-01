package com.example.isk;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
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
import com.example.isk.api.User;
import com.example.isk.api.UserInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    AlertDialog.Builder builder;
    EditText nama, username, email, password, konfirmasiPassword;
    Button daftar;
    TextView masuk;
    boolean passwordVisible;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getSupportActionBar().hide();

        nama = findViewById(R.id.nama);
        username = findViewById(R.id.username);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        konfirmasiPassword = findViewById(R.id.konfirmasiPassword);
        daftar = findViewById(R.id.tombolDaftar);
        masuk = findViewById(R.id.tombolMasuk);

        builder = new AlertDialog.Builder(RegisterActivity.this);

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

        konfirmasiPassword.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_RIGHT = 2;

                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getRawX() >= (konfirmasiPassword.getRight() - konfirmasiPassword.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        if (passwordVisible) {
                            konfirmasiPassword.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.ic_baseline_visibility_24, 0);
                            konfirmasiPassword.setTransformationMethod(new PasswordTransformationMethod());
                            passwordVisible = false;

                        } else {
                            konfirmasiPassword.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.ic_baseline_visibility_off_24, 0);
                            konfirmasiPassword.setTransformationMethod(null);
                            passwordVisible = true;
                        }
                        return true;
                    }
                }
                return false;
            }
        });

        daftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserInterface userInterface = APIClient.getClient().create(UserInterface.class);
                Call<User> call = userInterface.postUser(username.getText().toString(),
                        nama.getText().toString(),
                        email.getText().toString(),
                        password.getText().toString(),
                        konfirmasiPassword.getText().toString());
                call.enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        if (response.body().getStatus() == 200) {
                            builder.setTitle("Registrasi Berhasil")
                                    .setMessage("Silakan login untuk melanjutkan")
                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                            startActivity(intent);
                                            finish();
                                        }
                                    }).show();
                        } else {
                            String error = "TERJADI KESALAHAN:";
                            for (int i = 0; i < response.body().getError().size(); i++) {
                                error += "\n" + (i + 1) + ". " + response.body().getError().get(i);
                            }
                            Toast.makeText(RegisterActivity.this, error, Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {
                        Toast.makeText(RegisterActivity.this, "Registrasi gagal", Toast.LENGTH_LONG).show();
                        Log.d("Mau Cari Error", t.getMessage());
                    }
                });
            }
        });

        masuk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }
}