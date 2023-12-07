package com.example.pokedex.main.dto

import com.google.gson.annotations.SerializedName

data class BaseStatsDTO(
    @SerializedName("base_stat")
    val base_stat : Int
)
