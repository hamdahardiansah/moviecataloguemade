package com.devkominfosda.moviecataloguebajp1.core.data.source.local

import com.devkominfosda.moviecataloguebajp1.core.data.source.local.entity.MoviesEntity
import com.devkominfosda.moviecataloguebajp1.core.data.source.local.room.MoviesDao
import com.devkominfosda.moviecataloguebajp1.core.utils.Sort
import kotlinx.coroutines.flow.Flow

class LocalDataSource(private val mMoviesDao: MoviesDao) {


    fun getAllMovies(sort: String): Flow<List<MoviesEntity>> {
        val query = Sort.getSortedQueryMovies(sort)
        return mMoviesDao.getAllMovies(query)
    }

    fun getAllFavoriteMovies(): Flow<List<MoviesEntity>> {
        return mMoviesDao.getFavoriteMovies()
    }

    fun setMoviesFavorite(movie: MoviesEntity, state: Boolean) {
        movie.isFavorite = state
        mMoviesDao.updateDataMovies(movie)
    }

    fun getSearchMovies(title: String): Flow<List<MoviesEntity>> {
        return mMoviesDao.getSearchResult(title)
    }

    suspend fun insertMovies(movie: List<MoviesEntity>) = mMoviesDao.insertDataMovies(movie)
}