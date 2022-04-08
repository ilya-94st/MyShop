package com.example.myshop.domain.use_case

import com.example.myshop.common.Resource
import com.example.myshop.domain.models.response.CurrencyItem
import com.example.myshop.domain.models.response.CurrencyRates
import com.example.myshop.domain.repository.GetApiCurrencyRepository
import javax.inject.Inject

class GetCurrencyFromApi @Inject constructor(private val getApiCurrencyRepository: GetApiCurrencyRepository) {

    suspend operator fun invoke(): Resource<CurrencyRates> {
        return try {
            val response = getApiCurrencyRepository.getCurrency()
            val result = response.body()
            if(response.isSuccessful && result != null) {
                Resource.Success(result)
            } else {
                Resource.Error(response.message())
            }
        } catch(e: Exception) {
            Resource.Error(e.message ?: "An error occured")
        }
    }
}