package com.example.mybooks;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class AccountAdapter extends ListAdapter<Account,AccountAdapter.AccountHolder> {
    OnItemClickListener listener;

    public AccountAdapter() {
        super(DIFF_CALLBACK);
    }
    private static final DiffUtil.ItemCallback<Account> DIFF_CALLBACK=new DiffUtil.ItemCallback<Account>() {
        @Override
        public boolean areItemsTheSame(@NonNull Account oldItem, @NonNull Account newItem) {
            return oldItem.getId()== newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Account oldItem, @NonNull Account newItem) {
            return oldItem.getAccount_type().equals(newItem.getAccount_type())&&oldItem.getEmail().equals(newItem.getEmail())&&
                    oldItem.getUsername().equals(newItem.getUsername())&&oldItem.getPassword().equals(newItem.getPassword());
        }
    };

    @NonNull
    @Override
    public AccountHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.account_item, parent, false);
        return new AccountHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull AccountHolder holder, int position) {
        Account account = getItem(position);
        holder.accountType.setText(account.getAccount_type());
        holder.email.setText(account.getEmail());
    }



    public Account getAccountAt(int position) {
        return getItem(position);
    }

    class AccountHolder extends RecyclerView.ViewHolder {
        private TextView accountType;
        private TextView email;

        public AccountHolder(@NonNull View itemView) {
            super(itemView);
            accountType = itemView.findViewById(R.id.account_type);
            email = itemView.findViewById(R.id.email);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onClick(getItem(position));
                    }
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onClick(Account account);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}


