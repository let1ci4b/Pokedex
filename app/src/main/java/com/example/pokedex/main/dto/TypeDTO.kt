package com.example.pokedex.main.dto

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class TypeDTO(
    @SerializedName("name")
    val name: String
) : Serializable

