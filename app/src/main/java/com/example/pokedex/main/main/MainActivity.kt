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
    private var isSearchModeOn : Boolean = false
        set(value) {
            with(binding) {
                if(value) exitSearchIcon.visibility = View.VISIBLE
                else {
                    exitSearchIcon.visibility = View.GONE
                    recyclerView.requestFocus()
                    Log.i("POKEMON", "Entrou na foco recycle")
                    UIUtil.hideKeyboard(this@MainActivity, searchViewQuery)
                    searchViewQuery.setText("")
                }
                field = value
            }
        }

    // TODO use layer list or gradient to implement inner shadow
    // TODO implement load on activity create
    /// TODO implement pagination
    /// TODO fix recyclerview cards loading interruption
    /// TODO replace searchbar with text input edittext to deal with focus bug

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MainLayoutBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupViewModel()
        setupObserve()
        setupRecyclerView()
        setupListeners(null)
        setupPokemonList()
    }

    override fun onPause() {
        super.onPause()
        Log.i("POKEMON", "Entrou na main pause")
        isSearchModeOn = false
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
                    Log.i("POKEMON", "Entrou na details")
                    Toast.makeText(this@MainActivity, "Sem resultados.", Toast.LENGTH_SHORT).show()
                }
            }
            searchBar.setOnClickListener { isSearchModeOn = true }
            exitSearchIcon.setOnClickListener { isSearchModeOn = false }
        }
    }


    private fun setupViewModel() {
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
    }

    private fun setupPokemonList() {
        if(viewModel.pokemonData.isEmpty()) viewModel.getPokemon()
    }

    private fun setupObserve() {
        viewModel.pokemon.observe(this@MainActivity) { pokemon ->
            Log.i("POKEMON", "${pokemon.name}")
            recyclerViewAdapter.notifyItemInserted(viewModel.pokemonData.indexOf(pokemon))
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