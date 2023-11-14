package com.example.pokedex.main.pokemonDetails

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import com.example.pokedex.R
import com.example.pokedex.databinding.PokemonDetailsBinding
import com.example.pokedex.main.dto.PokemonResponseDTO

class PokemonDetailsActivity : AppCompatActivity() {
    private lateinit var binding: PokemonDetailsBinding
    private var pokemon: PokemonResponseDTO? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = PokemonDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        pokemon = intent.getSerializableExtra("POKEMON") as PokemonResponseDTO
        pokemon?.let { bindPokemonDetails(pokemon!!) }
    }

    private fun bindPokemonDetails(pokemon : PokemonResponseDTO) {
        with(binding) {
            pokemonDetails.setBackgroundColor(resources.getColor(getColorResource()))
            pokemonName.text = pokemon.name
            pokemonType1.text = pokemon.types[0].type.name
            if(pokemon.types.size > 1) pokemonType2.text = pokemon.types[1].type.name
            else pokemonType2.visibility = View.GONE
            pokemonWeight.text = pokemon.weight.toString().plus(" kg")
            pokemonHeight.text = pokemon.height.toString().plus(" m")
            pokemonMove1.text = pokemon.abilities[0].ability.name
            if(pokemon.abilities.size > 1) pokemonMove2.text = pokemon.abilities[1].ability.name
            else pokemonMove2.visibility = View.GONE
        }
    }

    private fun getColorResource() : Int {
        return when(pokemon?.types?.get(0)?.type?.name) {
            "electric" -> R.color.electric_pokemon
            "bug" -> R.color.bug_pokemon
            "dark" -> R.color.dark_pokemon
            "dragon" -> R.color.dragon_pokemon
            "fairy" -> R.color.fairy_pokemon
            "fighting" -> R.color.fighting_pokemon
            "fire" -> R.color.fire_pokemon
            "flying" -> R.color.flying_pokemon
            "ghost" -> R.color.ghost_pokemon
            "normal" -> R.color.normal_pokemon
            "grass" -> R.color.grass_pokemon
            "ground" -> R.color.ground_pokemon
            "ice" -> R.color.ice_pokemon
            "poison" -> R.color.poison_pokemon
            "psychic" -> R.color.psychic_pokemon
            "rock" -> R.color.rock_pokemon
            "steel" -> R.color.steel_pokemon
            "water" -> R.color.water_pokemon
            else -> R.color.primary
        }
    }
}