package com.example.pokedex.main.dto

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class OtherDTO(
    @SerializedName("official-artwork") val officialArtwork: OfficialArtworkDTO
) : Serializable
