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
import com.example.isk.UserActivity;
import com.example.isk.api.User;

import java.util.ArrayList;

public class AdapterUser extends RecyclerView.Adapter<AdapterUser.HolderUser> {

    ArrayList<User> listUser;

    public AdapterUser(ArrayList<User> listUser) {
        this.listUser = listUser;
    }

    @NonNull
    @Override
    public HolderUser onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.row_user, parent, false);
        return new HolderUser(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HolderUser holder, int position) {
        holder.tvNama.setText(listUser.get(position).getNama());

        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), UserActivity.class);

                intent.putExtra("id", listUser.get(holder.getBindingAdapterPosition()).getId());
                intent.putExtra("nama", listUser.get(holder.getBindingAdapterPosition()).getNama());
                intent.putExtra("username", listUser.get(holder.getBindingAdapterPosition()).getUsername());
                intent.putExtra("email", listUser.get(holder.getBindingAdapterPosition()).getEmail());

                view.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return (listUser != null) ? listUser.size() : 0;
    }

    public class HolderUser extends RecyclerView.ViewHolder {
        TextView tvNama;
        LinearLayout linearLayout;
        public HolderUser(@NonNull View itemView) {
            super(itemView);

            tvNama = itemView.findViewById(R.id.tvNama);
            linearLayout = itemView.findViewById(R.id.linearLayout);
        }
    }
}
