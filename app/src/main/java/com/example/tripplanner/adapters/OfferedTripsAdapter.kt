package com.example.tripplanner.adapters

import android.content.res.Resources
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
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
import com.google.android.material.card.MaterialCardView
import timber.log.Timber

class OfferedTripsAdapter(
    val showSubscriptionOption: (trip: TripsResponseItem) -> Unit
) : RecyclerView.Adapter<OfferedTripsAdapter.OfferedTripsViewHolder>() {

    private val listOfTrips: MutableList<TripsResponseItem> = mutableListOf()
    private var activeTripId: Int = -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OfferedTripsViewHolder {
        return OfferedTripsViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.offered_trip_item, parent, false))
    }

    override fun getItemCount(): Int = listOfTrips.size

    override fun onBindViewHolder(
        holder: OfferedTripsAdapter.OfferedTripsViewHolder,
        position: Int
    ) {
        holder.init(listOfTrips[position], activeTripId) {
            Timber.d(" 3: ${listOfTrips}")
            Timber.d(" 3: ${activeTripId}")
            showSubscriptionOption.invoke(it)
            notifyDataSetChanged()
        }
    }

    fun addDataToAdapter(list: MutableList<TripsResponseItem>, activeTripId: Int = -1) {
        this.listOfTrips.clear()
        notifyDataSetChanged()
        this.listOfTrips.addAll(list.sortedByDescending { it -> it.id == activeTripId })
        notifyDataSetChanged()
        this.activeTripId = -1
        notifyDataSetChanged()
        this.activeTripId = activeTripId
        Timber.d(" 2: ${list.sortedByDescending { it -> it.id == activeTripId }}")
        Timber.d(" 2: ${this.activeTripId}")
        notifyDataSetChanged()
    }

    fun removeDataFromAdapter() {
        this.listOfTrips.clear()
        notifyDataSetChanged()
    }

    inner class OfferedTripsViewHolder(val view: View) :
        RecyclerView.ViewHolder(view) {

        var tripTitleTv : TextView?=null
        var tripDurationTv : TextView?=null
        var tripLengthTv : TextView?=null
        var tripCategoryTv : TextView?=null
        var tripItemCv : MaterialCardView?=null
        var userIv : ImageView?=null
        var selectBtn : Button?=null
        var selectedBtn : Button?=null


        init {
            tripTitleTv = view.findViewById<TextView>(R.id.trip_title_tv)
            tripDurationTv = view.findViewById<TextView>(R.id.trip_duration_tv)
            tripLengthTv = view.findViewById<TextView>(R.id.trip_length_tv)
            tripCategoryTv = view.findViewById<TextView>(R.id.trip_category_tv)
            tripItemCv = view.findViewById<MaterialCardView>(R.id.trip_item_cv)
            userIv = view.findViewById<ImageView>(R.id.user_iv)
            selectBtn = view.findViewById<Button>(R.id.select_btn)
            selectedBtn = view.findViewById<Button>(R.id.selected_btn)
        }

        fun init(
            trip: TripsResponseItem,
            activeTripId: Int,
            select: (trip: TripsResponseItem) -> Unit
        ) {
            tripTitleTv?.text = trip.name
            tripDurationTv?.text = "Duration: ${trip.duration}"
            tripLengthTv?.text = "Length: ${trip.distance}"
            tripCategoryTv?.text = "Category: ${trip.description}"
                if (trip.id != activeTripId) {
                    Timber.d("*****1 ${activeTripId}")
                    selectBtn?.makeVisible()
                    selectedBtn?.makeGone()
                    tripItemCv?.strokeWidth = 0
                    selectTripsOnClick(trip, select)
                } else {
                    Timber.d("*****2 ${activeTripId}")
                    selectBtn?.makeGone()
                    selectedBtn?.makeVisible()
                    tripItemCv?.strokeWidth = 2
                    tripItemCv?.strokeColor = ContextCompat.getColor(view.context, R.color.base_color_3)
            }
            getImageFromURL(trip.image_url)
        }

        private fun selectTripsOnClick(
            trip: TripsResponseItem,
            select: (trip: TripsResponseItem) -> Unit
        ) {
            selectBtn?.setOnClickListener {
                select.invoke(trip)
            }
        }

        private fun getImageFromURL(imageUrl: String) {
            userIv?.let {
                Glide.with(view)
                    .load(imageUrl)
                    .centerCrop()
                    .error(R.drawable.ic_godlen_city)
                    .apply(RequestOptions().transform(CenterCrop(), RoundedCorners(16)))
                    .into(it)
            }
        }
    }
}