package com.example.mybooks;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class AddAccount extends AppCompatActivity {
    // this class for adding new account
     // and can also be used to see an existing one
    //here is the codes that will be used to send the data to the main activity to store it in the database
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
        //if we start this activity and there is data inside the intent object that mean we start it by clicking an item
        //and also mean we want to see teh data or modified it and not create new account
        if(intent.hasExtra(EXTRA_ID)){
            setTitle("edit account");
            //it is the case of update
            eaccounttype.setText(intent.getStringExtra(EXTRA_ACCOUNT));
            eusername.setText(intent.getStringExtra(EXTRA_USERNAME));
            eemail.setText(intent.getStringExtra(EXTRA_EMAIL));
            epassword.setText(intent.getStringExtra(EXTRA_PASSWORD));
            String mainput=intent.getStringExtra(EXTRA_ACCOUNT).toUpperCase();
            //the main thing is the big text view in the activity i dont have a better name
            mainthing.setText(mainput);
        }else{
        setTitle("add Account");
        //here is the create new account case
        }

    }
    private void saveAccount(){
        //getting the data from the textviews in the activity
        String account=eaccounttype.getText().toString();
        String password=epassword.getText().toString();
        String email=eemail.getText().toString();
        String username=eusername.getText().toString();
        //making sure that it is not empty
        if(account.trim().isEmpty()||password.trim().isEmpty()||email.trim().isEmpty()||username.trim().isEmpty()){
            Toast.makeText(this,"Pleas put values",Toast.LENGTH_LONG).show();
            return;
        }
        //and then send it back to the main activity to save it
            Intent intent = new Intent();
            intent.putExtra(EXTRA_ACCOUNT,account);
            intent.putExtra(EXTRA_USERNAME,username);
            intent.putExtra(EXTRA_EMAIL,email);
            intent.putExtra(EXTRA_PASSWORD,password);
            int id=getIntent().getIntExtra(EXTRA_ID,-1);
            //if there is no id that main it is a new account
        //if there is an id that main we want to update an old one so we also want to send the id to update the account that has the same one
            if(id!=-1){
                intent.putExtra(EXTRA_ID,id);
            }
            setResult(RESULT_OK,intent);
            finish();



    }
// the menu thing to add the option of save the data and go back to the main activity
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        //get the icon from the menu directory
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