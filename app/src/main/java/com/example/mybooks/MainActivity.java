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
import android.content.SharedPreferences;
import android.os.Bundle;
import android.service.notification.StatusBarNotification;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
        //the floating button that will take you to the addaccout class
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(MainActivity.this,AddAccount.class);
                //this method is old
                startActivityForResult(intent,AAD_REQ);
            }
        });
        recyclerView=findViewById(R.id.recycler_account);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        adapter =new AccountAdapter();
        recyclerView.setAdapter(adapter);
        // the viewmodel provider
        accountViewModel= new ViewModelProvider(this).get(AccountViewModel.class);
        accountViewModel.getAccountes().observe(this, new Observer<List<Account>>() {
            //this method in case a change happened inside the database so the viewmodel will observe the change and do the things inside this method
            @Override
            public void onChanged(List<Account> accounts) {
            adapter.submitList(accounts);
            }
        });
        /**
         * the animated way to delete the account taking it to the left or to the right
         * there is no need to use the onMove method so everything is in the onSwiped method
         * it will delete the account using the methods inside the viewmodel and passing to it the adapter to
         * change the view in the recycler view
         * and then make a toast massage
         */
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
/**     implement the the onClick method inside the interface  itemonclicklistener in the adapter class
 * and creating the instance listener in the adapter class we will put it there using the method setOnItemClickListener
 *in the Onclick method we will create an intent object that will send data to the add account class and will get us there
 * so we can update the account or just see the data
 */

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
    //creating the menu in the main class
    //this method will connect the menu with the file of it in the menu directory
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.edit_lock, menu);
        return true;

    }

    //in case you select an item of this menu in our case there is only one option but anyway we use switch
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            /**the only option is changing the password and it will take us to the
             *  lock_screen class to change it and there is a problem here i think
             * i used shared preferences to store the password but there is maybe a data leak
             * and the old password is not delete it
             */
            case R.id.edit_lock:
                SharedPreferences sharedPreferences=getSharedPreferences(lock_screen.SHAREDNAME,MODE_PRIVATE);
                SharedPreferences.Editor editor=sharedPreferences.edit();
                editor.putString(lock_screen.LOCK_KEY,null);
                editor.apply();
                Intent intent =new Intent(MainActivity.this,lock_screen.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * the data that will come back from the AddAccount class
     * there is to options either we go there using the floating button so we want to create new account
     * and here when the data will come back we will store it in the database and the request code is ADD_REQ
     * or we went there by clicking an already existing account to see the data or modified it
     * so in this case we will just update the data in the database  and the request code is EDIT_REQ
     * anyway we will fetch the data using the intent object "data" by codes that have been created in the
     * AddAccount class
     */
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


