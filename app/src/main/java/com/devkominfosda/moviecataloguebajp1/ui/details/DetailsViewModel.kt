package com.devkominfosda.moviecataloguebajp1.ui.details

import androidx.lifecycle.ViewModel
import com.devkominfosda.moviecataloguebajp1.core.domain.model.Movies
import com.devkominfosda.moviecataloguebajp1.core.domain.usecase.MoviesUseCase

class DetailsViewModel(private val movieUseCase: MoviesUseCase) : ViewModel() {

    fun setFavoriteMovie(movie: Movies, newState: Boolean) {
        movieUseCase.setMovieFavorite(movie, newState)
    }
}