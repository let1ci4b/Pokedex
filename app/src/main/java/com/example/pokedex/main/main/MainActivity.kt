package com.example.pokedex.main.main

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.pokedex.databinding.MainLayoutBinding
import com.example.pokedex.main.pokemonData.PokemonMutableList
import net.yslibrary.android.keyboardvisibilityevent.util.UIUtil


class MainActivity : AppCompatActivity() {
    private lateinit var binding: MainLayoutBinding
    private lateinit var recyclerViewAdapter: RecyclerViewAdapter
    private lateinit var viewModel: MainViewModel

    // TODO use layer list or gradient to implement inner shadow
    // TODO implement load on activity create
    /// TODO implement pagination
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MainLayoutBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.recyclerView.requestFocus()
        if(PokemonMutableList.pokemonData.isEmpty()) getPokemons()
        setupRecyclerView()
        setupListeners(null)
    }

    private var isSearchModeOn : Boolean = false
        set(value) {
            with(binding) {
                if(value) exitSearchIcon.visibility = View.VISIBLE
                else {
                    exitSearchIcon.visibility = View.GONE
                    recyclerView.requestFocus()
                    UIUtil.hideKeyboard(this@MainActivity, searchViewQuery)
                }
                field = value
            }
        }

    private fun setupListeners(view: View?) {
        with(binding) {
            searchViewQuery.doOnTextChanged { text, start, before, count ->
                if (!viewModel.filterPokemonList(searchViewQuery.text.toString(), recyclerViewAdapter)) {
                    Toast.makeText(this@MainActivity, "Sem resultados.", Toast.LENGTH_SHORT).show()
                }
            }
//            view.let { view?.setOnClickListener {
//                    searchBar.children.forEach { childView ->
//                        childView.setOnClickListener { isSearchModeOn = childView != exitSearchIcon }
//                    }
//                }
//            }
        }
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