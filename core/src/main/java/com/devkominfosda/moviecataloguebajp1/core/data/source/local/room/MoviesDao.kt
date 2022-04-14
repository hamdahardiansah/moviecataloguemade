package com.devkominfosda.moviecataloguebajp1.core.data.source.local.room

import androidx.room.*
import androidx.sqlite.db.SupportSQLiteQuery
import com.devkominfosda.moviecataloguebajp1.core.data.source.local.entity.MoviesEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MoviesDao {

    @RawQuery(observedEntities = [MoviesEntity::class])
    fun getAllMovies(query: SupportSQLiteQuery): Flow<List<MoviesEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDataMovies(movies: List<MoviesEntity>)

    @Update
    fun updateDataMovies(movies: MoviesEntity)

    @Query("SELECT * FROM movie_entities WHERE isFavorite = 1")
    fun getFavoriteMovies(): Flow<List<MoviesEntity>>

    @Query("SELECT * FROM movie_entities where title like :title")
    fun getSearchResult(title: String): Flow<List<MoviesEntity>>

}