package com.example.tripplanner.adapters

import android.graphics.drawable.Icon
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.tripplanner.R
import com.example.tripplanner.databinding.OfferedTripItemBinding
import com.example.tripplanner.extensions.makeGone
import com.example.tripplanner.extensions.makeVisible
import com.example.tripplanner.models.TripsResponseItem

class TripsAdapter(
    private val showSubscriptionOption: (trip: TripsResponseItem) -> Unit
) : RecyclerView.Adapter<TripsAdapter.TripsViewHolder>() {

    private lateinit var binding: OfferedTripItemBinding
    private val listOfTrips: MutableList<TripsResponseItem> = mutableListOf()
    private var activeTripId: Int = -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TripsViewHolder {
        provideDataBinding(parent)
        return TripsViewHolder(binding)
    }

    private fun provideDataBinding(parent: ViewGroup){
        binding = OfferedTripItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
    }

    override fun getItemCount(): Int = listOfTrips.size

    override fun onBindViewHolder(
        holder: TripsAdapter.TripsViewHolder,
        position: Int
    ) {
        provideDataBinding(holder.binding.root)
        holder.init(listOfTrips[position], activeTripId) {
            showSubscriptionOption.invoke(it)
            notifyDataSetChanged()
        }
    }

    fun addDataToAdapter(list: MutableList<TripsResponseItem>, activeTripId: Int = -1) {
        this.listOfTrips.clear()
        this.listOfTrips.addAll(list.sortedByDescending { trip -> trip.id == activeTripId })
        this.activeTripId = -1
        this.activeTripId = activeTripId
        notifyDataSetChanged()
    }

    inner class TripsViewHolder(val binding: OfferedTripItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun init(
            trip: TripsResponseItem,
            activeTripId: Int,
            select: (trip: TripsResponseItem) -> Unit
        ) {
            binding.tripTitleTv.text = trip.name
            binding.tripDurationTv.text = "Duration: ${trip.duration} mins"
            binding.tripLengthTv.text = "Length: ${trip.distance}km"
            binding.tripCategoryTv.text = "Category: ${trip.description}"
            with(binding) {
                if (trip.id != activeTripId) {
                    tripItemCv.strokeWidth = 0
                    changeItemTripBtn(true)
                    selectTripsOnClick(trip, select)
                } else {
                    tripItemCv.strokeWidth = 2
                    changeItemTripBtn()
                    tripItemCv.strokeColor = ContextCompat.getColor(binding.root.context, R.color.base_color_3)
                }
            }
            getImageFromURL(trip.image_url)
        }

        private fun changeItemTripBtn(isSelectedBtn: Boolean = false){
            if (isSelectedBtn)
                binding.tripBtn.setIconResource(R.drawable.ic_right_arrow)
            else
                binding.tripBtn.setIconResource(R.drawable.ic_check)
        }

        private fun selectTripsOnClick(
            trip: TripsResponseItem,
            select: (trip: TripsResponseItem) -> Unit
        ) {
            binding.tripBtn.setOnClickListener {
                select.invoke(trip)
            }
            binding.tripItemCv.setOnClickListener {
                select.invoke(trip)
            }
        }

        private fun getImageFromURL(imageUrl: String) {
            Glide.with(binding.root)
                .load(imageUrl)
                .centerCrop()
                .error(R.drawable.ic_godlen_city)
                .apply(RequestOptions().transform(CenterCrop(), RoundedCorners(16)))
                .into(binding.userIv)
        }
    }
}