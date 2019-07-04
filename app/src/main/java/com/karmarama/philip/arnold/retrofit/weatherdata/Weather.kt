package com.karmarama.philip.arnold.retrofit.weatherdata

data class Weather(
    val description: String,
    val icon: String,
    val id: Int,
    val main: String
)