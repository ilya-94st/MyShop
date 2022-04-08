package com.example.myshop.di

import com.example.myshop.common.Constants
import com.example.myshop.data.api.ApiCurrency
import com.example.myshop.data.repository.*
import com.example.myshop.domain.repository.*
import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideCurrencyApi(): ApiCurrency = Retrofit.Builder()
        .baseUrl(Constants.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(ApiCurrency::class.java)

    @Provides
    @Singleton
    fun provideApiRepository(apiCurrency: ApiCurrency): GetApiCurrencyRepository = GetApiCurrencyRepositoryImp(apiCurrency)

    @Provides
    @Singleton
    fun provideProductRepository(): ProductsRepository = ProductsRepositoryImp()

    @Provides
    @Singleton
    fun provideCheckRepository(): CheckUsersRepository = CheckUsersRepositoryImp()

    @Provides
    @Singleton
    fun provideUpdateRepository(): UpdateRepository = UpdateRepositoryImp()

    @Provides
    @Singleton
    fun provideAuthentication(firebaseAuth: FirebaseAuth): AuthenticationRepository = AuthenticationRepositoryIml(firebaseAuth)

    @Provides
    @Singleton
    fun provideLoadImageToCloudStorageRepository(): LoadImageToCloudStorageRepository = LoadImageToCloudStorageRepositoryImp()

    @Provides
    @Singleton
    fun provideAddressUserRepository(): AddressUserRepository = AddressUserRepositoryImp()

    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth = FirebaseAuth.getInstance()


}