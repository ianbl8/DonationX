package com.ianbl8.donationx.ui.map

import android.annotation.SuppressLint
import android.app.Application
import android.location.Location
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.GoogleMap
import timber.log.Timber

@SuppressLint("MissingPermission")
class MapsViewModel(application: Application): AndroidViewModel(application) {
    lateinit var map: GoogleMap
    var currentLocation = MutableLiveData<Location>()
    var locationClient: FusedLocationProviderClient

    init {
        locationClient = LocationServices.getFusedLocationProviderClient(application)
    }

    fun updateCurrentLocation() {
        if (locationClient.lastLocation.isSuccessful) locationClient.lastLocation.addOnSuccessListener { location: Location? ->
            currentLocation.value = location!!
            Timber.i("MapVM location success: %s", currentLocation.value)
        } else {
            currentLocation.value = Location("Default").apply {
                latitude = 52.245696
                longitude = -7.139102
            }
            Timber.i("MapVM location: %s", currentLocation.value)
        }
    }
}