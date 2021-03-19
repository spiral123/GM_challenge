package com.example.utils

import com.example.gmchallenge.data.network.ArtistSearchApi
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockWebServer
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


class TestRetrofit(private val mockWebServer: MockWebServer) {

    val artistSearchApiFake: ArtistSearchApi

    private val client = OkHttpClient.Builder()
        .connectTimeout(1, TimeUnit.SECONDS)
        .readTimeout(1, TimeUnit.SECONDS)
        .writeTimeout(1, TimeUnit.SECONDS)
        .build()

    init {
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        artistSearchApiFake = retrofit.create(ArtistSearchApi::class.java)
    }


}