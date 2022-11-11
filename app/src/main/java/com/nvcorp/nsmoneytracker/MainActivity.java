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

public class MainActivity extends AppCompatActivity {

    private final String TAG = this.getClass().getSimpleName();

    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());



        replaceFragment(new ContactFragment());
    }

    // Replace Fragments
    public void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_manager, fragment);
        fragmentTransaction.addToBackStack("reverse");
        fragmentTransaction.commit();
    }

}