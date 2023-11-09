package com.example.pokedex.main.main

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.pokedex.databinding.MainLayoutBinding
import net.yslibrary.android.keyboardvisibilityevent.util.UIUtil


class MainActivity : AppCompatActivity() {
    private lateinit var binding: MainLayoutBinding
    private lateinit var recyclerViewAdapter: RecyclerViewAdapter
    private var viewModel: MainViewModel = MainViewModel()

    // TODO use layer list or gradient to implement inner shadow
    // TODO implement load on activity create
    /// TODO implement pagination
    /// TODO fix recyclerview cards loading interruption

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MainLayoutBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.recyclerView.requestFocus()
        if(viewModel.pokemonData.isEmpty()) getPokemons()
        setupRecyclerView()
        setupListeners(null)
    }

    override fun onPause() {
        super.onPause()
        isSearchModeOn = false
    }

    private var isSearchModeOn : Boolean = false
        set(value) {
            with(binding) {
                if(value) exitSearchIcon.visibility = View.VISIBLE
                else {
                    exitSearchIcon.visibility = View.GONE
                    recyclerView.requestFocus()
                    UIUtil.hideKeyboard(this@MainActivity, searchViewQuery)
                    searchViewQuery.setText("")
                }
                field = value
            }
        }

    @SuppressLint("ClickableViewAccessibility")
    private fun setupListeners(view: View?) {
        with(binding) {
            searchViewQuery.setOnTouchListener { view, motionEvent ->
                isSearchModeOn = true
                false }
            /// TODO replace no results warning to handle with loading pokemons
            searchViewQuery.doOnTextChanged { text, start, before, count ->
                if (!viewModel.filterPokemonList(searchViewQuery.text.toString(), recyclerViewAdapter)) {
                    Toast.makeText(this@MainActivity, "Sem resultados.", Toast.LENGTH_SHORT).show()
                }
            }
            searchBar.setOnClickListener { isSearchModeOn = true }
            exitSearchIcon.setOnClickListener { isSearchModeOn = false }
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
        recyclerViewAdapter = RecyclerViewAdapter(viewModel.pokemonData)
        var gridlayout = GridLayoutManager(this, 3)
        binding.recyclerView.apply {
            setHasFixedSize(false)

            adapter = recyclerViewAdapter
            layoutManager = gridlayout
        }
    }
}