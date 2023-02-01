package com.example.tripplanner.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tripplanner.databinding.ItemPlacesBinding
import com.example.tripplanner.domain.TripPlaceInfo

class PlaceAdapter : RecyclerView.Adapter<PlaceAdapter.PlaceViewHolder>() {

    private var adapterType : AdapterTypeEnum = AdapterTypeEnum.ACTIVE
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

    fun setAdapterType(isAdapterActive: Boolean){
        adapterType = when(isAdapterActive){
            true->{
                AdapterTypeEnum.ACTIVE
            }
            false ->{
                AdapterTypeEnum.NOT_ACTIVE
            }
        }
    }

    fun addData(placesList: List<TripPlaceInfo>){
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

            fun updateViewHolder(place: TripPlaceInfo, position: Int){
                with(itemViewBinding){
                    tvPlaceAddress.text =  "${place.place.address.street} ${place.place.address.buildingNumber}"
                    tvPlaceName.text = place.place.name
                    when(adapterType){
                        AdapterTypeEnum.ACTIVE ->{}
                        AdapterTypeEnum.NOT_ACTIVE ->{
                            btnTrip.apply {
                                icon = null
                                isClickable = false
                                text = (position+1).toString()
                                textSize = 16f
                            }
                        }
                    }
                }
            }
    }
}