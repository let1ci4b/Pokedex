package com.example.pokedex.main.main

import android.content.Intent
import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.pokedex.databinding.CardPokemonBinding
import com.example.pokedex.main.dto.PokemonResponseDTO
import com.example.pokedex.main.pokemonDetails.PokemonDetailsActivity
import java.text.FieldPosition

class RecyclerViewViewHolder(private var binding: CardPokemonBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(pokemonList: List<PokemonResponseDTO>, position: Int) {
        var completeId : String? =
            when(pokemonList[position].id.toString().length) {
                1 -> "#00"
                2 -> "#0"
                else -> "#"
            }

        with(binding) {
            Glide.with(itemView.context).load(pokemonList[position].sprites.other.officialArtwork.frontDefault).into(binding.pokemonIcon)
            pokemonName.text = pokemonList[position].name
            pokemonCode.text = completeId.plus(pokemonList[position].id.toString())
        }
        setupListeners(pokemonList as ArrayList<PokemonResponseDTO>, position)
    }

    private fun setupListeners(pokemonList : ArrayList<PokemonResponseDTO>, position: Int) {
        with(binding) {
            root.setOnClickListener {
                val intent = Intent(it.context, PokemonDetailsActivity::class.java)
                val args : Bundle = Bundle()
                args.putSerializable("POKEMONSLIST", pokemonList)
                intent.putExtra("BUNDLE", args)
                intent.putExtra("POKEMON", position)
                it.context.startActivity(intent)
            }
        }
    }
}