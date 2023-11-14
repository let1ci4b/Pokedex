package com.example.pokedex.main.dto

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class MovesDTO(
    @SerializedName("move")
    val name: String?
) : Serializable
