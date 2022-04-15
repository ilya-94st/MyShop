package com.example.myshop.data.api

import com.example.myshop.domain.models.response.CurrencyRates
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiCurrency {

    @GET("api/exrates/rates")
    suspend fun getCurrency(
        @Query("periodicity")
        newsPage: Int = 0
    ): Response<CurrencyRates>
}