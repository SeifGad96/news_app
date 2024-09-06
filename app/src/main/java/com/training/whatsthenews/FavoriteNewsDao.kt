package com.training.whatsthenews

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface FavoriteNewsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavoriteNews(favoriteNews: List<FavoriteNewsEntity>)

    @Delete
    suspend fun deleteFavoriteNews(favoriteNews: List<FavoriteNewsEntity>)

    @Query("SELECT * FROM favorite_news")
    fun getFavoriteNews(): LiveData<List<FavoriteNewsEntity>>

}