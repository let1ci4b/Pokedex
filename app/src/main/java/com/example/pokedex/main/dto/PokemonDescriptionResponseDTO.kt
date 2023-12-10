package com.example.pokedex.main.dto

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class PokemonDescriptionResponseDTO(
    @SerializedName("flavor_text_entries")
    var text_entries : List<FlavorTextDTO>
) : Serializable
