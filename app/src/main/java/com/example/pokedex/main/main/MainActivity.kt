package com.example.pokedex.main.main

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
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
    // TODO implement load on activity create
    /// TODO fix recyclerview cards loading interruption
    /// TODO replace searchbar with text input edittext to deal with focus bug
    /// TODO replace hardcoded strings

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MainLayoutBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onResume() {
        super.onResume()
        setupDatabase()
        setupViewModel()
        setupObservers()
        setupRecyclerView()
        //        setupListeners(null)
        setupPokemonList()
    }

    private fun setupDatabase() {
        DatabaseObject.database = Room
            .databaseBuilder(this, AppDatabase::class.java, "database-name")
            .addTypeConverter(Converter())
            .build()
    }

    override fun onPause() {
        super.onPause()
        Log.i("POKEMON", "Entrou na main pause")
        isSearchModeOn = false
    }

//    @SuppressLint("ClickableViewAccessibility")
//    private fun setupListeners(view: View?) {
//        with(binding) {
//            searchViewQuery.setOnTouchListener { view, motionEvent ->
//                isSearchModeOn = true
//                false }
//            /// TODO replace no results warning to handle with loading pokemons
//            searchViewQuery.doOnTextChanged { text, start, before, count ->
//                if (!viewModel.filterPokemonList(searchViewQuery.text.toString(), recyclerViewAdapter)) {
//                    Log.i("POKEMON", "Entrou na details")
//                    Toast.makeText(this@MainActivity, "Sem resultados.", Toast.LENGTH_SHORT).show()
//                }
//            }
//            searchBar.setOnClickListener { isSearchModeOn = true }
//            exitSearchIcon.setOnClickListener { isSearchModeOn = false }
//        }
//    }


    private fun setupViewModel() {
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
    }

    private fun setupPokemonList() {
        viewModel.getPokemon()
    }

    private fun setupObservers() {
        viewModel.pokemon.observe(this@MainActivity) { pokemon ->
            recyclerViewAdapter.addPokemon(pokemon)
        }

        viewModel.pokemonCompleteList.observe(this@MainActivity) { pokemonCompleteList ->
            recyclerViewAdapter.addAllPokemons(pokemonCompleteList)
        }
    }

    private fun setupRecyclerView() {
        with(binding) {
            recyclerViewAdapter = RecyclerViewAdapter(recyclerView, this@MainActivity)
            var gridlayout = GridLayoutManager(this@MainActivity, 3)
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
//        val args = Bundle()
//        args.putSerializable("POKEMONSLIST", viewModel.pokemonData as ArrayList)
//        intent.putExtra("BUNDLE", args)
        intent.putExtra("POKEMON", pokemonId)
        startActivity(intent)
    }
}