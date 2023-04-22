package com.example.mybooks;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class AccountRepositry {
    private AccountDao accountDao;
    private LiveData<List<Account>> accountes;
    public AccountRepositry(Application application) {
        MyDatabase instance= MyDatabase.getInstance(application);
        accountDao= instance.accountDao();
        accountes=accountDao.getAll();
    }
    public void insert (Account account){
        new insertAsyncTask(accountDao).execute(account);

    }
    public void update(Account account){
        new updateAsyncTask(accountDao).execute(account);

    }
    public void delete(Account account){
        new deleteAsyncTask(accountDao).execute(account);

    }
    public void deleteAll(){
        new deleteAllAsyncTask(accountDao).execute();

    }
   public LiveData<List<Account>> getAll(){
        return accountes;
   }
    private static class insertAsyncTask extends AsyncTask<Account,Void,Void>{
        private AccountDao accountDao;
        private insertAsyncTask(AccountDao accountDao){
            this.accountDao=accountDao;
        }

        @Override
        protected Void doInBackground(Account... accounts) {
            accountDao.insert(accounts[0]);
            return null;
        }
    }
    private static class updateAsyncTask extends AsyncTask<Account,Void,Void>{
        private AccountDao accountDao;
        private updateAsyncTask(AccountDao accountDao){
            this.accountDao=accountDao;
        }

        @Override
        protected Void doInBackground(Account... accounts) {
            accountDao.update(accounts[0]);
            return null;
        }
    }
    private static class deleteAsyncTask extends AsyncTask<Account,Void,Void>{
        private AccountDao accountDao;
        private deleteAsyncTask(AccountDao accountDao){
            this.accountDao=accountDao;
        }

        @Override
        protected Void doInBackground(Account... accounts) {
            accountDao.delete(accounts[0]);
            return null;
        }
    }
    private static class deleteAllAsyncTask extends AsyncTask<Void,Void,Void>{
        private AccountDao accountDao;
        private deleteAllAsyncTask(AccountDao accountDao){
            this.accountDao=accountDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            accountDao.deleteAll();
            return null;
        }
    }

}
