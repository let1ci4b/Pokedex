package com.example.pokedex.main.dto

import com.google.gson.annotations.SerializedName

data class SlotTypeDTO(
    @SerializedName("slot")
    val slot: Int?,
    @SerializedName("type")
    val type: String?
)
