package com.example.pokedex.main.dto

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class AbilitiesDTO(
    @SerializedName("ability")
    val ability: AbilityDTO
) : Serializable
