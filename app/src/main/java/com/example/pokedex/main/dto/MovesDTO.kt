package com.example.pokedex.main.dto

import com.google.gson.annotations.SerializedName

data class MovesDTO(
    @SerializedName("move")
    val name: String?
)
