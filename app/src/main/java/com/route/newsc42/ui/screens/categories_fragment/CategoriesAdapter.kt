package com.route.newsc42.ui.screens.categories_fragment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.route.newsc42.databinding.ItemCategoryLeftBinding
import com.route.newsc42.databinding.ItemCategoryRightBinding
import com.route.newsc42.ui.model.Category

class CategoriesAdapter(val categories: List<Category>, val onCategoryClick: (Category) -> Unit) :
    RecyclerView.Adapter<CategoriesAdapter.CategoryViewHolder>() {
    companion object {
        const val ODD_CATEGORY = 1
        const val EVEN_CATEGORY = 2
    }

    override fun getItemViewType(position: Int): Int =
        if (position % 2 == 0) ODD_CATEGORY else EVEN_CATEGORY

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CategoryViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = if (viewType == ODD_CATEGORY) ItemCategoryLeftBinding.inflate(
            layoutInflater,
            parent,
            false
        ) else ItemCategoryRightBinding.inflate(
            layoutInflater,
            parent,
            false
        )
        return CategoryViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: CategoryViewHolder,
        position: Int
    ) {

        val category = categories[position]
        holder.binding.root.setOnClickListener {
            onCategoryClick(category)
        }
        if (holder.binding is ItemCategoryLeftBinding) {
            holder.binding.categoryTitle.text = category.title
           holder.binding.categoryImage.setImageResource(category.imageId);
        } else if (holder.binding is ItemCategoryRightBinding) {
            holder.binding.categoryTitle.text = category.title
            holder.binding.categoryImage.setImageResource(category.imageId);
        }
    }

    override fun getItemCount(): Int = categories.size

    class CategoryViewHolder(val binding: ViewBinding) : RecyclerView.ViewHolder(binding.root)
}