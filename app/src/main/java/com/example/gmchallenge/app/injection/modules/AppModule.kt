@file:Suppress("EXPERIMENTAL_API_USAGE")

package com.example.gmchallenge.app.injection.modules

import com.example.gmchallenge.data.network.NetworkArtistFetch
import com.example.gmchallenge.presentation.features.ArtistViewModel
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
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
            .baseUrl("https://itunes.apple.com")
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .build()
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
    fun providesIoDispatcher(): CoroutineDispatcher = Dispatchers.IO

    @Provides
    @Singleton
    fun provideNetworkArtistFetch(
        ioDispatcher: CoroutineDispatcher,
        retrofit: Retrofit
    ): NetworkArtistFetch {
        return NetworkArtistFetch(ioDispatcher, retrofit)
    }

    @Provides
    @Singleton
    fun provideArtistViewModel(repo: NetworkArtistFetch): ArtistViewModel {
        return ArtistViewModel(repo)
    }


}