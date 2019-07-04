package com.karmarama.philip.arnold.retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitFactory {
    val cityBaseUrl = "https://maps.googleapis.com/maps/api/geocode/"
    val cityRetrofit by lazy {
        Retrofit.Builder()
            .baseUrl(cityBaseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val weatherBaseUrl = "https://api.openweathermap.org/data/2.5/"
    val weatherRetrofit by lazy {
        Retrofit.Builder()
            .baseUrl(weatherBaseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}