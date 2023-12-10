package com.example.pokedex.main.dto

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class FlavorTextDTO(
    @SerializedName("flavor_text")
    val flavor_text: String,
    @SerializedName("language")
    val language: LanguageDTO
) : Serializable
