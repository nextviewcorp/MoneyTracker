package com.nvcorp.nsmoneytracker.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.nvcorp.nsmoneytracker.category.Category;
import com.nvcorp.nsmoneytracker.category.CategoryDao;
import com.nvcorp.nsmoneytracker.contact.Contact;
import com.nvcorp.nsmoneytracker.contact.ContactDao;
import com.nvcorp.nsmoneytracker.payment.Payment;
import com.nvcorp.nsmoneytracker.payment.PaymentDao;
import com.nvcorp.nsmoneytracker.transaction.Transaction;
import com.nvcorp.nsmoneytracker.transaction.TransactionDao;

@Database(entities = {Category.class, Contact.class, Payment.class, Transaction.class}, version = 1)
public abstract class NSMTDatabase extends RoomDatabase {

    public abstract CategoryDao categoryDao();
    public abstract ContactDao contactDao();
    public abstract PaymentDao paymentDao();
    public abstract TransactionDao transactionDao();

    public static volatile NSMTDatabase nSMTDatabaseInstance;

    public static NSMTDatabase getInstance(final Context context) {
        if (nSMTDatabaseInstance == null) {
            synchronized (NSMTDatabase.class) {
                if (nSMTDatabaseInstance == null) {
                    nSMTDatabaseInstance = Room.databaseBuilder(context.getApplicationContext(), NSMTDatabase.class, "NSMT_database").build();
                }
            }
        }
        return nSMTDatabaseInstance;
    }
}
