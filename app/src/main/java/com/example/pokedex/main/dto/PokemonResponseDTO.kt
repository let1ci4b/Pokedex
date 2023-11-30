package com.example.pokedex.main.dto

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName // Retrofit recurse to indicate a JSON serialization
import java.io.Serializable

@Entity
data class PokemonResponseDTO(
    @PrimaryKey
    @SerializedName("id")
    var id : Int,
    @ColumnInfo
    @SerializedName("name")
    val name: String,
    @ColumnInfo
    @SerializedName("height")
    val height: Int?,
    @ColumnInfo
    @SerializedName("weight")
    val weight: Int?,
    @ColumnInfo
    @SerializedName("sprites")
    val sprites: SpritesDTO?,
    @ColumnInfo
    @SerializedName("types")
    val types: List<TypesDTO>,
    @ColumnInfo
    @SerializedName("abilities")
    val abilities: List<AbilitiesDTO>,
    @ColumnInfo
    @SerializedName("flavor_text")
    val flavor_text: String,
    ) : Serializable

