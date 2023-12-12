package com.example.pokedex.main.main

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Filter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.room.Room
import com.example.pokedex.databinding.MainLayoutBinding
import com.example.pokedex.main.database.AppDatabase
import com.example.pokedex.main.database.Converter
import com.example.pokedex.main.model.DatabaseObject
import com.example.pokedex.main.pokemonDetails.PokemonDetailsActivity
import net.yslibrary.android.keyboardvisibilityevent.util.UIUtil


class MainActivity : AppCompatActivity(), RecyclerViewInterface {
    private lateinit var binding: MainLayoutBinding
    private lateinit var recyclerViewAdapter: RecyclerViewAdapter
    private var databaseReference: AppDatabase? = null

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

    private lateinit var viewModel: MainViewModel

    // TODO use layer list or gradient to implement inner shadow
    // TODO fix coroutines flux
    /// TODO replace hardcoded strings
    /// TODO fix filter comportment on onresume

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MainLayoutBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupDatabase()
        setupViewModel()
        setupObservers()
        setupRecyclerView()
        setupListeners(null)
        setupPokemonList()
    }

    override fun onResume() {
        super.onResume()
    }

    private fun setupDatabase() {
        DatabaseObject.setupDatabase(this)
        DatabaseObject.database?.let { databaseReference = it }
    }

    override fun onPause() {
        super.onPause()
        isSearchModeOn = false
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setupListeners(view: View?) {
        with(binding) {
            searchViewQuery.setOnTouchListener { view, motionEvent ->
                isSearchModeOn = true
                false }

            searchViewQuery.doOnTextChanged { text, start, before, count ->
                searchPokemons()
            }

            searchBar.setOnClickListener { isSearchModeOn = true }
            exitSearchIcon.setOnClickListener { isSearchModeOn = false }

            filter.setOnClickListener { activePokemonFilter() }

            radioButton1.setOnClickListener { setupPokemonFilter(MainViewModel.Filter.NUMBER) }

            radioButton2.setOnClickListener { setupPokemonFilter(MainViewModel.Filter.NAME) }
        }
    }

    /// TODO replace no results warning to handle with loading pokemons
    private fun searchPokemons() {
        if (!viewModel.searchPokemon(binding.searchViewQuery.text.toString(), recyclerViewAdapter)) {
            Log.i("POKEMON", "Entrou na details")
            Toast.makeText(this@MainActivity, "Sem resultados.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun activePokemonFilter() {
        with(binding) {
            when {
                filterNumber.isVisible -> {
                    radioButton1.isChecked = true
                    radioButton2.isChecked = false
                }
                filterText.isVisible -> {
                    radioButton1.isChecked = false
                    radioButton2.isChecked = true
                }
            }

            if (filterConstraint.isVisible) {
                filterConstraint.visibility = View.GONE
                fadedBackground.visibility = View.GONE
            } else {
                filterConstraint.visibility = View.VISIBLE
                fadedBackground.visibility = View.VISIBLE
            }
        }
    }

    private fun setupPokemonFilter(filter: MainViewModel.Filter) {
        with(binding) {
            when (filter) {
                MainViewModel.Filter.NUMBER -> {
                    filterNumber.visibility = View.VISIBLE
                    filterText.visibility = View.GONE
                }
                MainViewModel.Filter.NAME -> {
                    filterNumber.visibility = View.GONE
                    filterText.visibility = View.VISIBLE
                }
            }
            viewModel.filterPokemonList(filter, recyclerViewAdapter)
            filterConstraint.visibility = View.GONE
            fadedBackground.visibility = View.GONE
        }
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
    }

    /// TODO revise this loading flux comportment
    private fun setupPokemonList() {
        if (recyclerViewAdapter.needToUpdateList() && (viewModel.jobCoroutine?.isActive == false || viewModel.jobCoroutine?.isActive == null)) {
            viewModel.getPokemon()
        }
    }

    private fun setupObservers() {
        viewModel.pokemonCompleteListLiveData.observe(this@MainActivity) { pokemonCompleteList ->
            recyclerViewAdapter.addAllPokemons(pokemonCompleteList)
            binding.loadingLayout.visibility = View.GONE
        }
    }

    private fun setupRecyclerView() {
        with(binding) {
            recyclerViewAdapter = RecyclerViewAdapter(recyclerView, this@MainActivity)
            val gridlayout = GridLayoutManager(this@MainActivity, 3)
            binding.recyclerView.apply {
                setHasFixedSize(false)
                adapter = recyclerViewAdapter
                layoutManager = gridlayout
            }
            recyclerView.requestFocus()
        }
    }

    override fun onPokemonClicked(pokemonId: Int) {
        val intent = Intent(this, PokemonDetailsActivity::class.java)
        intent.putExtra("POKEMON", pokemonId)
        startActivity(intent)
    }
}