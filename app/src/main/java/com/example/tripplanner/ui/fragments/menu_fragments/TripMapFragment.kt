package com.example.tripplanner.ui.fragments.menu_fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.tripplanner.R
import com.example.tripplanner.domain.Resource
import com.example.tripplanner.ui.activities.MenuActivity
import com.example.tripplanner.view_models.CurrentJourneyViewModel
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class TripMapFragment : Fragment() {

    private val menuActivityInstance by lazy { activity as MenuActivity }
    val viewModel: CurrentJourneyViewModel by viewModels()
    private var callback : OnMapReadyCallback? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        collectJourneyResponse()
        viewModel.getCurrentJourney()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        menuActivityInstance.hideMenu()
        return inflater.inflate(R.layout.fragment_trip_map, container, false)
    }

    private fun addMarkerToMap(
        googleMap: GoogleMap,
        sydney2: LatLng,
        title: String
    ) {
        googleMap.addMarker(MarkerOptions().position(sydney2).title(title))
    }

    @SuppressLint("MissingPermission")
    private fun collectJourneyResponse(){
        lifecycleScope.launch{
            viewModel.response.collect{
                when(it){
                    is Resource.Success -> {
                        it.data.journey_place_infos.find { it.status == "active" }.let {
                            journey ->
                            if(journey !=null){
                                Timber.d("*******@*$!**")
                                callback = OnMapReadyCallback { googleMap ->
                                    addMarkerToMap(googleMap, LatLng(journey.place.point.x.toDouble(),journey.place.point.y.toDouble()),"")
                                    googleMap.isMyLocationEnabled = true
                                }
                            }else{
                                callback = OnMapReadyCallback { googleMap ->
                                    googleMap.isMyLocationEnabled = true
                                }
                            }
                            val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
                            callback?.let { c-> mapFragment?.getMapAsync(c)}
                        }
                    }
                    else -> {}
                }
            }
        }
    }
}