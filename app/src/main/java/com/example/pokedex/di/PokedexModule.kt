package com.example.pokedex.di

import com.example.pokedex.data.remote.service.PokedexApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object PokedexModule {

    private const val USER_BASE_URL = "https://pokeapi.co/"

    @Provides
    fun providePokedexBaseUrl() = "${USER_BASE_URL}api/v2/"

    @Provides
    fun providesHttpClient(): OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(
            HttpLoggingInterceptor()
                .setLevel(HttpLoggingInterceptor.Level.BODY)
        )
        .build()

    @Provides
    fun provideRetrofitClient(
        baseUrl: String,
        okHttpClient: OkHttpClient
    ): Retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Provides
    fun providePokedexApiService(retrofit: Retrofit): PokedexApiService =
        retrofit.create(PokedexApiService::class.java)
}