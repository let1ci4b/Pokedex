package com.example.pokedex.main.main

import android.content.Intent
import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.pokedex.databinding.CardPokemonBinding
import com.example.pokedex.main.dto.PokemonResponseDTO
import com.example.pokedex.main.model.Database
import com.example.pokedex.main.pokemonDetails.PokemonDetailsActivity
import java.text.FieldPosition

class RecyclerViewViewHolder(private var binding: CardPokemonBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(pokemon: PokemonResponseDTO, listener: RecyclerViewInterface) {
        val completeId : String =
            when(pokemon.id.toString().length) {
                1 -> "#00"
                2 -> "#0"
                else -> "#"
            }

        with(binding) {
            Glide.with(itemView.context).load(pokemon.sprites.other.officialArtwork.frontDefault).into(binding.pokemonIcon)
            pokemonName.text = pokemon.name
            pokemonCode.text = completeId.plus(pokemon.id.toString())
        }
        setupListeners(pokemon.id, listener)
    }

    private fun setupListeners(pokemon: Int, listener: RecyclerViewInterface) {
        binding.root.setOnClickListener {
            listener.onPokemonClicked(pokemon)
        }
    }
}