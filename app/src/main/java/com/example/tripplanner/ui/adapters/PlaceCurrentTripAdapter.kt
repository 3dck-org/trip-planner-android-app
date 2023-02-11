package com.example.tripplanner.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tripplanner.R
import com.example.tripplanner.databinding.ItemPlacesBinding
import com.example.tripplanner.domain.JourneyPlaceInfo
import com.example.tripplanner.domain.StatusRequest

class PlaceCurrentTripAdapter(val function: (statusRequest: StatusRequest) -> Unit) :
    RecyclerView.Adapter<PlaceCurrentTripAdapter.PlaceCurrentTripViewHolder>() {

    lateinit var itemBinding: ItemPlacesBinding
    private val placeInfoList: MutableList<JourneyPlaceInfo> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaceCurrentTripViewHolder {
        initViewBinding(parent)
        return PlaceCurrentTripViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: PlaceCurrentTripViewHolder, position: Int) {
        holder.initViewHolder(placeInfoList[position])
    }

    override fun getItemCount() = placeInfoList.size


    fun addData(placeInfoList: List<JourneyPlaceInfo>) {
        this.placeInfoList.apply {
            clear()
            addAll(placeInfoList.sortedByDescending { it.status=="inactive" }.sortedByDescending { it.status=="active" })
        }
        notifyDataSetChanged()
    }

    private fun initViewBinding(parent: ViewGroup) {
        itemBinding = ItemPlacesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    }

    inner class PlaceCurrentTripViewHolder(val itemViewBinding: ItemPlacesBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {

        fun initViewHolder(
            placeInfo: JourneyPlaceInfo
        ) {
            with(itemViewBinding) {
                tvPlaceAddress.text =
                    "${placeInfo.place.address.street} ${placeInfo.place.address.buildingNumber}"
                tvPlaceName.text = placeInfo.place.name
                with(btnTrip) {
                    when (placeInfo.status) {
                        "active" -> {
                            isEnabled = true
                            isClickable = true
                            setIconResource(R.drawable.ic_pause)
                            setOnClickListener { function.invoke(StatusRequest(placeInfo.journey_id, placeInfo.place_id, "visited")) }
                        }
                        "inactive" -> {
                            if (placeInfoList.find { it.status=="active" }!=null){
                                isEnabled = false
                                isClickable = false
                                setIconResource(R.drawable.ic_started_trip)
                            }else{
                                isEnabled = true
                                isClickable = true
                                setOnClickListener { function.invoke(StatusRequest(placeInfo.journey_id, placeInfo.place_id, "active")) }
                                setIconResource(R.drawable.ic_started_trip)
                            }
                        }
                        "visited" -> {
                            isEnabled = false
                            isClickable = false
                            setIconResource(R.drawable.ic_check)
                        }
                    }
                }
            }
        }
    }
}