package com.devkominfosda.moviecataloguebajp1.core.domain.repository

import com.devkominfosda.moviecataloguebajp1.core.data.source.Resource
import com.devkominfosda.moviecataloguebajp1.core.domain.model.Movies
import kotlinx.coroutines.flow.Flow

interface IMoviesRepository {

    fun getAllMovies(sort: String): Flow<Resource<List<Movies>>>

    fun getFavoriteMovies(): Flow<List<Movies>>

    fun getSearchMovies(title: String): Flow<List<Movies>>

    fun setMoviesFavorite(movie: Movies, state: Boolean)
}