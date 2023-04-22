package com.example.mybooks;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

public class AddAccount extends AppCompatActivity {
    public static final String EXTRA_ID="apk.nethieee";
    public static final String EXTRA_ACCOUNT="apk.nethyy";
    public static final String EXTRA_USERNAME="apk.nethyyy";
    public static final String EXTRA_PASSWORD="apk.nethyyyyy";
    public static final String EXTRA_EMAIL="apk.nethyyyyyyy";
    private TextView eaccounttype;
    private TextView eusername;
    private TextView epassword;
    private TextView eemail;
    private TextView mainthing;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_account);
        eaccounttype=findViewById(R.id.account_name);
        eusername =findViewById(R.id.username);
        epassword=findViewById(R.id.editPassword);
        eemail=findViewById(R.id.editemail);
        mainthing=findViewById(R.id.textView2);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);
        Intent intent=getIntent();
        if(intent.hasExtra(EXTRA_ID)){
            setTitle("edit account");
            eaccounttype.setText(intent.getStringExtra(EXTRA_ACCOUNT));
            eusername.setText(intent.getStringExtra(EXTRA_USERNAME));
            eemail.setText(intent.getStringExtra(EXTRA_EMAIL));
            epassword.setText(intent.getStringExtra(EXTRA_PASSWORD));
            mainthing.setText(intent.getStringExtra(EXTRA_ACCOUNT)+" ACCOUNT");
        }else{
        setTitle("add Account");}

    }
    private void saveAccount(){
        String account=eaccounttype.getText().toString();
        String password=epassword.getText().toString();
        String email=eemail.getText().toString();
        String username=eusername.getText().toString();
        if(account.trim().isEmpty()||password.trim().isEmpty()||email.trim().isEmpty()||username.trim().isEmpty()){
            Toast.makeText(this,"Pleas put values",Toast.LENGTH_LONG).show();
            return;
        }
            Intent intent = new Intent();
            intent.putExtra(EXTRA_ACCOUNT,account);
            intent.putExtra(EXTRA_USERNAME,username);
            intent.putExtra(EXTRA_EMAIL,email);
            intent.putExtra(EXTRA_PASSWORD,password);
            int id=getIntent().getIntExtra(EXTRA_ID,-1);
            if(id!=-1){
                intent.putExtra(EXTRA_ID,id);
            }
            setResult(RESULT_OK,intent);
            finish();



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.save_account, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.saved:
                saveAccount();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}