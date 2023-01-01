package com.example.isk.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.isk.R;
import com.example.isk.ThreadActivityUser;
import com.example.isk.api.Thread;

import java.util.ArrayList;

public class AdapterThreadUser extends RecyclerView.Adapter<AdapterThreadUser.HolderThread> {

    ArrayList<Thread> listThread;

    public AdapterThreadUser(ArrayList<Thread> listThread) {
        this.listThread = listThread;
    }

    @NonNull
    @Override
    public HolderThread onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.row_thread, parent, false);
        return new HolderThread(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HolderThread holder, int position) {
        holder.tvJudul.setText(listThread.get(position).getJudul());
        holder.tvTanggal.setText("Terakhir diperbarui pada " + listThread.get(position).getTanggal());

        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), ThreadActivityUser.class);

                intent.putExtra("id", listThread.get(holder.getBindingAdapterPosition()).getId());
                intent.putExtra("judul", listThread.get(holder.getBindingAdapterPosition()).getJudul());
                intent.putExtra("gambar", listThread.get(holder.getBindingAdapterPosition()).getGambar());
                intent.putExtra("isi", listThread.get(holder.getBindingAdapterPosition()).getIsi());
                intent.putExtra("tanggal", listThread.get(holder.getBindingAdapterPosition()).getTanggal());

                view.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return (listThread != null) ? listThread.size() : 0;
    }

    public class HolderThread extends RecyclerView.ViewHolder {
        TextView tvJudul, tvTanggal;
        LinearLayout linearLayout;

        public HolderThread(@NonNull View itemView) {
            super(itemView);

            tvJudul = itemView.findViewById(R.id.tvJudul);
            tvTanggal = itemView.findViewById(R.id.tvTanggal);
            linearLayout = itemView.findViewById(R.id.linearLayout);
        }
    }

}
