package com.example.tripplanner.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tripplanner.databinding.ItemCityFiltersBinding
import com.example.tripplanner.db.entities.CityEntity

class CityFilterAdapter(
    val updateDatabase: (city: CityEntity) -> Unit
) : RecyclerView.Adapter<CityFilterAdapter.CityFilterViewHolder>() {

    lateinit var itemBinding: ItemCityFiltersBinding
    private val cityFilterList: MutableList<CityEntity> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityFilterViewHolder {
        initViewBinding(parent)
        return CityFilterViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: CityFilterViewHolder, position: Int) {
        holder.initViewHolder(cityFilterList[position])
    }

    override fun getItemCount(): Int = cityFilterList.size

    fun addData(cities: List<CityEntity>) {
        this.cityFilterList.apply {
            clear()
            addAll(cities)
        }
        notifyDataSetChanged()
    }

    private fun initViewBinding(parent: ViewGroup) {
        itemBinding = ItemCityFiltersBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    }

    inner class CityFilterViewHolder(val itemViewBinding: ItemCityFiltersBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {

            fun initViewHolder(city: CityEntity){
                itemViewBinding.tvCityName.text = city.city
            }
    }
}