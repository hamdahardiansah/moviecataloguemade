package com.devkominfosda.moviecataloguebajp1.core.domain.usecase

import com.devkominfosda.moviecataloguebajp1.core.data.source.Resource
import com.devkominfosda.moviecataloguebajp1.core.domain.model.Movies
import com.devkominfosda.moviecataloguebajp1.core.domain.repository.IMoviesRepository
import kotlinx.coroutines.flow.Flow

class MoviesInteractor(private val iMoviesRepository: IMoviesRepository): MoviesUseCase {

    override fun getAllMovies(sort: String): Flow<Resource<List<Movies>>> {
        return iMoviesRepository.getAllMovies(sort)
    }

    override fun getFavoriteMovies(): Flow<List<Movies>> {
        return iMoviesRepository.getFavoriteMovies()
    }

    override fun getSearchMovies(title: String): Flow<List<Movies>> {
        return iMoviesRepository.getSearchMovies(title)
    }

    override fun setMovieFavorite(movie: Movies, state: Boolean) {
        return iMoviesRepository.setMoviesFavorite(movie, state)
    }
}