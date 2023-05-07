package com.example.mybooks;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

//here is the main data component inside the table in the room data base
@Entity(tableName = "account_table")
public class Account {
    //the id is auto generated for each account and it is used also in dao to select a specific account to delete or update
    @PrimaryKey(autoGenerate = true)
    private int id;

    private String account_type;

    private String username;

    private String email;

    private String password;

    public Account( String account_type, String username, String email, String password) {
        this.account_type = account_type;
        this.username = username;
        this.email = email;
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
        return email;
    }

    public String getPassword() {
        return password;
    }
    public String getAccount_type(){
        return account_type;
    }
}
