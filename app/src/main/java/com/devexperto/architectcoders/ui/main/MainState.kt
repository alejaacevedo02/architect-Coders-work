package com.devexperto.architectcoders.ui.main

import android.Manifest
import android.content.Context
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.devexperto.architectcoders.R
import com.devexperto.architectcoders.domain.Error
import com.devexperto.architectcoders.ui.common.PermissionRequester
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class MainState(
    private val context: Context,
    private val scope: CoroutineScope,
    private val navController: NavController,
    private val locationPermissionRequester: PermissionRequester
) {
    fun onMovieClicked(movieId: Int) {
        val navAction = MainFragmentDirections.actionMainToDetail(movieId)
        navController.navigate(navAction)
    }

    fun requestLocationPermission(afterRequest: (Boolean) -> Unit) {
        scope.launch {
            val result = locationPermissionRequester.request()
            afterRequest(result)
        }
    }

    fun errorToString(error: com.devexperto.architectcoders.domain.Error) = when (error) {
        com.devexperto.architectcoders.domain.Error.Connectivity -> context.getString(R.string.connectivity_error)
        is com.devexperto.architectcoders.domain.Error.Server -> context.getString(R.string.server_error, error.code)
        is com.devexperto.architectcoders.domain.Error.Unknown -> context.getString(R.string.unknown_error, error.message)
    }

}

fun Fragment.buildMainState(
    scope: CoroutineScope = viewLifecycleOwner.lifecycleScope,
    navController: NavController = findNavController(),
    locationPermissionRequester: PermissionRequester = PermissionRequester(
        this,
        Manifest.permission.ACCESS_COARSE_LOCATION
    ),
    context: Context = requireContext()
) = MainState(context, scope, navController, locationPermissionRequester)
