package com.devkominfosda.moviecataloguebajp1.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.devkominfosda.moviecataloguebajp1.core.data.source.Resource
import com.devkominfosda.moviecataloguebajp1.core.domain.model.Movies
import com.devkominfosda.moviecataloguebajp1.core.domain.usecase.MoviesUseCase

class HomeViewModel(private val movieUseCase: MoviesUseCase) : ViewModel() {

    fun getMovies(sort: String): LiveData<Resource<List<Movies>>> =
        movieUseCase.getAllMovies(sort).asLiveData()
}