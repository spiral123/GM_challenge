package com.example.gmchallenge.presentation.features

import com.example.gmchallenge.data.models.ArtistDataState
import com.example.gmchallenge.data.network.NetworkArtistFetch
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ArtistViewModel(private val scope: CoroutineScope, private val network: NetworkArtistFetch) {

    private val _artistData: MutableStateFlow<ArtistDataState> =
        MutableStateFlow(ArtistDataState.Success(emptyList()))
    val artistData: StateFlow<ArtistDataState> = _artistData

    fun resetArtistData() {
        scope.launch {
            _artistData.emit(ArtistDataState.Success(emptyList()))
        }
    }

    fun loadArtistData(artistName: String) {
        scope.launch {
            _artistData.emit(ArtistDataState.Loading)
            _artistData.emit(network.fetchArtistData(artistName))
        }
    }

    fun sortArtistData(artistName: String) {
        scope.launch {
            _artistData.emit(ArtistDataState.Loading)
            _artistData.emit(network.fetchArtistData(artistName, sort = true))
        }
    }


}