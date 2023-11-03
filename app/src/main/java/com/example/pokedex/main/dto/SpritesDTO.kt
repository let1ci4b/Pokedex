package com.example.pokedex.main.dto

import com.google.gson.annotations.SerializedName

data class SpritesDTO(
    @SerializedName("other")
    val other: OtherDTO
)
