package com.nvcorp.nsmoneytracker.contact;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nvcorp.nsmoneytracker.databinding.LayoutCategoryCellBinding;

import java.util.List;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ContactViewHolder> {

    LayoutCategoryCellBinding binding;

    Context context;
    List<Contact> contactList;

    public ContactAdapter(Context context, List<Contact> contactList) {
        this.context = context;
        this.contactList = contactList;
    }

    @NonNull
    @Override
    public ContactAdapter.ContactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = LayoutCategoryCellBinding.inflate(LayoutInflater.from(parent.getContext()),parent, false);
        return new ContactViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactAdapter.ContactViewHolder holder, int position) {
        Contact currentContact = contactList.get(position);
        holder.binding.category.setText(currentContact.getName() + " " + currentContact.getPhone() + " " + currentContact.getType());
    }

    @Override
    public int getItemCount() {
        return contactList.size();
    }

    public class ContactViewHolder extends RecyclerView.ViewHolder {

        LayoutCategoryCellBinding binding;

        public ContactViewHolder(LayoutCategoryCellBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
