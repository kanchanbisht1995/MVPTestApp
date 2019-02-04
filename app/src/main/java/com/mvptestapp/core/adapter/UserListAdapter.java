package com.mvptestapp.core.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.luckey.mvptestapplication.R;
import com.mvptestapp.core.model.UserListData;

import java.util.ArrayList;

public class UserListAdapter extends RecyclerView.Adapter<UserListAdapter.MainViewHolder> {


    ArrayList<UserListData> items;

    public UserListAdapter(ArrayList<UserListData> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public MainViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.user_list_item, parent, false);
        return new MainViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MainViewHolder holder, int position) {
        holder.username.setText((items.get(position).getFirstName().concat(" " + items.get(position).getLastName())));

        //checking for this as in API loan amount is not coming
        if(items.get(position).getLoanAmount()!=null)
        {
            holder.loanamout.setText(String.valueOf(items.get(position).getLoanAmount()));
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class MainViewHolder extends RecyclerView.ViewHolder {

        TextView username, loanamout;

        MainViewHolder(View itemView) {
            super(itemView);
            username = itemView.findViewById(R.id.username);
            loanamout = itemView.findViewById(R.id.loanamout);
        }
    }
}
