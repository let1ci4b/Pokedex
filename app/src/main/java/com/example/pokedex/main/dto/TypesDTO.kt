package com.example.pokedex.main.dto

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class TypesDTO(
    @SerializedName("type")
    val type: TypeDTO
) : Serializable
