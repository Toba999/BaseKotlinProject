package com.example.androiddevelopertask.di

import android.content.Context
import android.content.SharedPreferences
import com.example.androiddevelopertask.local.HelperSharedPreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocalModule {
    @Provides
    @Singleton
    fun provideHelperSharedPreferences(@ApplicationContext context: Context): HelperSharedPreferences {
        return HelperSharedPreferences(context)
    }
}