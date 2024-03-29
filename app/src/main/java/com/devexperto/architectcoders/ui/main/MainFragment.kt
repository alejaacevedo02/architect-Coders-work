package com.devexperto.architectcoders.ui.main

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.devexperto.architectcoders.R
import com.devexperto.architectcoders.data.AndroidPermissionChecker
import com.devexperto.architectcoders.data.MoviesRepository
import com.devexperto.architectcoders.data.PlayServicesLocationDataSource
import com.devexperto.architectcoders.data.RegionRepository
import com.devexperto.architectcoders.databinding.FragmentMainBinding
import com.devexperto.architectcoders.data.database.MovieRoomLocalDataSource
import com.devexperto.architectcoders.data.server.MovieServerDataSource
import com.devexperto.architectcoders.ui.common.app
import com.devexperto.architectcoders.ui.common.launchAndCollect
import com.devexperto.architectcoders.usecases.GetPopularMoviesUseCase
import com.devexperto.architectcoders.usecases.RequestPopularMoviesUseCase

class MainFragment : Fragment(R.layout.fragment_main) {

    private val viewModel: MainViewModel by viewModels {
        val application = requireActivity().app
        val localDataSource = MovieRoomLocalDataSource(requireActivity().app.db.movieDao())
        val remoteDataSource = MovieServerDataSource(getString(R.string.api_key))
        val regionRepository = RegionRepository(
            PlayServicesLocationDataSource(application),
            AndroidPermissionChecker(application)
        )
        val repository = MoviesRepository(
            regionRepository,
            localDataSource,
            remoteDataSource
        )
        MainViewModelFactory(
            RequestPopularMoviesUseCase(repository),
           GetPopularMoviesUseCase(repository)
        )
    }

    private val adapter = MoviesAdapter { mainState.onMovieClicked(it.id) }
    private lateinit var mainState: MainState

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainState = buildMainState()
        val binding = FragmentMainBinding.bind(view).apply {
            recycler.adapter = adapter
        }
        viewLifecycleOwner.launchAndCollect(viewModel.state) {
            binding.loading = it.loading
            binding.movies = it.movies
            binding.error = it.error?.let(mainState::errorToString)
        }
        mainState.requestLocationPermission { viewModel.onUiReady() }
    }
}
