package com.example.timetrackerapp.api


import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

const val baseUrl = "https://api.quotable.io/"


interface NewsInterface {

    @GET("random")
    fun getHeadlines(): Call<QuotesData>

}


object quoteObject {
    val news: NewsInterface

    init {
        val retrofit =
            Retrofit.Builder().baseUrl(baseUrl).addConverterFactory(GsonConverterFactory.create())
                .build()
        news = retrofit.create(NewsInterface::class.java)
    }


}