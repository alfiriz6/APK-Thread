package com.example.isk;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.isk.adapter.AdapterThreadAdmin;
import com.example.isk.api.APIClient;
import com.example.isk.api.Thread;
import com.example.isk.api.ThreadInterface;
import com.example.isk.api.ThreadResponse;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;

public class ThreadsActivityAdmin extends AppCompatActivity {

    RecyclerView rvThreads;
    LinearLayoutManager linearLayoutManager;
    AdapterThreadAdmin adapterThreadAdmin;
    ArrayList<Thread> listThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_threads_admin);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Threads");

        rvThreads = findViewById(R.id.rvThreads);
        listThread = new ArrayList<>();
        //get data user from api and add to listThread
        ThreadInterface threadInterface = APIClient.getClient().create(ThreadInterface.class);
        Call<ThreadResponse> call = threadInterface.getThread();
        call.enqueue(new Callback<ThreadResponse>() {
            @Override
            public void onResponse(Call<ThreadResponse> call, retrofit2.Response<ThreadResponse> response) {
                try {
                    if (response.isSuccessful()) {
                        if (response.body().getStatus() == 200) {
                            listThread.addAll(response.body().getData());
                            adapterThreadAdmin = new AdapterThreadAdmin(listThread);
                            linearLayoutManager = new LinearLayoutManager(ThreadsActivityAdmin.this);
                            rvThreads.setLayoutManager(linearLayoutManager);
                            rvThreads.setAdapter(adapterThreadAdmin);
                        } else {
                            Toast.makeText(ThreadsActivityAdmin.this, "Data thread kosong", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(ThreadsActivityAdmin.this, "Gagal mengambil data thread", Toast.LENGTH_LONG).show();
                    }
                } catch (Exception e) {
                    Toast.makeText(ThreadsActivityAdmin.this, "TERJADI KESALAHAN", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ThreadResponse> call, Throwable t) {
                Toast.makeText(ThreadsActivityAdmin.this, "GAGAL", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_add_thread, menu);
        return true;
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(android.view.MenuItem item) {
        switch (item.getItemId()) {
            case R.id.tombolTambah:
                Intent intent = new Intent(ThreadsActivityAdmin.this, CreateThreadActivity.class);
                startActivity(intent);
                return true;
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}