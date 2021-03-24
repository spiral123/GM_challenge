package com.example.gmchallenge.data.network

import com.example.gmchallenge.data.models.ArtistData
import com.example.gmchallenge.data.models.ArtistDataState
import com.example.gmchallenge.data.models.Track
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ArtistSearchApi {

    @GET("search")
    suspend fun getArtistData(@Query("term") artistName: String): Response<ArtistData>

}

class NetworkArtistFetch(private val artistSearchApi: ArtistSearchApi) {

    suspend fun fetchArtistData(artistName: String, sort: Boolean = false): ArtistDataState {
        return try {
            val response = artistSearchApi.getArtistData(artistName)
            if (response.isSuccessful) {
                val tracks: List<Track> = response.body()!!.results
                if (tracks.isEmpty()) {
                    ArtistDataState.Error(Exception("No tracks for $artistName"))
                } else {
                    ArtistDataState.Success(if (sort) {
                        tracks.sortedBy { it.releaseDate }
                    } else {
                        tracks
                    })
                }
            } else {
                ArtistDataState.Error(Exception(response.raw().message))
            }
        } catch (e: Throwable) {
            ArtistDataState.Error(e)
        }
    }

}