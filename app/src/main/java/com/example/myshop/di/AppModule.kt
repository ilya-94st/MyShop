package com.example.myshop.di

import com.example.myshop.data.repository.AuthenticationRepositoryIml
import com.example.myshop.data.repository.CheckUsersRepositoryImp
import com.example.myshop.data.repository.ProductsRepositoryImp
import com.example.myshop.data.repository.UpdateRepositoryImp
import com.example.myshop.domain.repository.AuthenticationRepository
import com.example.myshop.domain.repository.CheckUsersRepository
import com.example.myshop.domain.repository.ProductsRepository
import com.example.myshop.domain.repository.UpdateRepository
import com.google.firebase.auth.FirebaseAuth
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
    fun provideCheckRepository(): CheckUsersRepository = CheckUsersRepositoryImp()

    @Provides
    @Singleton
    fun provideUpdateRepository(): UpdateRepository = UpdateRepositoryImp()

    @Provides
    @Singleton
    fun provideAuthentication(firebaseAuth: FirebaseAuth): AuthenticationRepository = AuthenticationRepositoryIml(firebaseAuth)

    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth = FirebaseAuth.getInstance()
}