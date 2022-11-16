package com.nvcorp.nsmoneytracker.payment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nvcorp.nsmoneytracker.databinding.LayoutPaymentCellBinding;

import java.util.List;

public class PaymentAdapter extends RecyclerView.Adapter<PaymentAdapter.PaymentViewHolder> {

    LayoutPaymentCellBinding binding;

    Context context;
    List<Payment> paymentList;
    OnSelectPayment onSelectPayment;

    public PaymentAdapter(Context context, List<Payment> paymentList, OnSelectPayment onSelectPayment) {
        this.context = context;
        this.paymentList = paymentList;
        this.onSelectPayment = onSelectPayment;
    }

    @NonNull
    @Override
    public PaymentAdapter.PaymentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = LayoutPaymentCellBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new PaymentViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull PaymentAdapter.PaymentViewHolder holder, int position) {
        Payment currentPayment = paymentList.get(position);

        holder.binding.paymentMethod.setText(currentPayment.getName());
        holder.binding.paymentMethod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onSelectPayment.onSelectPayment(currentPayment);
            }
        });
    }

    @Override
    public int getItemCount() {
        return paymentList.size();
    }

    public class PaymentViewHolder extends RecyclerView.ViewHolder {

        LayoutPaymentCellBinding binding;

        public PaymentViewHolder(LayoutPaymentCellBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
