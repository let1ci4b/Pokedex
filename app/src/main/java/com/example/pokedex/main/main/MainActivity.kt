package com.example.pokedex.main.main

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.pokedex.databinding.MainLayoutBinding
import com.example.pokedex.main.pokemonData.PokemonMutableList

class MainActivity : AppCompatActivity() {
    private lateinit var binding: MainLayoutBinding
    private lateinit var recyclerViewAdapter: RecyclerViewAdapter
    private lateinit var viewModel: MainViewModel

    // TODO use layer list or gradient to implement inner shadow
    // TODO implement load on activity create
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MainLayoutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getPokemons()
        setupRecyclerView()
    }

    private fun getPokemons() {
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        viewModel.getPokemon()

        viewModel.pokemon.observe(this@MainActivity) { pokedex ->
            pokedex.forEach { pokemon ->
                Log.i("POKEMON", "${pokemon.name}")
                recyclerViewAdapter.notifyDataSetChanged()
            }
        }
    }

    private fun setupRecyclerView() {
        /// TODO adjust Adapter instance (view cannot access model data)
        recyclerViewAdapter = RecyclerViewAdapter(PokemonMutableList.pokemonData)
        var gridlayout = GridLayoutManager(this, 3)
        binding.recyclerView.apply {
            setHasFixedSize(false)

            adapter = recyclerViewAdapter
            layoutManager = gridlayout
        }
    }
}