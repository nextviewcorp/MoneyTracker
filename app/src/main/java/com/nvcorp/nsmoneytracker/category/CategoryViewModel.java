package com.nvcorp.nsmoneytracker.category;

import android.app.Application;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.nvcorp.nsmoneytracker.database.NSMTDatabase;

import java.util.List;

public class CategoryViewModel extends AndroidViewModel {

    private final String TAG = this.getClass().getSimpleName();
    private CategoryDao categoryDao;
    private NSMTDatabase database;
    private LiveData<List<Category>> mAllCategories;

    public CategoryViewModel(@NonNull Application application) {
        super(application);
        database = NSMTDatabase.getInstance(application);
        categoryDao = database.categoryDao();
        mAllCategories = categoryDao.getAllCategories();
    }

    public void insert(Category category) {
        new InsertAsyncTask(categoryDao).execute(category);
    }

    LiveData<List<Category>> getAllCategories() {
        return mAllCategories;
    }

    private class InsertAsyncTask extends AsyncTask<Category, Void, Void> {

        CategoryDao mCategoryDao;

        public InsertAsyncTask(CategoryDao mCategoryDao) {
            this.mCategoryDao = mCategoryDao;
        }

        @Override
        protected Void doInBackground(Category... categories) {
            mCategoryDao.insert(categories[0]);
            return null;
        }
    }
}
