package com.example.mybooks;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static final int AAD_REQ=1;
    private AccountViewModel accountViewModel;
   private  RecyclerView recyclerView;
   private AccountAdapter adapter;
   private FloatingActionButton button;
    @Override
   protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button=findViewById(R.id.floating_add);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(MainActivity.this,AddAccount.class);
                startActivityForResult(intent,AAD_REQ);
            }
        });
        recyclerView=findViewById(R.id.recycler_account);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        adapter =new AccountAdapter();
        recyclerView.setAdapter(adapter);
        accountViewModel= new ViewModelProvider(this).get(AccountViewModel.class);
        accountViewModel.getAccountes().observe(this, new Observer<List<Account>>() {
            @Override
            public void onChanged(List<Account> accounts) {
            adapter.setAccounts(accounts);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==AAD_REQ&&resultCode==RESULT_OK){
            String account =data.getStringExtra(AddAccount.EXTRA_ACCOUNT);
            String username =data.getStringExtra(AddAccount.EXTRA_USERNAME);
            String password =data.getStringExtra(AddAccount.EXTRA_PASSWORD);
            String email =data.getStringExtra(AddAccount.EXTRA_EMAIL);
            Account account1=new Account(account,username,email,password);
            accountViewModel.insert(account1);
            Toast.makeText(this,"Account Saved",Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(this,"Account Not Saved",Toast.LENGTH_LONG).show();
        }
    }
}


