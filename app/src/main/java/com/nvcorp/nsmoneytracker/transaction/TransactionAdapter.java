package com.nvcorp.nsmoneytracker.transaction;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.nvcorp.nsmoneytracker.R;
import com.nvcorp.nsmoneytracker.category.Category;
import com.nvcorp.nsmoneytracker.contact.Contact;
import com.nvcorp.nsmoneytracker.databinding.LayoutTransactionCellBinding;
import com.nvcorp.nsmoneytracker.payment.Payment;

import java.util.List;

public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.TransactionViewHolder> {

    LayoutTransactionCellBinding binding;

    Context context;
    List<Transaction> transactionList;

    public TransactionAdapter(Context context, List<Transaction> transactionList) {
        this.context = context;
        this.transactionList = transactionList;
    }

    @NonNull
    @Override
    public TransactionAdapter.TransactionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = LayoutTransactionCellBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new TransactionViewHolder(binding);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull TransactionAdapter.TransactionViewHolder holder, int position) {
        Transaction currentTransaction = transactionList.get(position);

        holder.binding.createDate.setText(currentTransaction.getDate());
        holder.binding.createTime.setText("at " + currentTransaction.getTime());

        if (currentTransaction.getRemarks().equalsIgnoreCase("")) {
            holder.binding.remarks.setVisibility(View.GONE);
        }else{
            holder.binding.remarks.setVisibility(View.VISIBLE);
            holder.binding.remarks.setText(currentTransaction.getRemarks());
        }

        if (currentTransaction.getContactId().equalsIgnoreCase("")) {
            holder.binding.contactName.setVisibility(View.GONE);
            holder.binding.contactType.setVisibility(View.GONE);
        }else{
            for (Contact contact : TransactionFragment.contactList) {
                if (contact.getId().equalsIgnoreCase(currentTransaction.getContactId())) {
                    holder.binding.contactName.setVisibility(View.VISIBLE);
                    holder.binding.contactType.setVisibility(View.VISIBLE);
                    holder.binding.contactName.setText(contact.getName());
                    holder.binding.contactType.setText(" (" + contact.getType() + ")");
                    break;
                }
            }
        }

        if (currentTransaction.getCategoryId().equalsIgnoreCase("")) {
            holder.binding.categoryName.setVisibility(View.GONE);
        }else{
            for (Category category : TransactionFragment.categoryList) {
                if (category.getId().equalsIgnoreCase(currentTransaction.getCategoryId())) {
                    holder.binding.categoryName.setVisibility(View.VISIBLE);
                    holder.binding.categoryName.setText(category.getName());
                    break;
                }
            }
        }

        if (currentTransaction.getPaymentId().equalsIgnoreCase("")) {
            holder.binding.paymentType.setVisibility(View.GONE);
        }else{
            for (Payment payment : TransactionFragment.paymentList) {
                if (payment.getId().equalsIgnoreCase(currentTransaction.getPaymentId())) {
                    holder.binding.paymentType.setVisibility(View.VISIBLE);
                    holder.binding.paymentType.setText(payment.getName());
                    break;
                }
            }
        }

        holder.binding.balanceText.setText("Balance: " + currentTransaction.getBalance());
        holder.binding.amountText.setText(String.valueOf(currentTransaction.getAmount()));
        if (currentTransaction.getPaymentType().equalsIgnoreCase("cash_in")) {
            holder.binding.amountText.setTextColor(ContextCompat.getColor(context, R.color.green));
        }else{
            holder.binding.amountText.setTextColor(ContextCompat.getColor(context, R.color.red));
        }
    }

    @Override
    public int getItemCount() {
        return transactionList.size();
    }

    public class TransactionViewHolder extends RecyclerView.ViewHolder {

        LayoutTransactionCellBinding binding;

        public TransactionViewHolder(LayoutTransactionCellBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
