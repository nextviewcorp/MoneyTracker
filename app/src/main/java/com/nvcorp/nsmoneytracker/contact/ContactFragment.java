package com.nvcorp.nsmoneytracker.contact;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.chip.Chip;
import com.google.android.material.textfield.TextInputLayout;
import com.nvcorp.nsmoneytracker.R;
import com.nvcorp.nsmoneytracker.category.CategoryAdapter;
import com.nvcorp.nsmoneytracker.databinding.FragmentContactBinding;
import com.nvcorp.nsmoneytracker.transaction.AddNewTransactionFragment;

import java.util.List;
import java.util.UUID;

public class ContactFragment extends Fragment implements OnSelectContact {

    FragmentContactBinding binding;

    ContactViewModel contactViewModel;

    ContactAdapter contactAdapter;

    public ContactFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentContactBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        contactViewModel = new ViewModelProvider(this).get(ContactViewModel.class);

        binding.addContact.setOnClickListener(v -> {
            final Dialog dialog = new Dialog(getContext());
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.layout_add_contact);

            dialog.findViewById(R.id.add_contact_close).setOnClickListener(v1 -> dialog.dismiss());

            TextInputLayout contactNameTIL = dialog.findViewById(R.id.contact_name_text_input_layout);
            EditText contactNameET = dialog.findViewById(R.id.contact_name_text_input_edit_text);

            EditText mobileNumberET = dialog.findViewById(R.id.contact_phone_text_input_edit_text);

            Button saveContactBtn = dialog.findViewById(R.id.save_contact);

            saveContactBtn.setOnClickListener(v12 -> {
                String contactName = contactNameET.getText().toString();
                String contactPhone = mobileNumberET.getText().toString();

                if (contactName.equals("")) {
                    contactNameTIL.setErrorEnabled(true);
                    contactNameTIL.setError("Please enter contact name.");
                } else {
                    contactNameTIL.setErrorEnabled(false);
                    String id = UUID.randomUUID().toString();
                    String type = "";
                    Chip chip1 = dialog.findViewById(R.id.chip_cash_in);
                    Chip chip2 = dialog.findViewById(R.id.chip_cash_out);
                    if (chip1.isChecked()) {
                        type = chip1.getText().toString();
                    }
                    if (chip2.isChecked()) {
                        type = chip2.getText().toString();
                    }
                    Contact contact = new Contact(id, contactName, contactPhone, type);
                    contactViewModel.insert(contact);
                    Toast.makeText(getContext(), "New contact added.", Toast.LENGTH_SHORT).show();
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
        binding.contactsRv.setLayoutManager(layoutManager);

        contactViewModel.getAllContacts().observe(getViewLifecycleOwner(), new Observer<List<Contact>>() {
            @Override
            public void onChanged(List<Contact> contacts) {
                contactAdapter = new ContactAdapter(getActivity(), contacts, ContactFragment.this::onSelectContact);
                binding.contactsRv.setAdapter(contactAdapter);
            }
        });
    }

    @Override
    public void onSelectContact(Contact contact) {
        Bundle result = new Bundle();
        result.putString("contactId", contact.getId());
        result.putString("contactName", contact.getName());
        getParentFragmentManager().setFragmentResult(AddNewTransactionFragment.CONTACT_REQUEST_CODE, result);
        getParentFragmentManager().popBackStack();
    }
}