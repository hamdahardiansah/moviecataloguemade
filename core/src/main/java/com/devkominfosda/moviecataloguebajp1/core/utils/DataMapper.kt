package com.devkominfosda.moviecataloguebajp1.core.utils

import com.devkominfosda.moviecataloguebajp1.core.data.source.local.entity.MoviesEntity
import com.devkominfosda.moviecataloguebajp1.core.data.source.remote.response.ResponseMovie
import com.devkominfosda.moviecataloguebajp1.core.domain.model.Movies

object DataMapper {

    fun mapMovieResponsesToEntities(input: List<ResponseMovie>): List<MoviesEntity> {
        val movieList = ArrayList<MoviesEntity>()
        input.map {
            val movie = MoviesEntity(
                id = it.id,
                title = it.title,
                overview = it.overview,
                releaseDate = it.releaseDate,
                originalLanguage = it.originalLanguage,
                popularity = it.popularity,
                voteAverage = it.voteAverage,
                posterPath = it.posterPath,
                backdropPath = it.backdropPath,
                voteCount = it.voteCount,
                isFavorite = false,
                isTvShow = false
            )
            movieList.add(movie)
        }
        return movieList
    }

    fun mapEntitiesToDomain(input: List<MoviesEntity>): List<Movies> =
        input.map {
            Movies(
                id = it.id,
                title = it.title,
                overview = it.overview,
                releaseDate = it.releaseDate,
                originalLanguage = it.originalLanguage,
                popularity = it.popularity,
                voteAverage = it.voteAverage,
                posterPath = it.posterPath,
                backdropPath = it.backdropPath,
                voteCount = it.voteCount,
                isFavorite = it.isFavorite,
                isTvShow = it.isTvShow
            )
        }

    fun mapDomainToEntities(input: Movies): MoviesEntity {
        return MoviesEntity(
            input.id,
            input.title,
            input.overview,
            input.releaseDate,
            input.originalLanguage,
            input.popularity,
            input.voteAverage,
            input.posterPath,
            input.backdropPath,
            input.voteCount,
            input.isFavorite,
            input.isTvShow
        )
    }
}
