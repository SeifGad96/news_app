package com.training.whatsthenews

import retrofit2.Call
import retrofit2.http.GET

import retrofit2.http.Query


interface NewsCallable {
    @GET("news?apikey=08094a62d7d5454fa4fc051ae47ab52c")
    fun getNews(
        @Query("category") category: String,
        @Query("country") country: String,
    ): Call<News>


    //health
    @GET("/v2/everything?q=germany+AND+health&apiKey=08094a62d7d5454fa4fc051ae47ab52c")
    fun getHealthNewsGermany(): Call<News>

    @GET("/v2/everything?q=france+AND+health&apiKey=08094a62d7d5454fa4fc051ae47ab52c")
    fun getHealthNewsFrance(): Call<News>

    @GET("/v2/top-headlines?country=us&category=health&apiKey=08094a62d7d5454fa4fc051ae47ab52c")
    fun getHealthNewsUs(): Call<News>

    @GET("/v2/everything?q=england+AND+health&apiKey=08094a62d7d5454fa4fc051ae47ab52c")
    fun getHealthNewsEngland(): Call<News>


    //business
    @GET("/v2/everything?q=germany+AND+business&apiKey=08094a62d7d5454fa4fc051ae47ab52c")
    fun getBusinessNewsGermany(): Call<News>

    @GET("/v2/everything?q=france+AND+business&apiKey=08094a62d7d5454fa4fc051ae47ab52c")
    fun getBusinessNewsFrance(): Call<News>

    @GET("/v2/top-headlines?country=us&category=business&apiKey=08094a62d7d5454fa4fc051ae47ab52c")
    fun getBusinessNewsUs(): Call<News>

    //1
    @GET("/v2/everything?q=england+AND+business&apiKey=08094a62d7d5454fa4fc051ae47ab52c")
    fun getBusinessNewsEngland(): Call<News>


    //sports

    @GET("/v2/everything?q=germany+AND+sports&apiKey=08094a62d7d5454fa4fc051ae47ab52c")
    fun getSportNewsGermany(): Call<News>

    @GET("/v2/everything?q=france+AND+sports&apiKey=08094a62d7d5454fa4fc051ae47ab52c")
    fun getSportNewsFrance(): Call<News>

    @GET("/v2/top-headlines?country=us&category=sports&apiKey=08094a62d7d5454fa4fc051ae47ab52c")
    fun getSportNewsUs(): Call<News>

    @GET("/v2/everything?q=england+AND+sports&apiKey=08094a62d7d5454fa4fc051ae47ab52c")
    fun getSportNewsEngland(): Call<News>


    //technology
    @GET("/v2/everything?q=germany+AND+technology&apiKey=08094a62d7d5454fa4fc051ae47ab52c")
    fun getTechnoNewsGermany(): Call<News>

    @GET("/v2/everything?q=france+AND+technology&apiKey=08094a62d7d5454fa4fc051ae47ab52c")
    fun getTechnoNewsFrance(): Call<News>

    @GET("/v2/top-headlines?country=us&category=technology&apiKey=08094a62d7d5454fa4fc051ae47ab52c")
    fun getTechnoNewsUs(): Call<News>

    @GET("/v2/everything?q=england+AND+technology&apiKey=08094a62d7d5454fa4fc051ae47ab52c")
    fun getTechnoNewsEngland(): Call<News>



}

