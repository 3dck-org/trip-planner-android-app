package com.example.tripplanner.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tripplanner.databinding.ItemCategoryFiltersBinding
import com.example.tripplanner.domain.Category
import timber.log.Timber

class CategoriesFilterAdapter : RecyclerView.Adapter<CategoriesFilterAdapter.CategoriesViewHolder>() {

    lateinit var itemBinding: ItemCategoryFiltersBinding
    private val categoryFilterList: MutableList<Category> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoriesViewHolder {
        initViewBinding(parent)
        return CategoriesViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: CategoriesViewHolder, position: Int) {
        holder.initViewHolder(categoryFilterList[position])
    }

    override fun getItemCount(): Int = categoryFilterList.size

    fun addData(categories: List<Category>) {
        this.categoryFilterList.apply {
            clear()
            Timber.d(" ****** ${categories}")
            addAll(categories)
        }
        notifyDataSetChanged()
    }

    private fun initViewBinding(parent: ViewGroup) {
        itemBinding = ItemCategoryFiltersBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    }

    inner class CategoriesViewHolder(val itemViewBinding: ItemCategoryFiltersBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {

            fun initViewHolder(category: Category){
                itemViewBinding.tvCategoryName.text = category.name
            }
    }
}