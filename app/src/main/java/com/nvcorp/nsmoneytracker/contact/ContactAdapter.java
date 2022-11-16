package com.nvcorp.nsmoneytracker.contact;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nvcorp.nsmoneytracker.databinding.LayoutCategoryCellBinding;
import com.nvcorp.nsmoneytracker.databinding.LayoutContactCellBinding;

import java.util.List;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ContactViewHolder> {

    LayoutContactCellBinding binding;

    Context context;
    List<Contact> contactList;
    OnSelectContact onSelectContact;

    public ContactAdapter(Context context, List<Contact> contactList, OnSelectContact onSelectContact) {
        this.context = context;
        this.contactList = contactList;
        this.onSelectContact = onSelectContact;
    }

    @NonNull
    @Override
    public ContactAdapter.ContactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = LayoutContactCellBinding.inflate(LayoutInflater.from(parent.getContext()),parent, false);
        return new ContactViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactAdapter.ContactViewHolder holder, int position) {
        Contact currentContact = contactList.get(position);
        holder.binding.contact.setText(currentContact.getName() + " " + currentContact.getPhone() + " " + currentContact.getType());

        holder.binding.contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onSelectContact.onSelectContact(currentContact);
            }
        });
    }

    @Override
    public int getItemCount() {
        return contactList.size();
    }

    public class ContactViewHolder extends RecyclerView.ViewHolder {

        LayoutContactCellBinding binding;

        public ContactViewHolder(LayoutContactCellBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
