package com.devexperto.architectcoders.ui.main

import android.Manifest
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.devexperto.architectcoders.R
import com.devexperto.architectcoders.databinding.FragmentMainBinding
import com.devexperto.architectcoders.model.MoviesRepository
import com.devexperto.architectcoders.ui.common.PermissionRequester
import com.devexperto.architectcoders.ui.launchAndCollect
import com.devexperto.architectcoders.ui.visible

class MainFragment : Fragment(R.layout.fragment_main) {

    private val viewModel: MainViewModel by viewModels {
        MainViewModelFactory(
            MoviesRepository(
                requireActivity().application
            )
        )
    }

    private val adapter = MoviesAdapter { mainState.onMovieClicked(it) }
    private lateinit var mainState: MainState

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainState = buildMainState()
        val binding = FragmentMainBinding.bind(view).apply {
            recycler.adapter = adapter
        }
        viewLifecycleOwner.launchAndCollect(viewModel.state) { binding.updateUI(it) }
        mainState.requestLocationPermission { viewModel.onUiReady() }
    }

    private fun FragmentMainBinding.updateUI(state: UiState) {
        progress.visible = state.loading
        state.movies?.let(adapter::submitList)
    }
}

