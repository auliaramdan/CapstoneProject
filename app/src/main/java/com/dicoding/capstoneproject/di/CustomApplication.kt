package com.dicoding.capstoneproject.di

import android.app.Application
import com.dicoding.capstoneproject.core.di.databaseModule
import com.dicoding.capstoneproject.core.di.networkModule
import com.dicoding.capstoneproject.core.di.repositoryModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class CustomApplication : Application()  {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.NONE)
            androidContext(this@CustomApplication)
            modules(
                listOf(
                    databaseModule,
                    networkModule,
                    repositoryModule,
                    viewModelModule,
                    useCaseModule
                )
            )
        }
    }
}