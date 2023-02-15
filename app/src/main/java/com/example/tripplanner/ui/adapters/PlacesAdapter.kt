package com.example.tripplanner.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tripplanner.databinding.ItemPlacesBinding
import com.example.tripplanner.domain.TripPlaceInfo

class PlaceAdapter : RecyclerView.Adapter<PlaceAdapter.PlaceViewHolder>() {

    lateinit var itemBinding: ItemPlacesBinding
    private val placesList: MutableList<TripPlaceInfo> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaceViewHolder {
        initViewBinding(parent)
        return PlaceViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: PlaceViewHolder, position: Int) {
        holder.updateViewHolder(placesList[position], position)
    }

    override fun getItemCount() = placesList.size

    fun addData(placesList: List<TripPlaceInfo>) {
        this.placesList.apply {
            clear()
            addAll(placesList)
        }
        notifyDataSetChanged()
    }

    private fun initViewBinding(parent: ViewGroup) {
        itemBinding = ItemPlacesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    }

    inner class PlaceViewHolder(val itemViewBinding: ItemPlacesBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {

        fun updateViewHolder(
            place: TripPlaceInfo,
            position: Int
        ) {
            with(itemViewBinding) {
                tvPlaceAddress.text =
                    place.place.address.street
                tvPlaceName.text = place.place.name
                btnTrip.apply {
                    icon = null
                    isClickable = false
                    isEnabled=true
                    text = (position + 1).toString()
                    textSize = 16f
                }
            }
        }
    }
}