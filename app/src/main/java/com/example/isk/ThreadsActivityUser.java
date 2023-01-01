package com.example.isk;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.example.isk.adapter.AdapterThreadUser;
import com.example.isk.api.APIClient;
import com.example.isk.api.Thread;
import com.example.isk.api.ThreadInterface;
import com.example.isk.api.ThreadResponse;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;

public class ThreadsActivityUser extends AppCompatActivity {

    RecyclerView rvThreads;
    LinearLayoutManager linearLayoutManager;
    AdapterThreadUser adapterThreadUser;
    ArrayList<Thread> listThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_threads_user);

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
                            adapterThreadUser = new AdapterThreadUser(listThread);
                            linearLayoutManager = new LinearLayoutManager(ThreadsActivityUser.this);
                            rvThreads.setLayoutManager(linearLayoutManager);
                            rvThreads.setAdapter(adapterThreadUser);
                        } else {
                            Toast.makeText(ThreadsActivityUser.this, "Data thread kosong", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(ThreadsActivityUser.this, "Gagal mengambil data thread", Toast.LENGTH_LONG).show();
                    }
                } catch (Exception e) {
                    Toast.makeText(ThreadsActivityUser.this, "TERJADI KESALAHAN", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ThreadResponse> call, Throwable t) {
                Toast.makeText(ThreadsActivityUser.this, "GAGAL", Toast.LENGTH_LONG).show();
            }
        });
    }
}