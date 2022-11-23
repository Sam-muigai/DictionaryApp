package com.example.dictionaryapp.di

import com.example.dictionaryapp.data.network.DictionaryApi
import com.example.dictionaryapp.data.repository.DictionaryRepository
import com.example.dictionaryapp.utils.Constants
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

    @Singleton
    @Provides
    fun provideDictionaryApi(): DictionaryApi =
        Retrofit.Builder().baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()).build()
            .create(DictionaryApi::class.java)

    @Singleton
    @Provides
    fun provideDictionaryRepository(api: DictionaryApi):
            DictionaryRepository = DictionaryRepository(api = api)


}