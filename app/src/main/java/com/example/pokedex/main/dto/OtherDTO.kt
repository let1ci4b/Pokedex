package com.example.pokedex.main.dto

import com.google.gson.annotations.SerializedName

data class OtherDTO(
    @SerializedName("official-artwork") val officialArtwork: OfficialArtworkDTO
)
