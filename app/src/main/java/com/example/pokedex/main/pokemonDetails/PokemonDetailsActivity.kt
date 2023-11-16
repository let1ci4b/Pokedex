package com.example.pokedex.main.pokemonDetails

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import com.example.pokedex.R
import com.example.pokedex.databinding.PokemonDetailsBinding
import com.example.pokedex.main.dto.PokemonResponseDTO

class PokemonDetailsActivity : AppCompatActivity() {
    private lateinit var binding: PokemonDetailsBinding
    private var pokemon: PokemonResponseDTO? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = PokemonDetailsBinding.inflate(layoutInflater)

        Log.i("POKEMON", "Entrou na details")

        setContentView(binding.root)
        pokemon = intent.getSerializableExtra("POKEMON") as PokemonResponseDTO
        pokemon?.let { bindPokemonDetails(pokemon!!) }
    }

    var completeId : String? =
        when(pokemon?.id.toString().length) {
            1 -> "#00"
            2 -> "#0"
            else -> "#"
        }

    private fun bindPokemonDetails(pokemon : PokemonResponseDTO) {
        with(binding) {
            Glide.with(applicationContext).load(pokemon.sprites.other.officialArtwork.frontDefault).into(binding.pokemonImage)
            pokemonName.text = pokemon.name
            pokemonType1.text = pokemon.types[0].type.name
            pokemonId.text = completeId.plus(pokemon.id.toString())
            if(pokemon.types.size > 1) pokemonType2.text = pokemon.types[1].type.name
            else pokemonType2.visibility = View.GONE
            pokemonWeight.text = convertPokemonMeasures(pokemon.weight).plus(" kg")
            pokemonHeight.text = convertPokemonMeasures(pokemon.height).plus(" m")
            pokemonMove1.text = pokemon.abilities[0].ability.name
            if(pokemon.abilities.size > 1) pokemonMove2.text = pokemon.abilities[1].ability.name
            else pokemonMove2.visibility = View.GONE

            pokemonDetails.setBackgroundColor(resources.getColor(getColorResource()))
//            pokemonType1.setBackgroundColor(resources.getColor(getColorResource()))
//            pokemonType2.setBackgroundColor(resources.getColor(getColorResource()))
        }
    }

    private fun convertPokemonMeasures(measure: Int?) : String {
       return measure?.toFloat()?.div(10).toString()
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