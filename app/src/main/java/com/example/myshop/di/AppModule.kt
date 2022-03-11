package com.example.myshop.di

import com.example.myshop.data.repository.CheckProductsRepositoryImp
import com.example.myshop.data.repository.ProductsRepositoryImp
import com.example.myshop.data.repository.UpdateRepositoryImp
import com.example.myshop.domain.repository.CheckProductsRepository
import com.example.myshop.domain.repository.ProductsRepository
import com.example.myshop.domain.repository.UpdateRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideProductRepository(): ProductsRepository = ProductsRepositoryImp()

    @Provides
    @Singleton
    fun provideCheckRepository(): CheckProductsRepository = CheckProductsRepositoryImp()

    @Provides
    @Singleton
    fun provideUpdateRepository(): UpdateRepository = UpdateRepositoryImp()
}