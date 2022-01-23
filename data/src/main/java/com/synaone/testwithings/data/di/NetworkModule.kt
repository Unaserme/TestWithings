package com.synaone.testwithings.data.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.synaone.testwithings.data.service.NetworkService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class NetworkModule {

    /** JSON parsing rules. */
    private val json = Json {
        ignoreUnknownKeys = true
        coerceInputValues = true
    }

    @Provides
    @Singleton
    fun provideOkHttpClientBuilder(): OkHttpClient.Builder {
        return OkHttpClient.Builder()
            .readTimeout(30, TimeUnit.SECONDS)
            .connectTimeout(10, TimeUnit.SECONDS)
            .addInterceptor(
                Interceptor { chain: Interceptor.Chain ->
                    var request = chain.request()

                    val updateUrl = request.url.newBuilder()
                        .setQueryParameter("key", "18021445-326cf5bcd3658777a9d22df6f")
                        .build()

                    request = request.newBuilder()
                        .url(updateUrl)
                        .build()

                    chain.proceed(request)
                }
            )
    }

    @Provides
    fun provideNetwork(okHttpClientBuilder: OkHttpClient.Builder): NetworkService {

        val client = okHttpClientBuilder.build()
        val retrofit = Retrofit.Builder()
            .baseUrl("https://pixabay.com")
            .client(client)
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .build()

        return retrofit.create(NetworkService::class.java)
    }
}
