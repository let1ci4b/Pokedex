package com.example.pokedex.main.dto

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class SpritesDTO(
    @SerializedName("other")
    val other: OtherDTO
) : Serializable
