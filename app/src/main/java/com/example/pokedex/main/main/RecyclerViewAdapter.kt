package com.example.pokedex.main.main

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.pokedex.databinding.CardPokemonBinding
import com.example.pokedex.main.database.AppDatabase
import com.example.pokedex.main.dto.PokemonResponseDTO
import com.example.pokedex.main.model.Database

class RecyclerViewAdapter(
    private val recyclerView: RecyclerView,
    private val listener: RecyclerViewInterface
) : RecyclerView.Adapter<RecyclerViewViewHolder>() {


    private val db : AppDatabase = Database.database
    private var pokemonList: MutableList<PokemonResponseDTO> = mutableListOf()

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): RecyclerViewViewHolder {
        val binding = CardPokemonBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return RecyclerViewViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerViewViewHolder, position: Int) {
        holder.bind(db.pokemonDAO().getPokemonById(position), listener)
    }

    override fun getItemCount() = pokemonList.size

    @SuppressLint("NotifyDataSetChanged")
    fun addPokemon(pokemonId: Int) {
        pokemonList.add(db.pokemonDAO().getPokemonById(pokemonId))
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