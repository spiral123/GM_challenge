package com.example.gmchallenge.data.models

import java.math.BigDecimal

data class ArtistData(
    val resultCount: Int,
    val results: List<Track>
)

data class Track(
    val trackId: Long,
    val artistName: String,
    val trackName: String,
    val trackPrice: BigDecimal? = BigDecimal.ZERO,
    val releaseDate: String,
    val primaryGenreName: String,
)