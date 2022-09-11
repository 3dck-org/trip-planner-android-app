package com.example.tripplanner.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tripplanner.databinding.OfferedTripItemBinding
import com.example.tripplanner.models.TripsResponseItem

class OfferedTripsAdapter(
    val showSubscriptionOption: (trip: TripsResponseItem) -> Unit
) : RecyclerView.Adapter<OfferedTripsAdapter.OfferedTripsViewHolder>() {

    private lateinit var binding: OfferedTripItemBinding
    private val listOfTrips: MutableList<TripsResponseItem> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OfferedTripsViewHolder {
        provideDataBinding(parent)
        return OfferedTripsViewHolder(binding)
    }

    private fun provideDataBinding(parent: ViewGroup){
        binding = OfferedTripItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
    }

    override fun getItemCount(): Int = listOfTrips.size

    override fun onBindViewHolder(
        holder: OfferedTripsAdapter.OfferedTripsViewHolder,
        position: Int
    ) {
        holder.init(listOfTrips[position]) {
            showSubscriptionOption.invoke(it)
            notifyDataSetChanged()
        }
    }

    fun addDataToAdapter(list: MutableList<TripsResponseItem>) {
        this.listOfTrips.clear()
        this.listOfTrips.addAll(list)
        notifyDataSetChanged()
    }

    inner class OfferedTripsViewHolder(binding: OfferedTripItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun init(trip: TripsResponseItem, func: (trip: TripsResponseItem) -> Unit) {
            binding.tripTitleTv.text = trip.name
            binding.tripDurationTv.text = "Duration: ${trip.duration}"
            binding.tripLengthTv.text = "Length: ${trip.distance}"
            binding.tripCategoryTv.text = "Category: ${trip.created_at}"
        }

    }
}