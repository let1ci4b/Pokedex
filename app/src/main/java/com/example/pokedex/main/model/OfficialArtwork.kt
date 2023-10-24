package com.example.pokedex.main.model

import com.google.gson.annotations.SerializedName

data class OfficialArtwork(
    @SerializedName("front-default")
    val frontDefault: String?
)
