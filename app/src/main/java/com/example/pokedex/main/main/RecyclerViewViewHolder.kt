package com.example.pokedex.main.main

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.pokedex.databinding.CardPokemonBinding
import com.example.pokedex.main.database.PokemonEntity

class RecyclerViewViewHolder(private var binding: CardPokemonBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(pokemon: PokemonEntity, position: Int, listener: RecyclerViewInterface) {
        val completeId : String =
            when(pokemon.id.toString().length) {
                1 -> "#00"
                2 -> "#0"
                else -> "#"
            }

        with(binding) {
            Glide.with(itemView.context).load(pokemon.sprite).into(binding.pokemonIcon)
            pokemonName.text = pokemon.name
            pokemonCode.text = completeId.plus(pokemon.id.toString())
        }
        setupListeners(position, listener)
    }

    private fun setupListeners(position: Int, listener: RecyclerViewInterface) {
        binding.root.setOnClickListener {
            listener.onPokemonClicked(position)
        }
    }
}