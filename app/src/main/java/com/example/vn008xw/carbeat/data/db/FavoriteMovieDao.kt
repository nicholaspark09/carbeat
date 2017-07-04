package com.example.vn008xw.carbeat.data.db

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*
import com.example.vn008xw.carbeat.data.vo.FavoriteMovie

@Dao
abstract class FavoriteMovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertMovies(movies: List<FavoriteMovie>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insert(movie: FavoriteMovie)

    @Query("SELECT * FROM favoritemovie ORDER BY title")
    abstract fun loadFavoriteMovies(): LiveData<List<FavoriteMovie>>

    @Query("SELECT * FROM favoritemovie WHERE title = :title")
    abstract fun searchFavoriteMovies(title: String): LiveData<List<FavoriteMovie>>

    @Query("SELECT * FROM favoritemovie WHERE id = :id")
    abstract fun loadMovie(id: Int): LiveData<FavoriteMovie>

    @Delete
    abstract fun deleteMovie(favoriteMovie: FavoriteMovie)
}
