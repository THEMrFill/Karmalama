package com.karmarama.philip.arnold.retrofit.citydata

data class GeoLookup(
    val results: List<Result>,
    val status: String
)