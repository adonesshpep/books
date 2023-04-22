package com.example.mybooks;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
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
    public static final int EDIT_REQ=2;
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
            adapter.submitList(accounts);
            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                accountViewModel.delete(adapter.getAccountAt(viewHolder.getAdapterPosition()));
                Toast.makeText(MainActivity.this, "Deleted", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerView);
        adapter.setOnItemClickListener(new AccountAdapter.OnItemClickListener() {
            @Override
            public void onClick(Account account) {
                Intent intent = new Intent(MainActivity.this,AddAccount.class);
                intent.putExtra(AddAccount.EXTRA_ACCOUNT,account.getAccount_type());
                intent.putExtra(AddAccount.EXTRA_PASSWORD,account.getPassword());
                intent.putExtra(AddAccount.EXTRA_EMAIL,account.getEmail());
                intent.putExtra(AddAccount.EXTRA_USERNAME,account.getUsername());
                intent.putExtra(AddAccount.EXTRA_ID,account.getId());
                startActivityForResult(intent,EDIT_REQ);

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
        } else  if(requestCode==EDIT_REQ&&resultCode==RESULT_OK){
            int id=data.getIntExtra(AddAccount.EXTRA_ID,-1);
            if(id==-1){
                Toast.makeText(this, "something went wrong", Toast.LENGTH_SHORT).show();
                return;
            }
            String account =data.getStringExtra(AddAccount.EXTRA_ACCOUNT);
            String username =data.getStringExtra(AddAccount.EXTRA_USERNAME);
            String password =data.getStringExtra(AddAccount.EXTRA_PASSWORD);
            String email =data.getStringExtra(AddAccount.EXTRA_EMAIL);
            Account account1=new Account(account,username,email,password);
            account1.setId(id);
            accountViewModel.update(account1);
            Toast.makeText(this, "Account updated", Toast.LENGTH_SHORT).show();

        }
        else{
            Toast.makeText(this,"Account Not Saved",Toast.LENGTH_LONG).show();
        }
    }
}


