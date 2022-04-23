package com.dicoding.capstoneproject.core.di

import androidx.room.Room
import com.dicoding.capstoneproject.core.BuildConfig
import com.dicoding.capstoneproject.core.data.Repository
import com.dicoding.capstoneproject.core.domain.repository.IMediaRepository
import com.dicoding.capstoneproject.core.data.local.LocalDataSource
import com.dicoding.capstoneproject.core.data.local.room.MediaDatabase
import com.dicoding.capstoneproject.core.data.remote.ApiService
import com.dicoding.capstoneproject.core.data.remote.RemoteDataSource
import com.dicoding.capstoneproject.core.utility.ExecutorsPool
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SupportFactory
import okhttp3.CertificatePinner
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val databaseModule = module {
    factory { get<MediaDatabase>().mediaDao() }
    single {
        val pass: ByteArray = SQLiteDatabase.getBytes("capstone".toCharArray())
        val factory = SupportFactory(pass)
        Room.databaseBuilder(
            androidContext(),
            MediaDatabase::class.java,
            "Media.db"
        ).fallbackToDestructiveMigration()
            .openHelperFactory(factory)
            .build()
    }
}

val networkModule = module {
    single {
        val hostname = BuildConfig.HOSTNAME
        val certificatePinner = CertificatePinner.Builder()
            .add(hostname, "sha256/oD/WAoRPvbez1Y2dfYfuo4yujAcYHXdv1Ivb2v2MOKk=")
            .add(hostname, "sha256/JSMzqOOrtyOT1kmau6zKhgT676hGgczD5VMdRMyJZFA=")
            .add(hostname, "sha256/++MBgDH5WGvL9Bcn5Be30cRcL0f5O+NyoXuWtQdX1aI=")
            .add(hostname, "sha256/KwccWaCgrnaw6tsrrSO61FgLacNgG2MMLq8GE6+oP5I=")
            .build()


        OkHttpClient.Builder()
            .addInterceptor(Interceptor { chain ->
                val request = chain.request().newBuilder().addHeader("Authorization", BuildConfig.API_KEY).build()
                chain.proceed(request)
            })
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .connectTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .certificatePinner(certificatePinner)
            .build()
    }
    single {

        val retrofit = Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(get())
            .build()

        retrofit.create(ApiService::class.java)
    }
}

val repositoryModule = module {
    single { LocalDataSource(get()) }
    single { RemoteDataSource(get()) }
    factory { ExecutorsPool() }
    single<IMediaRepository> {
        Repository(
            get(),
            get(),
            get()
        )
    }
}