package com.example.pokedex.main.model

import com.google.gson.annotations.SerializedName

data class Sprites(
    @SerializedName("official-artwork")
    val officialArtwork: List<OfficialArtwork>
)
