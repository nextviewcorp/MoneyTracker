package com.nvcorp.nsmoneytracker.transaction;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Html;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.nvcorp.nsmoneytracker.MainActivity;
import com.nvcorp.nsmoneytracker.OnBackPressed;
import com.nvcorp.nsmoneytracker.category.Category;
import com.nvcorp.nsmoneytracker.category.CategoryViewModel;
import com.nvcorp.nsmoneytracker.contact.Contact;
import com.nvcorp.nsmoneytracker.contact.ContactViewModel;
import com.nvcorp.nsmoneytracker.databinding.FragmentTransactionBinding;
import com.nvcorp.nsmoneytracker.payment.Payment;
import com.nvcorp.nsmoneytracker.payment.PaymentAdapter;
import com.nvcorp.nsmoneytracker.payment.PaymentViewModel;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TransactionFragment extends Fragment implements OnBackPressed {

    FragmentTransactionBinding binding;

    TransactionAdapter adapter;

    TransactionViewModel transactionViewModel;
    ContactViewModel contactViewModel;
    CategoryViewModel categoryViewModel;
    PaymentViewModel paymentViewModel;

    public static OnBackPressed onBackPressed;

    public static List<Contact> contactList = new ArrayList<>();
    public static List<Category> categoryList = new ArrayList<>();
    public static List<Payment> paymentList = new ArrayList<>();

    public static double balance = 0;
    private double cashIn = 0;
    private double cashOut = 0;

    public TransactionFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentTransactionBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        transactionViewModel = new ViewModelProvider(this).get(TransactionViewModel.class);
        contactViewModel = new ViewModelProvider(this).get(ContactViewModel.class);
        categoryViewModel = new ViewModelProvider(this).get(CategoryViewModel.class);
        paymentViewModel = new ViewModelProvider(this).get(PaymentViewModel.class);


        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        binding.transactionRv.setLayoutManager(layoutManager);

        transactionViewModel.getAllTransaction().observe(getViewLifecycleOwner(), new Observer<List<Transaction>>() {
            @Override
            public void onChanged(List<Transaction> transactions) {
                double ci = 0;
                double co = 0;
                for (Transaction transaction : transactions) {
                    if (transaction.getPaymentType().equalsIgnoreCase("cash_in")) {
                        ci += transaction.getAmount();
                    }else{
                        co += transaction.getAmount();
                    }
                }

                cashIn = ci;
                cashOut = co;
                balance = ci - co;
                binding.numberOfEntry.setText("Showing " + transactions.size() + " entries");
                printDetails();

                Collections.reverse(transactions);

                adapter = new TransactionAdapter(getActivity(), transactions);
                binding.transactionRv.setAdapter(adapter);
            }
        });
        contactViewModel.getAllContacts().observe(getViewLifecycleOwner(), new Observer<List<Contact>>() {
            @Override
            public void onChanged(List<Contact> contacts) {
                contactList = contacts;
            }
        });
        categoryViewModel.getAllCategories().observe(getViewLifecycleOwner(), new Observer<List<Category>>() {
            @Override
            public void onChanged(List<Category> categories) {
                categoryList = categories;
            }
        });
        paymentViewModel.getAllPayments().observe(getViewLifecycleOwner(), new Observer<List<Payment>>() {
            @Override
            public void onChanged(List<Payment> payments) {
                paymentList = payments;
            }
        });

        binding.cashIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity)requireActivity()).replaceFragment(new AddNewTransactionFragment("cash_in"));
            }
        });

        binding.cashOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity)requireActivity()).replaceFragment(new AddNewTransactionFragment("cash_out"));
            }
        });
    }

    @Override
    public void onPause() {
        onBackPressed = null;
        super.onPause();
    }

    @Override
    public void onResume() {
        onBackPressed = this;
        super.onResume();
    }

    private void printDetails() {
        binding.cashInText.setText(String.valueOf(cashIn));
        binding.cashOutText.setText(String.valueOf(cashOut));
        binding.netBalanceText.setText(String.valueOf(balance));
    }

    @Override
    public void onBackPressed() {
        ((MainActivity)requireActivity()).onExit();
    }
}