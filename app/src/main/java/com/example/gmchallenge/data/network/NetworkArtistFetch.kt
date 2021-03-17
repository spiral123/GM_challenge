package com.example.gmchallenge.data.network

import com.example.gmchallenge.data.models.ArtistData
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Query

interface artistSearch {

    @GET("search")
    fun getArtistData(@Query("term") artistName: String): Call<ArtistData>

}

class NetworkArtistFetch(
    private val ioDispatcher: CoroutineDispatcher,
    private val retrofit: Retrofit
) {
    suspend fun fetchArtistData() {
        withContext(ioDispatcher) {

        }
    }


}