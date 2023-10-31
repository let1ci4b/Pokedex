package com.example.pokedex.main.pokemonDetails

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.pokedex.databinding.PokemonDetailsBinding
import com.example.pokedex.main.model.PokemonResponse
import com.example.pokedex.main.pokemonData.PokemonMutableList

class PokemonDetailsActivity : AppCompatActivity() {
    private lateinit var binding: PokemonDetailsBinding
    private var position: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = PokemonDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        position = intent.extras?.getInt("POKEMON")
        position?.let { bindPokemonDetails(PokemonMutableList.pokemonData[it]) }
    }

    private fun bindPokemonDetails(pokemon : PokemonResponse) {
        with(binding) {
            pokemonName.text = pokemon.name
//            pokemonType1.text = pokemon.types[0].toString()
            pokemonWeight.text = pokemon.weight.toString()
            pokemonHeight.text = pokemon.height.toString()
            //pokemonMove1.text = pokemon.moves[0].toString()
        }
    }
}