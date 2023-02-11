package com.example.tripplanner.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tripplanner.databinding.ItemCityFiltersBinding
import timber.log.Timber

class CityFilterAdapter : RecyclerView.Adapter<CityFilterAdapter.CityFilterViewHolder>() {

    lateinit var itemBinding: ItemCityFiltersBinding
    private val cityFilterList: MutableList<String> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityFilterViewHolder {
        initViewBinding(parent)
        return CityFilterViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: CityFilterViewHolder, position: Int) {
        holder.initViewHolder(cityFilterList[position])
    }

    override fun getItemCount(): Int = cityFilterList.size

    fun addData(cities: List<String>) {
        this.cityFilterList.apply {
            clear()
            Timber.d(" ****** ${cities}")
            addAll(cities)
        }
        notifyDataSetChanged()
    }

    private fun initViewBinding(parent: ViewGroup) {
        itemBinding = ItemCityFiltersBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    }

    inner class CityFilterViewHolder(val itemViewBinding: ItemCityFiltersBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {

            fun initViewHolder(city: String){
                itemViewBinding.tvCityName.text = city
            }
    }
}