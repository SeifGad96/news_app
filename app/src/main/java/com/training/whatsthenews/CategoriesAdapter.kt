package com.training.whatsthenews

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder

import com.training.whatsthenews.databinding.CategoryListItemBinding


class CategoriesAdapter(val fragment: Fragment, private val categories: List<Category>) :
    Adapter<CategoriesAdapter.CategoriesViewHolder>() {
    class CategoriesViewHolder(val binding: CategoryListItemBinding) : ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoriesViewHolder {
        val binding =
            CategoryListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CategoriesViewHolder(binding)
    }

    override fun getItemCount() = categories.size

    override fun onBindViewHolder(holder: CategoriesViewHolder, position: Int) {
        val viewModel = ViewModelProvider(fragment)[CategoryViewModel::class.java]
        holder.binding.categoryIv.setImageResource(categories[position].categoryImgId)
        holder.binding.categoryNameTv.text = categories[position].name
        holder.binding.categoryContainer.setOnClickListener {
            viewModel.updateCategoryImage(categories[position].categoryImgId)
            Log.d("test", "${categories[position].name}")
        }

    }

}