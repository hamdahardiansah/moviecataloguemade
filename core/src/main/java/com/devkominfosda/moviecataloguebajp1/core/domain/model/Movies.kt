package com.devkominfosda.moviecataloguebajp1.core.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Movies(
    val id: Int,
    val title: String,
    val overview: String,
    val releaseDate: String,
    val originalLanguage: String,
    val popularity: Double,
    val voteAverage: Double,
    val posterPath: String,
    val backdropPath: String?,
    val voteCount: Int,
    var isFavorite: Boolean,
    var isTvShow: Boolean
) : Parcelable