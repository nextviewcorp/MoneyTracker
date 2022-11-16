package com.nvcorp.nsmoneytracker.contact;

import android.app.Application;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.nvcorp.nsmoneytracker.database.NSMTDatabase;

import java.util.List;

public class ContactViewModel extends AndroidViewModel {

    private NSMTDatabase database;
    private ContactDao contactDao;
    LiveData<List<Contact>> allContacts;

    public ContactViewModel(@NonNull Application application) {
        super(application);

        database = NSMTDatabase.getInstance(application);
        contactDao = database.contactDao();
        allContacts = contactDao.getAllContacts();
    }

    public void insert(Contact contact) {
        new InsertAsyncTask(contactDao).execute(contact);
    }

    public LiveData<List<Contact>> getAllContacts() {
        return allContacts;
    }

    private class InsertAsyncTask extends AsyncTask<Contact, Void, Void> {

        ContactDao contactDao;

        public InsertAsyncTask(ContactDao contactDao) {
            this.contactDao = contactDao;
        }

        @Override
        protected Void doInBackground(Contact... contacts) {
            contactDao.insert(contacts[0]);
            return null;
        }
    }
}
