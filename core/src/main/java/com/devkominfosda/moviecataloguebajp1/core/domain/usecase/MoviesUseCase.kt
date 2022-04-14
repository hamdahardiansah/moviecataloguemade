package com.devkominfosda.moviecataloguebajp1.core.domain.usecase

import com.devkominfosda.moviecataloguebajp1.core.data.source.Resource
import com.devkominfosda.moviecataloguebajp1.core.domain.model.Movies
import kotlinx.coroutines.flow.Flow

interface MoviesUseCase {

    fun getAllMovies(sort: String): Flow<Resource<List<Movies>>>

    fun getFavoriteMovies(): Flow<List<Movies>>

    fun getSearchMovies(title: String): Flow<List<Movies>>

    fun setMovieFavorite(movie: Movies, state: Boolean)
}