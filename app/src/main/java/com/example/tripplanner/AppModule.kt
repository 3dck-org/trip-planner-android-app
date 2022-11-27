package com.example.tripplanner

import android.content.Context
import com.example.tripplanner.constants.Constants.BASE_URL
import com.example.tripplanner.extensions.ExternalUserData
import com.example.tripplanner.sharedpreferences.EncryptedSharedPreferences
import com.example.tripplanner.sharedpreferences.UserContainer
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
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

    @Singleton
    @Provides
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        return interceptor
    }

    @Singleton
    @Provides
    fun provideToHttpClient(interceptor: HttpLoggingInterceptor): OkHttpClient {
        val builder = OkHttpClient.Builder()
        if (BuildConfig.DEBUG) {
            builder.addInterceptor(interceptor)
        }
        return builder.build()
    }

    @Singleton
    @Provides
    fun provideApi(): TripPlannerAPI =
//        SafeMock()
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(provideToHttpClient(provideLoggingInterceptor()))
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build()
            .create(TripPlannerAPI::class.java)

    @Singleton
    @Provides
    fun provideSharedPref(@ApplicationContext appContext: Context): EncryptedSharedPreferences {
        return EncryptedSharedPreferences(appContext)
    }

    @Singleton
    @Provides
    fun provideKidContainer(@ApplicationContext context: Context): UserContainer {
        return UserContainer(provideSharedPref(context))
    }
}
