package com.example.mybooks;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.chaos.view.PinView;

public class lock_screen extends AppCompatActivity {
    private PinView pinView;
    public static final String SHAREDNAME ="hellonethy";
    public static final String LOCK_KEY ="qhellonethy";
    private SharedPreferences sharedPreferences;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lock_screen);
        sharedPreferences=getSharedPreferences(SHAREDNAME,MODE_PRIVATE);
        Intent intent =new Intent(lock_screen.this,MainActivity.class);
        textView=findViewById(R.id.textView3);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        String lock=sharedPreferences.getString(LOCK_KEY,null);
        if(lock==null||lock.equals("")){
            textView.setText("Chose your pin lock");
        }else{textView.setText("Insert your pin");}

        pinView=findViewById(R.id.pin_view);
        pinView.requestFocus();
        InputMethodManager inputMethodManager=(InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED,InputMethodManager.HIDE_IMPLICIT_ONLY);
        pinView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(charSequence.toString().length()==4){
                if(lock==null||lock.equals("")){
                    editor.putString(LOCK_KEY,charSequence.toString());
                    editor.apply();
                    startActivity(intent);
                    finish();
                }else{
                    if(charSequence.toString().equals(lock)){
                        startActivity(intent);
                        finish();
                    }else{
                        textView.setText("WRONG PIN TRY AGAIN");
                        textView.setTextColor(Color.RED);
                    }
                }

            }else{if(lock==null){textView.setText("Chose your pin lock");}else{textView.setText("Insert your pin");
                    textView.setTextColor(Color.WHITE);} }}

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }
}