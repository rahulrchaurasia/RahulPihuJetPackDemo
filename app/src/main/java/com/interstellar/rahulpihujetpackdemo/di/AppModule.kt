package com.interstellar.rahulpihujetpackdemo.di

import com.interstellar.rahulpihujetpackdemo.data.repository.AuthRepositoryImpl
import com.interstellar.rahulpihujetpackdemo.domain.repository.AuthRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {

    @Binds
    @Singleton
    abstract fun bindAuthRepository(
        impl: AuthRepositoryImpl
    ): AuthRepository
}