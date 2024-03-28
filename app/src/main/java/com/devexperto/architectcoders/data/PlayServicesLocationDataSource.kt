package com.devexperto.architectcoders.data

import android.annotation.SuppressLint
import android.app.Application
import android.location.Geocoder
import android.location.Location
import com.devexperto.architectcoders.data.datasource.LocationDataSource
import com.devexperto.architectcoders.ui.common.getFromLocationCompat
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.suspendCancellableCoroutine
import javax.inject.Inject
import kotlin.coroutines.resume

class PlayServicesLocationDataSource @Inject constructor(application: Application) : LocationDataSource {
    private val fusedLocationClient = LocationServices.getFusedLocationProviderClient(application)
    private val geocoder = Geocoder(application)

    override suspend fun findLastRegion(): String? = findLastLocation().toRegion()

    @SuppressLint("MissingPermission")
    private suspend fun findLastLocation(): Location =
        suspendCancellableCoroutine { continuation ->
            fusedLocationClient.lastLocation
                .addOnCompleteListener {
                    continuation.resume(it.result)
                }
        }

    private suspend fun Location?.toRegion(): String? {
        val addresses = this?.let {
            geocoder.getFromLocationCompat(latitude, longitude, 1)
        }
        return addresses?.firstOrNull()?.countryCode
    }
}