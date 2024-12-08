package com.healthcard.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.healthcard.data.local.database.MedicineDatabase
import com.healthcard.data.remote.ApiService
import com.healthcard.data.repository.MedicineRepositoryImpl
import com.healthcard.domain.repository.MedicineRepository
import com.healthcard.util.SharedPrefManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideMedicineDatabase(app: Application): MedicineDatabase {
        return Room.databaseBuilder(
            app,
            MedicineDatabase::class.java,
            "medicine_db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideOkHttpClient():OkHttpClient{
        return OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY // Set the desired logging level
            })
            .build()
    }
    @Provides
    @Singleton
    fun provideApiService(okHttpClient: OkHttpClient): ApiService {
        return Retrofit.Builder()
            .baseUrl("https://example.com/api/") // Replace with actual API base URL
            .client(okHttpClient) // Add the client with the interceptor
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideMedicineRepository(
        apiService: ApiService,
        database: MedicineDatabase
    ): MedicineRepository {
        return MedicineRepositoryImpl(apiService, database.medicineDao())
    }

    @Provides
    @Singleton
    fun provideSharedPrefManager(@ApplicationContext context: Context): SharedPrefManager {
        return SharedPrefManager(context)
    }


}