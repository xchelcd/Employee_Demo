package com.xchel.employeedemo.di

import com.xchel.employeedemo.BuildConfig
import com.xchel.employeedemo.data.network.EmployeeApiClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    private fun getUrl(): String = BuildConfig.Url

    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(getUrl())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }


    @Singleton
    @Provides
    fun provideEmployeeApiClient(retrofit: Retrofit): EmployeeApiClient {
        return retrofit.create(EmployeeApiClient::class.java)
    }


}