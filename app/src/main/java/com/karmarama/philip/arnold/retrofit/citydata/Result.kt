package com.karmarama.philip.arnold.retrofit.citydata

data class Result(
    val address_components: ArrayList<AddressComponent>,
    val formatted_address: String,
    val geometry: Geometry,
    val place_id: String,
    val types: ArrayList<String>
)