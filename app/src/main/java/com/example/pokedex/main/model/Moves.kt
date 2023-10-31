package com.example.pokedex.main.model

import com.google.gson.annotations.SerializedName

data class Moves(
    @SerializedName("move")
    val name: String?
)
