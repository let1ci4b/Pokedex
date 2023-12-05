package com.example.pokedex.main.main

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.pokedex.databinding.CardPokemonBinding
import com.example.pokedex.main.database.AppDatabase
import com.example.pokedex.main.database.PokemonEntity
import com.example.pokedex.main.model.DatabaseObject
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class RecyclerViewAdapter(
    private val recyclerView: RecyclerView,
    private val listener: RecyclerViewInterface
) : RecyclerView.Adapter<RecyclerViewViewHolder>() {

    private var pokemonList: MutableList<PokemonEntity> = mutableListOf()

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): RecyclerViewViewHolder {
        val binding = CardPokemonBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return RecyclerViewViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerViewViewHolder, position: Int) {
        holder.bind(pokemonList[position], listener)
    }

    override fun getItemCount() = pokemonList.size

    @SuppressLint("NotifyDataSetChanged")
    fun addPokemon(pokemon: PokemonEntity) {
        pokemonList.add(pokemon)
        pokemonList.sortedBy { it.id }
        notifyDataSetChanged()
        recyclerView.scrollToPosition(pokemonList.lastIndex)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun addAllPokemons(pokemonCompleteList: List<PokemonEntity>) {
        pokemonList.clear()
        pokemonList.addAll(pokemonCompleteList)
        pokemonList.sortedBy { it.id }
        notifyDataSetChanged()
    }

}