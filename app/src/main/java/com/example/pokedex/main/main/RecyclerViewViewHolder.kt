package com.example.pokedex.main.main

import android.content.Intent
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.pokedex.databinding.CardPokemonBinding
import com.example.pokedex.main.dto.PokemonResponseDTO
import com.example.pokedex.main.pokemonDetails.PokemonDetailsActivity

class RecyclerViewViewHolder(private var binding: CardPokemonBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(pokemon : PokemonResponseDTO) {
        var completeId : String? =
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
        setupListeners(pokemon)
    }

    private fun setupListeners(pokemon : PokemonResponseDTO) {
        with(binding) {
            root.setOnClickListener {
                val intent = Intent(it.context, PokemonDetailsActivity::class.java)
                intent.putExtra("POKEMON", pokemon)
                it.context.startActivity(intent)
            }
        }
    }
}