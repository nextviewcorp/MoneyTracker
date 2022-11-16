package com.nvcorp.nsmoneytracker;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.nvcorp.nsmoneytracker.category.CategoryFragment;
import com.nvcorp.nsmoneytracker.category.CategoryViewModel;
import com.nvcorp.nsmoneytracker.contact.ContactFragment;
import com.nvcorp.nsmoneytracker.databinding.ActivityMainBinding;
import com.nvcorp.nsmoneytracker.payment.PaymentFragment;
import com.nvcorp.nsmoneytracker.transaction.AddNewTransactionFragment;
import com.nvcorp.nsmoneytracker.transaction.TransactionFragment;

public class MainActivity extends AppCompatActivity {

    private final String TAG = this.getClass().getSimpleName();

    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());



        replaceFragment(new TransactionFragment());
    }


    @Override
    public void onBackPressed() {
        if (TransactionFragment.onBackPressed != null ) {
            TransactionFragment.onBackPressed.onBackPressed();
        } else {
            super.onBackPressed();
        }
    }

    // Replace Fragments
    public void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_manager, fragment);
        fragmentTransaction.addToBackStack("reverse");
        fragmentTransaction.commit();
    }

    public void onExit() {
        MainActivity.this.finish();
        System.exit(0);
    }

}