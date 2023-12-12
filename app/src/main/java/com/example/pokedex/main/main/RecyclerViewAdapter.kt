package com.example.pokedex.main.main

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.pokedex.databinding.CardPokemonBinding
import com.example.pokedex.main.database.PokemonEntity
import com.example.pokedex.main.model.Constants

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
        holder.bind(pokemonList[position], position, listener)
    }

    override fun getItemCount() = pokemonList.size

    /// TODO scroll to position user was before on resume
//    recyclerView.scrollToPosition(pokemonList.lastIndex)

    @SuppressLint("NotifyDataSetChanged")
    fun addAllPokemons(pokemonCompleteList: List<PokemonEntity>) {
        pokemonList.clear()
        pokemonList.addAll(pokemonCompleteList)
        notifyDataSetChanged()
    }

    fun needToUpdateList(): Boolean = pokemonList.size < Constants.MAX_POKEMON_ID

    @SuppressLint("NotifyDataSetChanged")
    fun filterList(filteredList: List<PokemonEntity>) {
        pokemonList = filteredList.toMutableList()
        notifyDataSetChanged()
    }

}