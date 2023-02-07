package com.example.tripplanner.ui.fragments.menu_fragments

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
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
import com.example.tripplanner.databinding.FragmentTripMapBinding
import com.example.tripplanner.domain.Resource
import com.example.tripplanner.extensions.makeGone
import com.example.tripplanner.extensions.makeVisible
import com.example.tripplanner.ui.activities.MenuActivity
import com.example.tripplanner.ui.dialogs.DialogImpl
import com.example.tripplanner.utils.permission.PermissionManager
import com.example.tripplanner.view_models.CurrentJourneyViewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class TripMapFragment : Fragment() {

    private val menuActivityInstance by lazy { activity as MenuActivity }
    val viewModel: CurrentJourneyViewModel by viewModels()
    private var callback: OnMapReadyCallback? = null
    private val permissionManager = PermissionManager.from(this)
    private lateinit var binding: FragmentTripMapBinding

    private fun initViewBinding() {
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
                                showDialogByType(State.SUCCESS)
                                val latLng = LatLng(
                                    journey.place.point.x.toDouble(),
                                    journey.place.point.y.toDouble()
                                )
                                callback = OnMapReadyCallback { googleMap ->
                                    googleMap.uiSettings.setAllGesturesEnabled(true)
                                    googleMap.uiSettings.isMapToolbarEnabled = true
                                    googleMap.uiSettings.isCompassEnabled = true
                                    addMarkerToMap(googleMap, latLng, journey.place.name)
                                    googleMap.moveCamera(
                                        CameraUpdateFactory.newLatLngZoom(
                                            latLng,
                                            15f
                                        )
                                    )
                                    googleMap.isMyLocationEnabled = true
                                }
                            } else {
                                showDialogByType(State.EMPTY_PLACE)
                                callback = OnMapReadyCallback { googleMap ->
                                    googleMap.isMyLocationEnabled = true
                                }
                            }
                            val mapFragment =
                                childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
                            callback?.let { c -> mapFragment?.getMapAsync(c) }
                        }
                    }
                    is Resource.Empty -> {
                        showDialogByType(State.EMPTY_JOURNEY)
                    }
                    else ->{ Timber.d(" Other answer") }
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

    private fun showDialogByType(state: State) {
        var dialogInformation: DialogImpl.DialogInformation? = null
        context?.let { fragmentContext ->
            when (state) {
                State.SUCCESS -> {
                    dialogInformation = DialogImpl.DialogInformation(
                        "Cool",
                        "Click on your place and use additional buttons to navigate to your place.\n" +
                                "To get back please, click \"back\"",
                        "Okay"
                    )
                }
                State.EMPTY_JOURNEY -> {
                    dialogInformation = DialogImpl.DialogInformation(
                        "Start a journey",
                        "Actually you did not pick any trip. Please start your first journey!",
                        "Ok"
                    )
                }
                State.EMPTY_PLACE -> {
                    dialogInformation = DialogImpl.DialogInformation(
                        "Pick a place",
                        "Actually you did not pick any place to visit. Please go back to your journey and pick one",
                        "Ok"
                    )
                }
            }
            dialogInformation?.let { showDialog(fragmentContext, it) }
        }
    }

    private fun showDialog(context: Context, dialogInformation: DialogImpl.DialogInformation) {
        DialogImpl.showDialog(
            context,
            dialogInformation
        )
    }

    private enum class State {
        SUCCESS,
        EMPTY_PLACE,
        EMPTY_JOURNEY
    }
}