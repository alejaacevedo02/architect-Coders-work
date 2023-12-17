package com.devexperto.architectcoders.data.datasource

import android.annotation.SuppressLint
import android.app.Application
import android.location.Location
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

interface LocationDataSource {
    suspend fun findLastLocation(): Location?
}


