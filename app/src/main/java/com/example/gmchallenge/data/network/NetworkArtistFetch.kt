package com.example.gmchallenge.data.network

import com.example.gmchallenge.data.models.ArtistData
import com.example.gmchallenge.data.models.ArtistDataState
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ArtistSearchApi {

    @GET("search")
    suspend fun getArtistData(@Query("term") artistName: String): Response<ArtistData>

}

class NetworkArtistFetch(private val artistSearchApi: ArtistSearchApi) {

    suspend fun fetchArtistData(artistName: String): ArtistDataState {
        return try {
            val response = artistSearchApi.getArtistData(artistName)
            if (response.isSuccessful) {
                response.body()?.results?.let { tracks ->
                    if (tracks.isEmpty()) {
                        ArtistDataState.Error(Exception("No tracks for $artistName"))
                    } else {
                        ArtistDataState.Success(tracks)
                    }
                } ?: ArtistDataState.Error(Exception("No tracks for $artistName"))
            } else {
                ArtistDataState.Error(Exception(response.raw().message))
            }
        } catch (e: Throwable) {
            ArtistDataState.Error(e)
        }
    }

}