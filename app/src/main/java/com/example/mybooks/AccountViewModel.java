package com.example.mybooks;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class AccountViewModel extends AndroidViewModel {
    AccountRepositry repositry;
    LiveData<List<Account>> accountes;
    public AccountViewModel(@NonNull Application application) {
        super(application);
        repositry=new AccountRepositry(application);
        accountes=repositry.getAll();
    }
    public void insert (Account account){
        repositry.insert(account);
    }
    public void update (Account account){
        repositry.update(account);
    }
    public void delete (Account account){
        repositry.delete(account);
    }
    public void deleteAll (){
        repositry.deleteAll();
    }

    public LiveData<List<Account>> getAccountes() {
        return accountes;
    }
}
