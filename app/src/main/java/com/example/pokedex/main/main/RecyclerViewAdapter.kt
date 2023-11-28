package com.example.pokedex.main.main

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.pokedex.databinding.CardPokemonBinding
import com.example.pokedex.main.dto.PokemonResponseDTO

class RecyclerViewAdapter(
    private val recyclerView: RecyclerView,
    private val listener: RecyclerViewInterface,
) : RecyclerView.Adapter<RecyclerViewViewHolder>() {

    private var pokemonList: MutableList<PokemonResponseDTO> = mutableListOf()

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): RecyclerViewViewHolder {
        val binding = CardPokemonBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return RecyclerViewViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerViewViewHolder, position: Int) {
        holder.bind(pokemonList[position], listener)
    }

    override fun getItemCount() = pokemonList.size

    @SuppressLint("NotifyDataSetChanged")
    fun addPokemon(pokemon: PokemonResponseDTO) {
        pokemonList.add(pokemon)
        pokemonList.sortedBy { it.id }
        notifyDataSetChanged()
        recyclerView.scrollToPosition(pokemonList.lastIndex)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun filterList(filterlist: ArrayList<PokemonResponseDTO>) {
        pokemonList = filterlist
        notifyDataSetChanged()
    }

}