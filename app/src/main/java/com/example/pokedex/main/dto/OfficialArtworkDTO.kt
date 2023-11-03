package com.example.pokedex.main.dto

import com.google.gson.annotations.SerializedName

data class OfficialArtworkDTO(
    @SerializedName("front_default")
    val frontDefault: String?
)
