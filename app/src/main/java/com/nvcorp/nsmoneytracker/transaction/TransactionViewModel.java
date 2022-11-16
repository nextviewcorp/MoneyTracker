package com.nvcorp.nsmoneytracker.transaction;

import android.app.Application;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.nvcorp.nsmoneytracker.database.NSMTDatabase;

import java.util.List;

public class TransactionViewModel extends AndroidViewModel {

    private NSMTDatabase database;
    private TransactionDao transactionDao;
    LiveData<List<Transaction>> allTransactions;

    public TransactionViewModel(@NonNull Application application) {
        super(application);
        database = NSMTDatabase.getInstance(application);
        transactionDao = database.transactionDao();;
        allTransactions = transactionDao.getAllTransaction();
    }

    public void insert(Transaction transaction) {
        new TransactionViewModel.InsertAsyncTask(transactionDao).execute(transaction);
    }

    LiveData<List<Transaction>> getAllTransaction() {
        return allTransactions;
    }

    private class InsertAsyncTask extends AsyncTask<Transaction, Void, Void> {

        TransactionDao transactionDao;

        public InsertAsyncTask(TransactionDao transactionDao) {
            this.transactionDao = transactionDao;
        }

        @Override
        protected Void doInBackground(Transaction... transactions) {
            transactionDao.insert(transactions[0]);
            return null;
        }
    }
}
