package com.nvcorp.nsmoneytracker.category;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
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
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputLayout;
import com.nvcorp.nsmoneytracker.R;
import com.nvcorp.nsmoneytracker.databinding.FragmentCategoryBinding;
import com.nvcorp.nsmoneytracker.transaction.AddNewTransactionFragment;

import java.util.UUID;

public class CategoryFragment extends Fragment implements OnSelectCategory{

    FragmentCategoryBinding binding;

    CategoryViewModel categoryViewModel;

    CategoryAdapter categoryAdapter;

    public CategoryFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCategoryBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        categoryViewModel = new ViewModelProvider(this).get(CategoryViewModel.class);

        binding.addCategory.setOnClickListener(v -> {
            Dialog dialog = new Dialog(getContext());
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.layout_add_category);

            dialog.findViewById(R.id.add_category_close).setOnClickListener(v1 -> dialog.dismiss());

            TextInputLayout categoryNameTIL = dialog.findViewById(R.id.category_name_text_input_layout);
            EditText categoryNameET = dialog.findViewById(R.id.category_name_text_input_edit_text);
            Button saveCategoryBtn = dialog.findViewById(R.id.save_category);

            saveCategoryBtn.setOnClickListener(v12 -> {
                String categoryName = categoryNameET.getText().toString();

                if (categoryName.equals("")) {
                    categoryNameTIL.setErrorEnabled(true);
                    categoryNameTIL.setError("Please enter Category name.");
                } else {
                    categoryNameTIL.setErrorEnabled(false);
                    String id = UUID.randomUUID().toString();
                    Category category = new Category(id, categoryName);
                    categoryViewModel.insert(category);
                    Toast.makeText(getContext(), "New category added.", Toast.LENGTH_SHORT).show();
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
        binding.categoriesRv.setLayoutManager(layoutManager);

        categoryViewModel.getAllCategories().observe(getViewLifecycleOwner(), categories -> {
            categoryAdapter = new CategoryAdapter(getActivity(), categories, this);
            binding.categoriesRv.setAdapter(categoryAdapter);
        });

    }

    @Override
    public void onSelectCategory(Category category) {
        Bundle result = new Bundle();
        result.putString("categoryId", category.getId());
        result.putString("categoryName", category.getName());
        getParentFragmentManager().setFragmentResult(AddNewTransactionFragment.CATEGORY_REQUEST_CODE, result);
        getParentFragmentManager().popBackStack();
    }
}