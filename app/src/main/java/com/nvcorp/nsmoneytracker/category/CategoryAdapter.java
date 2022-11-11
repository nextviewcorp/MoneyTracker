package com.nvcorp.nsmoneytracker.category;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nvcorp.nsmoneytracker.databinding.LayoutCategoryCellBinding;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {

    LayoutCategoryCellBinding binding;

    Context context;
    List<Category> categoryList;

    public CategoryAdapter(Context context, List<Category> categoryList) {
        this.context = context;
        this.categoryList = categoryList;
    }

    @NonNull
    @Override
    public CategoryAdapter.CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = LayoutCategoryCellBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new CategoryViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryAdapter.CategoryViewHolder holder, int position) {
        if (categoryList.size() != 0) {
            Category currentCategory = categoryList.get(position);
            holder.binding.category.setText(currentCategory.getName());
        }
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
