package com.example.myshop.domain.repository


import com.example.myshop.domain.models.response.CurrencyRates
import retrofit2.Response

interface GetApiCurrencyRepository {
    suspend fun getCurrency(): Response<CurrencyRates>
}