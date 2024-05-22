package com.example.githubapp.db

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface UserFavoriteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertToFavorite(user: UserFavorite)

    @Query("DELETE FROM UserFavorite WHERE UserFavorite.id = :id")
    fun removeFromFavorite(id: Int)

    @Query("SELECT * FROM UserFavorite ORDER BY login ASC")
    fun getAll(): LiveData<List<UserFavorite>>

    @Query("SELECT * FROM UserFavorite WHERE UserFavorite.id = :id")
    fun getById(id: Int): LiveData<UserFavorite>
}