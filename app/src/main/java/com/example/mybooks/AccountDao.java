package com.example.mybooks;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;
//the database access object

@Dao
public interface AccountDao {
    @Insert
    void insert(Account account);
     @Update
    void update(Account account);
     @Delete
    void delete(Account account);
     @Query("DELETE FROM account_table")
    void deleteAll();
     @Query("SELECT * FROM account_table ORDER BY id DESC")
     LiveData<List<Account>> getAll();
}
