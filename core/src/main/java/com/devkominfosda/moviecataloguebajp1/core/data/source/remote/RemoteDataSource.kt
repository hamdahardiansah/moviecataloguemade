package com.devkominfosda.moviecataloguebajp1.core.data.source.remote

import android.content.ContentValues
import android.util.Log
import com.devkominfosda.moviecataloguebajp1.core.BuildConfig
import com.devkominfosda.moviecataloguebajp1.core.data.source.remote.response.*
import com.devkominfosda.moviecataloguebajp1.core.data.source.remote.service.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class RemoteDataSource(private val apiService: ApiService) {

    private val apiKey = BuildConfig.API_KEY

    suspend fun getMovies(): Flow<ApiResponse<List<ResponseMovie>>> {
        return flow {
            try {
                val response = apiService.getMovies(apiKey)
                val movieList = response.results
                if (movieList.isNotEmpty()) {
                    emit(ApiResponse.Success(response.results))
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
                Log.e(ContentValues.TAG, "getMovies: $e")
            }
        }.flowOn(Dispatchers.IO)
    }
}