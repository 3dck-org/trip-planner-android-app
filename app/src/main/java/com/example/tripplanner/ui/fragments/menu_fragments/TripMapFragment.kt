package com.example.tripplanner.ui.fragments.menu_fragments

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.currencyexchangeapp.utils.permission.Permission
import com.example.tripplanner.R
import com.example.tripplanner.databinding.FragmentTripBinding
import com.example.tripplanner.databinding.FragmentTripMapBinding
import com.example.tripplanner.domain.Resource
import com.example.tripplanner.domain.google_maps.DirectionResponses
import com.example.tripplanner.extensions.makeGone
import com.example.tripplanner.extensions.makeVisible
import com.example.tripplanner.ui.activities.MenuActivity
import com.example.tripplanner.utils.permission.PermissionManager
import com.example.tripplanner.view_models.CurrentJourneyViewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PolylineOptions
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import timber.log.Timber

@AndroidEntryPoint
class TripMapFragment : Fragment() {

    private val menuActivityInstance by lazy { activity as MenuActivity }
    val viewModel: CurrentJourneyViewModel by viewModels()
    private var callback: OnMapReadyCallback? = null
    private val permissionManager = PermissionManager.from(this)
    private lateinit var binding : FragmentTripMapBinding

    private fun initViewBinding(){
        binding = FragmentTripMapBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        askForPermission()
        initViewBinding()
        collectJourneyResponse()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        menuActivityInstance.hideMenu()
        return binding.root
    }

    private fun addMarkerToMap(
        googleMap: GoogleMap,
        sydney2: LatLng,
        title: String
    ): Marker? {
        return googleMap.addMarker(MarkerOptions().position(sydney2).title(title))
    }

    @SuppressLint("MissingPermission")
    private fun collectJourneyResponse() {
        lifecycleScope.launch {
            viewModel.response.collect {
                when (it) {
                    is Resource.Success -> {
                        it.data.journey_place_infos.find { it.status == "active" }.let { journey ->
                            if (journey != null) {
                                Timber.d("*******@*$!**")
                                binding.layoutEmpty.root.makeGone()
                                with(binding.layoutSuccess){
                                    root.makeVisible()
                                    tvState.text = "Fine"
                                    tvStateDescription.text = "Click on your place and use additional buttons to navigate to your place.\nTo get back please, click \"back\""
                                }
                                val latLng = LatLng(
                                    journey.place.point.x.toDouble(),
                                    journey.place.point.y.toDouble()
                                )
                                callback = OnMapReadyCallback { googleMap ->
                                    googleMap.uiSettings.setAllGesturesEnabled(true)
                                    googleMap.uiSettings.isMapToolbarEnabled = true
                                    googleMap.uiSettings.isCompassEnabled = true
                                    addMarkerToMap(googleMap, latLng, journey.place.name)
                                    googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15f))
                                    googleMap.isMyLocationEnabled = true
                                }
                            } else {
                                binding.layoutSuccess.root.makeGone()
                                with(binding.layoutEmpty){
                                    root.makeVisible()
                                    tvState.text = "Pick a place"
                                    tvStateDescription.text = "Actually you did not pick any place to visit. Please go back to your journey and pick one"
                                }
                                callback = OnMapReadyCallback { googleMap ->
                                    googleMap.isMyLocationEnabled = true
                                }
                            }
                            val mapFragment =
                                childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
                            callback?.let { c -> mapFragment?.getMapAsync(c) }
                        }
                    }
                    else -> {
                        binding.layoutSuccess.root.makeGone()
                        with(binding.layoutEmpty){
                            root.makeVisible()
                            tvState.text = "Empty -_-"
                            tvStateDescription.text = "Actually you did not pick any trip. Please start your first journey! "
                        }
                    }
                }
            }
        }
    }

    private fun askForPermission() {
        permissionManager
            .request(Permission.Location)
            .rationale("Please, give a location permission in settings")
            .checkPermission { granted ->
                if (granted) {
                    viewModel.getCurrentJourney()
                } else {
                    openSettings()
                }
            }
    }

    private fun openSettings() {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        val uri: Uri = Uri.fromParts("package", context?.packageName, null)
        intent.data = uri
        startActivity(intent)
    }


    private fun drawPolyline(response: Response<DirectionResponses>) {
        val shape = response.body()?.routes?.get(0)?.overviewPolyline?.points
        val polyline = PolylineOptions()
//            .addAll(PolyUtil.decode(shape))
            .width(8f)
            .color(Color.RED)
//        map.addPolyline(polyline)
    }

    private interface ApiServices {
        @GET("maps/api/directions/json")
        fun getDirection(@Query("origin") origin: String,
                         @Query("destination") destination: String,
                         @Query("key") apiKey: String): Call<DirectionResponses>
    }

    private object RetrofitClient {
        fun apiServices(context: Context): ApiServices {
            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://maps.googleapis.com")
                .build()

            return retrofit.create<ApiServices>(ApiServices::class.java)
        }
    }
}