package com.example.pokedex.main.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.pokedex.databinding.CardPokemonBinding
import com.example.pokedex.main.model.PokemonResponse

class RecyclerViewAdapter(
    private var pokemonList: MutableList<PokemonResponse>,
) : RecyclerView.Adapter<RecyclerViewViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): RecyclerViewViewHolder {
        val binding = CardPokemonBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return RecyclerViewViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerViewViewHolder, position: Int) {
        holder.bind(pokemonList[position])
    }

    override fun getItemCount() = pokemonList.size

}