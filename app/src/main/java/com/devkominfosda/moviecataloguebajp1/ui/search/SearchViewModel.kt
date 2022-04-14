package com.devkominfosda.moviecataloguebajp1.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.devkominfosda.moviecataloguebajp1.core.domain.model.Movies
import com.devkominfosda.moviecataloguebajp1.core.domain.usecase.MoviesUseCase

class SearchViewModel(private val movieUseCase: MoviesUseCase) : ViewModel() {

    fun searchForItems(title: String) : LiveData<List<Movies>> {
        return movieUseCase.getSearchMovies(title).asLiveData()
    }
}