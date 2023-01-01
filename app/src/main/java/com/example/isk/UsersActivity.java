package com.example.isk;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.example.isk.adapter.AdapterUser;
import com.example.isk.api.APIClient;
import com.example.isk.api.User;
import com.example.isk.api.UserInterface;
import com.example.isk.api.UserResponse;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;

public class UsersActivity extends AppCompatActivity {

    RecyclerView rvUsers;
    LinearLayoutManager linearLayoutManager;
    AdapterUser adapterUser;
    ArrayList<User> listUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Daftar Pengguna");


        rvUsers = findViewById(R.id.rvUsers);
        listUser = new ArrayList<>();
        //get data user from api and add to listUser
        UserInterface userInterface = APIClient.getClient().create(UserInterface.class);
        Call<UserResponse> call = userInterface.getUser();
        call.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, retrofit2.Response<UserResponse> response) {
                try {
                    if (response.isSuccessful()) {
                        if (response.body().getStatus()==200) {
                            listUser.addAll(response.body().getData());
                            adapterUser = new AdapterUser(listUser);
                            linearLayoutManager = new LinearLayoutManager(UsersActivity.this);
                            rvUsers.setLayoutManager(linearLayoutManager);
                            rvUsers.setAdapter(adapterUser);
                        } else {
                            Toast.makeText(UsersActivity.this, "Data pengguna kosong", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(UsersActivity.this, "Gagal mengambil data pengguna", Toast.LENGTH_LONG).show();
                    }
                } catch (Exception e) {
                    Toast.makeText(UsersActivity.this, "TERJADI KESALAHAN", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                Toast.makeText(UsersActivity.this, "GAGAL", Toast.LENGTH_LONG).show();
            }
        });
    }
}