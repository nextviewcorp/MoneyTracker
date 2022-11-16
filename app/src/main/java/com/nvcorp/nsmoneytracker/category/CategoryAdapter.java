package com.nvcorp.nsmoneytracker.category;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nvcorp.nsmoneytracker.databinding.LayoutCategoryCellBinding;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {

    LayoutCategoryCellBinding binding;

    Context context;
    List<Category> categoryList;
    OnSelectCategory onSelectCategory;

    public CategoryAdapter(Context context, List<Category> categoryList, OnSelectCategory onSelectCategory) {
        this.context = context;
        this.categoryList = categoryList;
        this.onSelectCategory = onSelectCategory;
    }

    @NonNull
    @Override
    public CategoryAdapter.CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = LayoutCategoryCellBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new CategoryViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryAdapter.CategoryViewHolder holder, int position) {
        Category currentCategory = categoryList.get(position);
        holder.binding.category.setText(currentCategory.getName());
        holder.binding.category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onSelectCategory.onSelectCategory(currentCategory);
            }
        });
    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    public class CategoryViewHolder extends RecyclerView.ViewHolder {

        LayoutCategoryCellBinding binding;

        public CategoryViewHolder(LayoutCategoryCellBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
