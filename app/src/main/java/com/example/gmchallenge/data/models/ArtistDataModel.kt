package com.example.gmchallenge.data.models

sealed class ArtistDataState {
    object Loading : ArtistDataState()
    data class Success(val data: List<Track>) : ArtistDataState()
    data class Error(val exception: Throwable) : ArtistDataState()
}