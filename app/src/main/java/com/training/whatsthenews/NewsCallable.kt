package com.training.whatsthenews

import retrofit2.Call
import retrofit2.http.GET



interface NewsCallable {


    //health
    @GET("/v2/top-headlines?country=eg&category=health&apiKey=08094a62d7d5454fa4fc051ae47ab52c")
    fun getHealthNewsegypt(): Call<News>

    @GET("/v2/top-headlines?country=de&category=health&apiKey=08094a62d7d5454fa4fc051ae47ab52c")
    fun getHealthNewsjermany(): Call<News>

    @GET("/v2/top-headlines?country=fr&category=health&apiKey=08094a62d7d5454fa4fc051ae47ab52c")
    fun getHealthNewsfrance(): Call<News>

    @GET("/v2/top-headlines?country=us&category=health&apiKey=08094a62d7d5454fa4fc051ae47ab52c")
    fun getHealthNewsus(): Call<News>

    @GET("/v2/top-headlines?country=gb&category=health&apiKey=08094a62d7d5454fa4fc051ae47ab52c")
    fun getHealthNewsengland(): Call<News>


    //business
    @GET("/v2/top-headlines?country=eg&category=business&apiKey=08094a62d7d5454fa4fc051ae47ab52c")
    fun getBusinessNewsegypt(): Call<News>

    @GET("/v2/top-headlines?country=de&category=business&apiKey=08094a62d7d5454fa4fc051ae47ab52c")
    fun getBusinessNewsjermany(): Call<News>

    @GET("/v2/top-headlines?country=fr&category=business&apiKey=08094a62d7d5454fa4fc051ae47ab52c")
    fun getBusinessNewsfrance(): Call<News>

    @GET("/v2/top-headlines?country=us&category=business&apiKey=08094a62d7d5454fa4fc051ae47ab52c")
    fun getBusinessNewsus(): Call<News>

    @GET("/v2/top-headlines?country=gb&category=business&apiKey=08094a62d7d5454fa4fc051ae47ab52c")
    fun getBusinessNewsengland(): Call<News>


    //sports
    @GET("/v2/top-headlines?country=eg&category=sports&apiKey=08094a62d7d5454fa4fc051ae47ab52c")
    fun getSportNewsegypt(): Call<News>

    @GET("/v2/top-headlines?country=de&category=sports&apiKey=08094a62d7d5454fa4fc051ae47ab52c")
    fun getSportNewsjermany(): Call<News>

    @GET("/v2/top-headlines?country=fr&category=sports&apiKey=08094a62d7d5454fa4fc051ae47ab52c")
    fun getSportNewsfrance(): Call<News>

    @GET("/v2/top-headlines?country=us&category=sports&apiKey=08094a62d7d5454fa4fc051ae47ab52c")
    fun getSportNewsus(): Call<News>

    @GET("/v2/top-headlines?country=gb&category=sports&apiKey=08094a62d7d5454fa4fc051ae47ab52c")
    fun getSportNewsengland(): Call<News>


    //technology
    @GET("/v2/top-headlines?country=eg&category=technology&apiKey=08094a62d7d5454fa4fc051ae47ab52c")
    fun getTechnoNewsegypt(): Call<News>

    @GET("/v2/top-headlines?country=de&category=technology&apiKey=08094a62d7d5454fa4fc051ae47ab52c")
    fun getTechnoNewsjermany(): Call<News>

    @GET("/v2/top-headlines?country=fr&category=technology&apiKey=08094a62d7d5454fa4fc051ae47ab52c")
    fun getTechnoNewsfrance(): Call<News>

    @GET("/v2/top-headlines?country=us&category=technology&apiKey=08094a62d7d5454fa4fc051ae47ab52c")
    fun getTechnoNewsus(): Call<News>

    @GET("/v2/top-headlines?country=gb&category=technology&apiKey=08094a62d7d5454fa4fc051ae47ab52c")
    fun getTechnoNewsengland(): Call<News>



}