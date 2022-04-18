package com.example.myshop.di

import com.example.myshop.common.Constants
import com.example.myshop.data.api.ApiCurrency
import com.example.myshop.data.repository.*
import com.example.myshop.domain.repository.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
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
    fun provideProductRepository(fireStore: FirebaseFirestore): ProductsRepository = ProductsRepositoryImp(fireStore)

    @Provides
    @Singleton
    fun provideCheckRepository(fireStore: FirebaseFirestore): CheckUsersRepository = CheckUsersRepositoryImp(fireStore)

    @Provides
    @Singleton
    fun provideUpdateRepository(fireStore: FirebaseFirestore): UpdateRepository = UpdateRepositoryImp(fireStore)

    @Provides
    @Singleton
    fun provideAuthentication(firebaseAuth: FirebaseAuth, fireStore: FirebaseFirestore): AuthenticationRepository = AuthenticationRepositoryIml(firebaseAuth, fireStore)

    @Provides
    @Singleton
    fun provideLoadImageToCloudStorageRepository(): LoadImageToCloudStorageRepository = LoadImageToCloudStorageRepositoryImp()

    @Provides
    @Singleton
    fun provideAddressUserRepository(fireStore: FirebaseFirestore): AddressUserRepository = AddressUserRepositoryImp(fireStore)

    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth = FirebaseAuth.getInstance()

    @Provides
    @Singleton
    fun provideFireStore(): FirebaseFirestore = FirebaseFirestore.getInstance()
}