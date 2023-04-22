package com.example.mybooks;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class AccountAdapter extends RecyclerView.Adapter<AccountAdapter.AccountHolder> {
    private List<Account> accounts=new ArrayList<>();

    @NonNull
    @Override
    public AccountHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.account_item,parent,false);
        return new AccountHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull AccountHolder holder, int position) {
    Account account = accounts.get(position);
    holder.accountType.setText(account.getAccount_type());
    holder.email.setText(account.getEmail());
    }

    @Override
    public int getItemCount() {
        return accounts.size();
    }
    public void setAccounts (List<Account> accounts){
        this.accounts=accounts;
        notifyDataSetChanged();
    }

    class AccountHolder extends RecyclerView.ViewHolder{
         private TextView accountType;
         private TextView email;
        public AccountHolder(@NonNull View itemView) {
            super(itemView);
            accountType= itemView.findViewById(R.id.account_type);
            email =itemView.findViewById(R.id.email);
        }
    }
}
