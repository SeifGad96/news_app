package com.training.whatsthenews

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_news")
data class FavoriteNewsEntity(
    @PrimaryKey val title: String,
    val uniformResourceLocator: String,
    val urlToImage: String
)