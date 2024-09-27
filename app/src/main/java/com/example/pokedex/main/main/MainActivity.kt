package com.example.pokedex.main.main

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.pokedex.R
import com.example.pokedex.databinding.MainLayoutBinding
import com.example.pokedex.main.database.AppDatabase
import com.example.pokedex.main.model.DatabaseObject
import com.example.pokedex.main.model.Filter
import com.example.pokedex.main.pokemonDetails.PokemonDetailsActivity
import net.yslibrary.android.keyboardvisibilityevent.util.UIUtil
import java.io.IOException
import java.net.ConnectException

class MainActivity : AppCompatActivity(), RecyclerViewInterface {
    private lateinit var binding: MainLayoutBinding
    private lateinit var recyclerViewAdapter: RecyclerViewAdapter
    private var databaseReference: AppDatabase? = null

    private var isSearchModeOn : Boolean = false
        set(value) {
            with(binding) {
                if(value) {
                    exitSearchIcon.visibility = View.VISIBLE
                    UIUtil.showKeyboard(this@MainActivity, searchViewQuery)
                }
                else {
                    exitSearchIcon.visibility = View.GONE
                    recyclerView.requestFocus()
                    UIUtil.hideKeyboard(this@MainActivity, searchViewQuery)
                    if(searchViewQuery.text.isNotEmpty()) searchViewQuery.setText("")
                }
                field = value
            }
        }

    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MainLayoutBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupDatabase()
        setupViewModel()
        setupObservers()
        setupRecyclerView()
        setupListeners(null)
    }

    override fun onPause() {
        super.onPause()
        if(binding.searchViewQuery.isEnabled) isSearchModeOn = false
        isPokemonFilterOn(false)
    }

    override fun onResume() {
        super.onResume()
        setupPokemonList()
    }

    private fun setupDatabase() {
        DatabaseObject.setupDatabase(this)
        DatabaseObject.database?.let { databaseReference = it }
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
    }

    private fun setupPokemonList() {
        if (recyclerViewAdapter.needToUpdateList() && (viewModel.jobCoroutine?.isActive == false || viewModel.jobCoroutine?.isActive == null)) {
            viewModel.getPokemon()
        }
    }

    private fun setupObservers() {
        with(binding) {
            viewModel.pokemonCompleteListLiveData.observe(this@MainActivity) { pokemonCompleteList ->
                loadingLayout.visibility = View.GONE
                recyclerViewAdapter.addAllPokemons(pokemonCompleteList)
                header.children.forEach {
                    it.isEnabled = true
                }
                searchViewQuery.isEnabled = true
            }
            viewModel.pokemonsLoadingLiveData.observe(this@MainActivity) { pokemonsLoading ->
                setupLoadingLayout(pokemonsLoading)
            }
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

            filter.setOnClickListener {
                if(filterConstraint.isVisible) isPokemonFilterOn(false)
                else isPokemonFilterOn(true)
                isSearchModeOn = false }

            fadedBackground.setOnClickListener { isPokemonFilterOn(false) }

            radioButton1.setOnClickListener {
                Filter.filter = Filter.FilterBy.NUMBER
                setupPokemonFilter()
            }

            radioButton2.setOnClickListener {
                Filter.filter = Filter.FilterBy.NAME
                setupPokemonFilter() }

            tryAgainIcon.setOnClickListener { setupPokemonList() }
        }
    }

    private fun setupLoadingLayout(isOnline : Boolean) {
        with(binding) {
            when (isOnline) {
                true -> {
                    loadingText.visibility = View.VISIBLE
                    mainLoading.visibility = View.VISIBLE
                    tryAgainIcon.visibility = View.GONE
                }
                false -> {
                    loadingText.visibility = View.GONE
                    mainLoading.visibility = View.GONE
                    tryAgainIcon.visibility = View.VISIBLE
                    Toast.makeText(this@MainActivity,  resources.getString(R.string.no_connection_warning), Toast.LENGTH_SHORT).show()
                }
            }
            header.children.forEach {
                it.isEnabled = false
            }
            searchViewQuery.isEnabled = false
        }
    }

    private fun searchPokemons() {
        if (!viewModel.searchPokemon(binding.searchViewQuery.text.toString(), recyclerViewAdapter)) {
            Toast.makeText(this@MainActivity, resources.getString(R.string.no_results_warning), Toast.LENGTH_SHORT).show()
        }
    }

    private fun isPokemonFilterOn(isPokemonFilterOn: Boolean) {
        with(binding) {
            if (isPokemonFilterOn) {
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
                filterConstraint.visibility = View.VISIBLE
                fadedBackground.visibility = View.VISIBLE
            } else {
                filterConstraint.visibility = View.GONE
                fadedBackground.visibility = View.GONE
            }
        }
    }

    private fun setupPokemonFilter() {
        with(binding) {
            when (Filter.filter) {
                Filter.FilterBy.NUMBER -> {
                    filterNumber.visibility = View.VISIBLE
                    filterText.visibility = View.GONE
                }
                Filter.FilterBy.NAME -> {
                    filterNumber.visibility = View.GONE
                    filterText.visibility = View.VISIBLE
                }
            }
            viewModel.filterPokemonList(null, Filter.filter, recyclerViewAdapter)
            isPokemonFilterOn(false)
        }
    }

    override fun onPokemonClicked(pokemonId: Int) {
        val intent = Intent(this, PokemonDetailsActivity::class.java)
        intent.putExtra("POKEMON", pokemonId)
        startActivityForResult(intent, 1)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                val position = data?.getStringExtra("POSITION")
                position?.let { binding.recyclerView.scrollToPosition(it.toInt()) }
            }
        }
    }
}