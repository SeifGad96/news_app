package com.training.whatsthenews

import com.google.gson.annotations.SerializedName

data class News(
    val articles: ArrayList<Article>
)

data class Article(
    val title: String,
    @SerializedName("url")
    val uniformResourceLocator: String,
    val urlToImage: String
)
