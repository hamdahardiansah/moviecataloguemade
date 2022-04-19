package com.devkominfosda.moviecataloguebajp1.ui.details

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.devkominfosda.moviecataloguebajp1.R
import com.devkominfosda.moviecataloguebajp1.databinding.ActivityDetailsBinding
import com.devkominfosda.moviecataloguebajp1.core.domain.model.Movies
import com.devkominfosda.moviecataloguebajp1.core.utils.gone
import com.devkominfosda.moviecataloguebajp1.core.utils.loadImage
import com.jakewharton.rxbinding2.view.clicks
import org.koin.android.viewmodel.ext.android.viewModel

class DetailsActivity : AppCompatActivity() {
    companion object {
        const val EXTRA_DATA = "extra_data"
    }

    private lateinit var binding: ActivityDetailsBinding
    private val viewModel: DetailsViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val detailMovie = intent.getParcelableExtra<Movies>(EXTRA_DATA)
        if (detailMovie != null) {
            loadDataDetail(detailMovie)
        }
    }

    @SuppressLint("CheckResult")
    private fun loadDataDetail(movie: Movies) {
        binding.apply {
            svLoadingDetail.gone()
            tvTitleDetail.text = movie.title
            tvLanguage.text = movie.originalLanguage
            tvPopularity.text = movie.popularity.toString()
            tvOverviewDetail.text = movie.overview
            tvReleaseDate.text = movie.releaseDate
            tvRatingDetail.text = movie.voteAverage.toString()
            loadImage(getString(R.string.url_poster, movie.posterPath), roundedPosterDetail)
            loadImage(getString(R.string.url_poster, movie.backdropPath), posterBackdrop)

            var statusFavorite = movie.isFavorite
            setFavorite(statusFavorite)
            fabFavorite.clicks().subscribe {
                statusFavorite = !statusFavorite
                viewModel.setFavoriteMovie(movie, statusFavorite)
                setFavorite(statusFavorite)
            }
        }
    }

    private fun setFavorite(state: Boolean) {
        if (state) {
            binding.fabFavorite.setImageDrawable(
                ContextCompat.getDrawable(
                    this, R.drawable.ic_favorite_primary
                )
            )
        } else {
            binding.fabFavorite.setImageDrawable(
                ContextCompat.getDrawable(
                    this, R.drawable.ic_favorite_border
                )
            )
        }
    }

}
