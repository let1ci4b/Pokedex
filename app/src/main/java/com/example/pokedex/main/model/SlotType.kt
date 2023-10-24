package com.example.pokedex.main.model

import com.google.gson.annotations.SerializedName

data class SlotType(
    @SerializedName("slot") val slot: Int?,
    @SerializedName("type") val type: Type
)
