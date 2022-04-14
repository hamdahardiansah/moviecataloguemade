package com.devkominfosda.favorite.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.devkominfosda.moviecataloguebajp1.core.domain.model.Movies
import com.devkominfosda.moviecataloguebajp1.core.domain.usecase.MoviesUseCase

class FavoriteViewModel(private val movieUseCase: MoviesUseCase) : ViewModel() {

    fun getFavoriteMovies(): LiveData<List<Movies>> =
        movieUseCase.getFavoriteMovies().asLiveData()

}