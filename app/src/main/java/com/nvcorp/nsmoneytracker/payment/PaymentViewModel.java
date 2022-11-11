package com.nvcorp.nsmoneytracker.payment;

import android.app.Application;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.nvcorp.nsmoneytracker.contact.Contact;
import com.nvcorp.nsmoneytracker.contact.ContactDao;
import com.nvcorp.nsmoneytracker.contact.ContactViewModel;
import com.nvcorp.nsmoneytracker.database.NSMTDatabase;

import java.util.List;

public class PaymentViewModel extends AndroidViewModel {

    private NSMTDatabase database;
    private PaymentDao paymentDao;
    LiveData<List<Payment>> allPayments;

    public PaymentViewModel(@NonNull Application application) {
        super(application);
        database = NSMTDatabase.getInstance(application);
        paymentDao = database.paymentDao();;
        allPayments = paymentDao.getAllPayments();
    }

    public void insert(Payment payment) {
        new InsertAsyncTask(paymentDao).execute(payment);
    }

    LiveData<List<Payment>> getAllPayments() {
        return allPayments;
    }

    private class InsertAsyncTask extends AsyncTask<Payment, Void, Void> {

        PaymentDao paymentDao;

        public InsertAsyncTask(PaymentDao paymentDao) {
            this.paymentDao = paymentDao;
        }

        @Override
        protected Void doInBackground(Payment... payments) {
            paymentDao.insert(payments[0]);
            return null;
        }
    }

}
