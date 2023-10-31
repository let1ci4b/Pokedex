package com.example.pokedex.main.model

import com.google.gson.annotations.SerializedName

data class Type(
    @SerializedName("name")
    val name: String?,
    @SerializedName("url")
    val url: String?
)
