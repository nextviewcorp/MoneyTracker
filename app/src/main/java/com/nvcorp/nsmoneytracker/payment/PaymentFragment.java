package com.nvcorp.nsmoneytracker.payment;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.nvcorp.nsmoneytracker.R;
import com.nvcorp.nsmoneytracker.category.Category;
import com.nvcorp.nsmoneytracker.category.CategoryAdapter;
import com.nvcorp.nsmoneytracker.category.CategoryViewModel;
import com.nvcorp.nsmoneytracker.databinding.FragmentPaymentBinding;

import java.util.UUID;

public class PaymentFragment extends Fragment {

    FragmentPaymentBinding binding;

    PaymentAdapter paymentAdapter;

    PaymentViewModel paymentViewModel;

    public PaymentFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentPaymentBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        paymentViewModel = new ViewModelProvider(this).get(PaymentViewModel.class);

        binding.addPaymentMethod.setOnClickListener(v -> {
            Dialog dialog = new Dialog(getContext());
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.layout_add_payment_method);

            dialog.findViewById(R.id.add_payment_method_close).setOnClickListener(v1 -> dialog.dismiss());

            TextInputLayout paymentMethodNameTIL = dialog.findViewById(R.id.payment_method_name_text_input_layout);
            EditText paymentMethodNameET = dialog.findViewById(R.id.payment_method_name_text_input_edit_text);
            Button savePaymentMethodBtn = dialog.findViewById(R.id.save_payment_method);

            savePaymentMethodBtn.setOnClickListener(v12 -> {
                String paymentMethodName = paymentMethodNameET.getText().toString();

                if (paymentMethodName.equals("")) {
                    paymentMethodNameTIL.setErrorEnabled(true);
                    paymentMethodNameTIL.setError("Please enter Category name.");
                } else {
                    paymentMethodNameTIL.setErrorEnabled(false);
                    String id = UUID.randomUUID().toString();
                    Payment payment = new Payment(id, paymentMethodName);
                    paymentViewModel.insert(payment);
                    Toast.makeText(getContext(), "New payment method added.", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }
            });


            dialog.show();
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
            dialog.getWindow().setGravity(Gravity.BOTTOM);
        });

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        binding.paymentMethodsRv.setLayoutManager(layoutManager);

        paymentViewModel.getAllPayments().observe(getViewLifecycleOwner(), payments -> {
            paymentAdapter = new PaymentAdapter(getActivity(), payments);
            binding.paymentMethodsRv.setAdapter(paymentAdapter);
        });

    }
}