package com.example.tripplanner.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tripplanner.databinding.ItemCategoryFiltersBinding
import com.example.tripplanner.db.entities.CategoryEntity

class CategoriesFilterAdapter(
    val updateDatabase: (category: CategoryEntity) -> Unit
) : RecyclerView.Adapter<CategoriesFilterAdapter.CategoriesViewHolder>() {

    lateinit var itemBinding: ItemCategoryFiltersBinding
    private val categoryFilterList: MutableList<CategoryEntity> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoriesViewHolder {
        initViewBinding(parent)
        return CategoriesViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: CategoriesViewHolder, position: Int) {
        holder.initViewHolder(categoryFilterList[position])
    }

    override fun getItemCount(): Int = categoryFilterList.size

    fun addData(categories: List<CategoryEntity>) {
        this.categoryFilterList.apply {
            clear()
            addAll(categories)
        }
        notifyDataSetChanged()
    }

    private fun initViewBinding(parent: ViewGroup) {
        itemBinding =
            ItemCategoryFiltersBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    }

    inner class CategoriesViewHolder(val itemViewBinding: ItemCategoryFiltersBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {

        fun initViewHolder(category: CategoryEntity) {
            with(itemViewBinding) {
                tvCategoryName.text = category.name
                cbCategory.isChecked = category.isPicked
                cbCategory.apply {
                    if (category.isPicked) {
                        setOnClickListener {
                            updateDatabase.invoke(category.copy(isPicked = false))
                        }
                    } else {
                        setOnClickListener {
                            updateDatabase.invoke(category.copy(isPicked = true))
                        }
                    }
                }
            }
        }
    }
}