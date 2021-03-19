@file:Suppress("EXPERIMENTAL_API_USAGE")

package com.example.gmchallenge.app.injection.modules

import com.example.gmchallenge.BuildConfig
import com.example.gmchallenge.data.BaseUrl
import com.example.gmchallenge.data.network.ArtistSearchApi
import com.example.gmchallenge.data.network.NetworkArtistFetch
import com.example.gmchallenge.presentation.features.ArtistViewModel
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.*
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
internal class AppModule {

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BaseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        val builder = OkHttpClient.Builder()
        if (BuildConfig.DEBUG) {
            builder.addInterceptor(httpLoggingInterceptor)
        }
        return builder.build()
    }

    @Provides
    @Singleton
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        return httpLoggingInterceptor
    }

    @Provides
    @Singleton
    fun providesCoroutineScope(): CoroutineScope {
        return CoroutineScope(SupervisorJob() + Dispatchers.IO)
    }

    @Provides
    @Singleton
    fun provideNetworkArtistFetch(
        ioDispatcher: CoroutineScope,
        retrofit: Retrofit
    ): NetworkArtistFetch {
        val artistSearchApi = retrofit.create(ArtistSearchApi::class.java)
        return NetworkArtistFetch(artistSearchApi)
    }

    @Provides
    @Singleton
    fun provideArtistViewModel(
        scope: CoroutineScope,
        network: NetworkArtistFetch
    ): ArtistViewModel {
        return ArtistViewModel(scope, network)
    }


}