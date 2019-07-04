package com.karmarama.philip.arnold.retrofit

import com.karmarama.philip.arnold.BuildConfig
import com.karmarama.philip.arnold.retrofit.citydata.GeoLookup
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitLocationService {
    @GET("json?")
    suspend fun getCities(
                    @Query("address") city: String,
                    @Query("key") api_key: String = BuildConfig.MAP_KEY
                ): Response<GeoLookup>
}