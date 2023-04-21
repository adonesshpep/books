package com.example.mybooks;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "account_table")
public class Account {
    @PrimaryKey(autoGenerate = true)
    private int id;

    private String account_type;

    private String username;

    private String Email;

    private String password;

    public Account(String account_type,String username, String email, String password) {
        this.account_type=account_type;
        this.username = username;
        Email = email;
        this.password = password;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return Email;
    }

    public String getPassword() {
        return password;
    }
    public String getAccount_type(){
        return account_type;
    }
}
