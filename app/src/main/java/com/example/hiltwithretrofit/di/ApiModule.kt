package com.example.hiltwithretrofit.di

import com.example.hiltwithretrofit.BuildConfig
import com.example.hiltwithretrofit.api.ApiServices
import com.example.hiltwithretrofit.utils.Constants.API_KEY
import com.example.hiltwithretrofit.utils.Constants.BASE_URL
import com.example.hiltwithretrofit.utils.Constants.NETWORK_TIMEOUT
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    @Provides
    @Singleton
    fun provideBaseUrl() = BASE_URL

    @Provides
    @Singleton
    fun connectionTimeOut() = NETWORK_TIMEOUT

    @Provides
    @Singleton
    fun provideGson() : Gson = GsonBuilder().setLenient().create()

    @Provides
    @Singleton
    fun provideOKHttpClient() = if (BuildConfig.DEBUG) {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.HEADERS)
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

        val requestInterceptor = Interceptor {chain ->
            val url = chain.request()
                .url
                .newBuilder()
                .addQueryParameter("api_key", API_KEY)
                .build()

           val request = chain.request()
               .newBuilder()
               .url(url)
               .build()

            return@Interceptor chain.proceed(request)
        }

        OkHttpClient
            .Builder()
            .addInterceptor(requestInterceptor)
            .addInterceptor(loggingInterceptor)

    } else {
        OkHttpClient
            .Builder()
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(baseUrl: String, gson: Gson, client: OkHttpClient) : ApiServices =
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(ApiServices::class.java)
}