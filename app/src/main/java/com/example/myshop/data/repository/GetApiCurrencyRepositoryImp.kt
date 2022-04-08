package com.example.myshop.data.repository


import com.example.myshop.data.api.ApiCurrency
import com.example.myshop.domain.models.response.CurrencyItem
import com.example.myshop.domain.models.response.CurrencyRates
import com.example.myshop.domain.repository.GetApiCurrencyRepository
import retrofit2.Response
import javax.inject.Inject

class GetApiCurrencyRepositoryImp @Inject constructor(private val apiCurrency: ApiCurrency): GetApiCurrencyRepository {
    override suspend fun getCurrency(): Response<CurrencyRates> {
        return apiCurrency.getCurrency()
    }


}