package com.example.pokedex.main.main

import android.content.Intent
import androidx.recyclerview.widget.RecyclerView
import com.example.pokedex.databinding.CardPokemonBinding
import com.example.pokedex.main.model.PokemonResponse
import com.example.pokedex.main.pokemonDetails.PokemonDetailsActivity

class RecyclerViewViewHolder(private var binding: CardPokemonBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(pokemon : PokemonResponse) {
        with(binding) {
            pokemonName.text = pokemon.name
            pokemonCode.text = pokemon.id.toString()
        }
        setupListeners(pokemon.id)
    }

    private fun setupListeners(id: Int?) {
        with(binding) {
            pokemonCardBackground.setOnClickListener {
                val intent = Intent(it.context, PokemonDetailsActivity::class.java)
                intent.putExtra("pokemon", id)
                it.context.startActivity(Intent(it.context, PokemonDetailsActivity::class.java))
            }
        }
    }
}