package com.devexperto.architectcoders.ui.detail

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.devexperto.architectcoders.databinding.ActivityDetailBinding
import com.devexperto.architectcoders.model.Movie
import com.devexperto.architectcoders.ui.loadUrl

class DetailActivity : AppCompatActivity() {
    companion object {
        const val MOVIE = "DetailActivity:movie"
    }

    private val viewModel: DetailViewModel by viewModels {
        DetailViewModelFactory(
            movie = requireNotNull(
                intent.getParcelableExtra(MOVIE)
            )
        )
    }
    private lateinit var binding: ActivityDetailBinding

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel.state.observe(this) { uiState ->
            uiState.movie?.let { updateUI(it) }
        }
    }


    private fun updateUI(movie: Movie) = with(binding) {
        movieDetailToolbar.title = movie.title
        movieDetailImage.loadUrl("https://image.tmdb.org/t/p/w780${movie.backdropPath}")
        movieDetailSummary.text = movie.overview
        movieDetailInfo.setMovie(movie)
    }
}