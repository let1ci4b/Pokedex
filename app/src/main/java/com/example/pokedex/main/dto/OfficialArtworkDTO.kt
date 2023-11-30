package com.example.pokedex.main.dto

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class OfficialArtworkDTO(
    @SerializedName("front_default")
    val frontDefault: String
) : Serializable
