package com.example.omdbapi.di

import android.content.Context
import androidx.room.Room
import com.example.omdbapi.common.Constants
import com.example.omdbapi.data.local.db.AppDatabase
import com.example.omdbapi.data.remote.api.OmdbApi
import com.example.omdbapi.data.repository.OmdbRepositoryImpl
import com.example.omdbapi.domain.repository.OmdbRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)

object AppModule {
    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): AppDatabase {
        return Room.databaseBuilder(
            appContext,
            AppDatabase::class.java,
            AppDatabase.NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideOmdbApi(): OmdbApi {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(OmdbApi::class.java)
    }

    @Provides
    @Singleton
    fun provideRepository(api: OmdbApi,db:AppDatabase): OmdbRepository {
        return OmdbRepositoryImpl(api, db)
    }
}