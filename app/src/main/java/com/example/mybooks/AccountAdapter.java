package com.example.mybooks;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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
        holder.setAccountImage();
    }



    public Account getAccountAt(int position) {
        return getItem(position);
    }

    class AccountHolder extends RecyclerView.ViewHolder {
        private TextView accountType;
        private TextView email;
       private  ImageView imageView;

        public AccountHolder(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.imageView);
            accountType = itemView.findViewById(R.id.account_type);
            email = itemView.findViewById(R.id.email);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        setAccountImage();
                        listener.onClick(getItem(position));
                    }
                }
            });
        }
        public  void setAccountImage ( ){
            int position =getAdapterPosition();
            if(position!=RecyclerView.NO_POSITION){
            Account account=getItem(position);
            String accountname=account.getAccount_type().trim().toLowerCase();
            switch(accountname){
                case "facebook": imageView.setImageResource(R.drawable.face_logo); break;
                case "instagram" :imageView.setImageResource(R.drawable.instgram_logo); break;
                case "telegram" : imageView.setImageResource(R.drawable.telegram_logo); break;
                case "whatsapp" : imageView.setImageResource(R.drawable.whatsapp_logo); break;
                case "twitter" : imageView.setImageResource(R.drawable.twitter_logo); break;
                case "snapchat" : imageView.setImageResource(R.drawable.snapchat_logo); break;
                case "gmail" : imageView.setImageResource(R.drawable.gmail_logo); break;
                case "google": imageView.setImageResource(R.drawable.google_logo); break;
                case "paypal":imageView.setImageResource(R.drawable.paypal_logo); break;
                case "amazon" : imageView.setImageResource(R.drawable.amazon_logo); break;
                case "tiktok" : imageView.setImageResource(R.drawable.tiktok_logo); break;
                default:imageView.setImageResource(R.drawable.lock_logo); break;

            }
    }}
    }

    public interface OnItemClickListener {
        void onClick(Account account);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
 }


