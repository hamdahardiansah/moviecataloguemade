package com.devkominfosda.moviecataloguebajp1.core.data.source

import com.devkominfosda.moviecataloguebajp1.core.data.source.local.LocalDataSource
import com.devkominfosda.moviecataloguebajp1.core.data.source.remote.ApiResponse
import com.devkominfosda.moviecataloguebajp1.core.data.source.remote.RemoteDataSource
import com.devkominfosda.moviecataloguebajp1.core.data.source.remote.response.ResponseMovie
import com.devkominfosda.moviecataloguebajp1.core.domain.model.Movies
import com.devkominfosda.moviecataloguebajp1.core.domain.repository.IMoviesRepository
import com.devkominfosda.moviecataloguebajp1.core.utils.AppExecutors
import com.devkominfosda.moviecataloguebajp1.core.utils.DataMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class MoviesRepository constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val appExecutors: AppExecutors
) : IMoviesRepository {

    override fun getAllMovies(sort: String): Flow<Resource<List<Movies>>> =
        object : NetworkBoundResource<List<Movies>, List<ResponseMovie>>() {
            override fun loadFromDB(): Flow<List<Movies>> {
                return localDataSource.getAllMovies(sort).map {
                    DataMapper.mapEntitiesToDomain(it)
                }
            }

            override fun shouldFetch(data: List<Movies>?): Boolean {
                return data == null || data.isEmpty()
            }


            override suspend fun createCall(): Flow<ApiResponse<List<ResponseMovie>>> {
                return remoteDataSource.getMovies()
            }

            override suspend fun saveCallResult(data: List<ResponseMovie>) {
                val movieList = DataMapper.mapMovieResponsesToEntities(data)
                localDataSource.insertMovies(movieList)
            }
        }.asLiveData()

    override fun getFavoriteMovies(): Flow<List<Movies>> {
        return localDataSource.getAllFavoriteMovies().map {
            DataMapper.mapEntitiesToDomain(it)
        }
    }

    override fun getSearchMovies(title: String): Flow<List<Movies>> {
        return localDataSource.getSearchMovies(title).map {
            DataMapper.mapEntitiesToDomain(it)
        }
    }

    override fun setMoviesFavorite(movie: Movies, state: Boolean) {
        val movieEntity = DataMapper.mapDomainToEntities(movie)
        appExecutors.diskIO().execute {
            localDataSource.setMoviesFavorite(movieEntity, state)
        }
    }
}