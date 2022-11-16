package com.nvcorp.nsmoneytracker.transaction;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.timepicker.MaterialTimePicker;
import com.google.android.material.timepicker.TimeFormat;
import com.nvcorp.nsmoneytracker.MainActivity;
import com.nvcorp.nsmoneytracker.R;
import com.nvcorp.nsmoneytracker.category.Category;
import com.nvcorp.nsmoneytracker.category.CategoryFragment;
import com.nvcorp.nsmoneytracker.category.CategoryViewModel;
import com.nvcorp.nsmoneytracker.contact.ContactFragment;
import com.nvcorp.nsmoneytracker.databinding.FragmentAddNewTransactionBinding;
import com.nvcorp.nsmoneytracker.payment.Payment;
import com.nvcorp.nsmoneytracker.payment.PaymentFragment;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

public class AddNewTransactionFragment extends Fragment {

    public static String CATEGORY_REQUEST_CODE = "C1";
    public static String CONTACT_REQUEST_CODE = "C2";
    public static String PAYMENT_REQUEST_CODE = "C3";

    FragmentAddNewTransactionBinding binding;

    TransactionViewModel transactionViewModel;

    private String categoryId = "";
    private String categoryName = "";
    private String contactId = "";
    private String contactName = "";
    private String paymentId = "";
    private String paymentName = "";
    private String paymentType = "";
    private String date ="";
    private String time = "";
    private double balance = 0;
    private double amount = 0;

    public AddNewTransactionFragment(String paymentType) {
        this.paymentType = paymentType;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAddNewTransactionBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        balance = TransactionFragment.balance;
        date = getDate();
        time = getTime();
        binding.datePicker.setText(date);
        binding.timePicker.setText(time);

        binding.addTransactionTopAppBar.setTitle(paymentType.equalsIgnoreCase("cash_in") ? "Add Cash In Entry" : "Add Cash Out Entry");

        binding.addTransactionTopAppBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getParentFragmentManager().popBackStack();
            }
        });

        transactionViewModel = new ViewModelProvider(this).get(TransactionViewModel.class);

        MaterialDatePicker datePicker = MaterialDatePicker.Builder.datePicker()
                .setTitleText("Select date").setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                .build();

        MaterialTimePicker timePicker = new MaterialTimePicker.Builder()
                .setTimeFormat(TimeFormat.CLOCK_12H)
                .setHour(12)
                .setMinute(10)
                .setTitleText("Select Time")
                .build();

        binding.datePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datePicker.show(getParentFragmentManager(), "Material_date_Picker");
                datePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener() {
                    @Override
                    public void onPositiveButtonClick(Object selection) {
                        try {
                            Date d = new SimpleDateFormat("MMM dd,yyyy").parse(datePicker.getHeaderText());
                            String dateString = new SimpleDateFormat("dd/MM/yyyy").format(d);
                            date = dateString;
                            binding.datePicker.setText(dateString);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                    }
                });
            }
        });

        binding.timePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timePicker.show(getParentFragmentManager(), "Material_time_Picker");
                timePicker.addOnPositiveButtonClickListener(dialog -> {
                    int newHour = timePicker.getHour();
                    int newMinute = timePicker.getMinute();
                    String AMPM = newHour >= 12 ? "PM" : "AM";
                    int hour = newHour == 0 ? 12 : newHour > 12 ? newHour - 12 : newHour;
                    time = hour + ":" + newMinute + " " + AMPM;
                    binding.timePicker.setText(time);
                });
            }
        });

        getParentFragmentManager().setFragmentResultListener(CATEGORY_REQUEST_CODE, this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                categoryId = result.getString("categoryId");
                categoryName = result.getString("categoryName");
                binding.transactionCategoryText.setText(result.getString("categoryName"));
            }
        });
        binding.transactionCategoryText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity)requireActivity()).replaceFragment(new CategoryFragment());
            }
        });

        getParentFragmentManager().setFragmentResultListener(CONTACT_REQUEST_CODE, this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                contactId = result.getString("contactId");
                contactName = result.getString("contactName");
                binding.transactionContactText.setText(result.getString("contactName"));
            }
        });
        binding.transactionContactText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity)requireActivity()).replaceFragment(new ContactFragment());
            }
        });

        getParentFragmentManager().setFragmentResultListener(PAYMENT_REQUEST_CODE, this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                paymentId = result.getString("paymentId");
                paymentName = result.getString("paymentName");
                binding.transactionPaymentMethodText.setText(result.getString("paymentName"));
            }
        });
        binding.transactionPaymentMethodText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity)requireActivity()).replaceFragment(new PaymentFragment());
            }
        });

        binding.saveTransaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (binding.transactionAmountText.getText().toString().isEmpty()) {
                    binding.transactionAmountLayout.setErrorEnabled(true);
                    binding.transactionAmountLayout.setError("Please enter the amount");
                }else{
                    binding.transactionAmountLayout.setErrorEnabled(false);
                    amount = Double.parseDouble(binding.transactionAmountText.getText().toString());
                    String remarks = binding.transactionRemarksText.getText().toString();

                    if (paymentType.equalsIgnoreCase("cash_in")) {
                        balance += amount;
                    }else{
                        balance -= amount;
                    }

                    String id = UUID.randomUUID().toString();
                    Transaction transaction = new Transaction(id, contactId, categoryId, paymentId, paymentType, remarks, amount, balance, date, time);
                    transactionViewModel.insert(transaction);

                    Toast.makeText(getContext(), "New entry added successfully", Toast.LENGTH_SHORT).show();
                    getParentFragmentManager().popBackStack();

                }


            }
        });

    }

    private String getTime() {
        return new SimpleDateFormat("hh:mm a").format(Calendar.getInstance().getTime());
    }


    private String getDate() {
        return new SimpleDateFormat("dd/MM/yyyy").format(Calendar.getInstance().getTime());
    }
}