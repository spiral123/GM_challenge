package com.example.gmchallenge.presentation.features

import com.example.gmchallenge.data.models.ArtistDataState
import com.example.gmchallenge.data.network.NetworkArtistFetch
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class ArtistViewModel(private val scope: CoroutineScope, private val network: NetworkArtistFetch) {

    private val artistData: MutableStateFlow<ArtistDataState> =
        MutableStateFlow(ArtistDataState.Success(emptyList()))

    fun resetArtistData() {
        artistData.value = ArtistDataState.Success(emptyList())
    }

    fun loadArtistData(artistName: String) {
        scope.launch {
            artistData.value = ArtistDataState.Loading
            artistData.value = network.fetchArtistData(artistName)
        }
    }


}