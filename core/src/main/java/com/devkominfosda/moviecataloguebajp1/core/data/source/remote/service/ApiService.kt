package com.devkominfosda.moviecataloguebajp1.core.data.source.remote.service

import com.devkominfosda.moviecataloguebajp1.core.BuildConfig
import com.devkominfosda.moviecataloguebajp1.core.data.source.remote.response.ListMovieResponse
import retrofit2.http.GET
import retrofit2.http.Query


interface ApiService {

    @GET("movie")
    suspend fun getMovies(
        @Query("api_key") apiKey: String = BuildConfig.API_KEY
    ): ListMovieResponse
}
