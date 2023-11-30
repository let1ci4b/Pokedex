package com.example.pokedex.main.pokemonDetails

import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.pokedex.R
import com.example.pokedex.databinding.PokemonDetailsBinding
import com.example.pokedex.main.database.AppDatabase
import com.example.pokedex.main.dto.PokemonResponseDTO
import com.example.pokedex.main.model.Database

class PokemonDetailsActivity : AppCompatActivity() {
    private lateinit var binding: PokemonDetailsBinding
    private var pokemonNumber: Int = 0
    private var db : AppDatabase = Database.database
    private var pokemonsList: List<PokemonResponseDTO>? = db.pokemonDAO().getAll()
    private enum class Arrow {
        RIGHT, LEFT
    }

    /// TODO refactor activity using ViewModel class
    /// TODO fix visibility bug (types and abilities)
    /// TODO replace logic to work with single pokemons

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = PokemonDetailsBinding.inflate(layoutInflater)

        Log.i("POKEMON", "Entrou na details")

        setContentView(binding.root)
//        val args : Bundle? = intent.getBundleExtra("BUNDLE")
//        pokemonsList = args?.get("POKEMONSLIST") as ArrayList<PokemonResponseDTO>?
        pokemonNumber = intent.getIntExtra("POKEMON", 0)
        pokemonsList?.let { bindPokemonDetails(pokemonsList!![pokemonNumber]) }
        setupOnClickListeners()
    }

    private fun setupOnClickListeners() {
        with(binding){
            iconBack.setOnClickListener { finish() }
            arrowRight.setOnClickListener {
                setupCarousel(Arrow.RIGHT)
            }
            arrowLeft.setOnClickListener {
                setupCarousel(Arrow.LEFT)
            }
        }
    }

    private fun bindPokemonDetails(pokemon : PokemonResponseDTO) {
        var completeId : String? =
            when(pokemon?.id.toString().length) {
                1 -> "#00"
                2 -> "#0"
                else -> "#"
            }
        with(binding) {
            if (pokemonsList?.first() == pokemon) arrowLeft.visibility = View.GONE
            if (pokemonsList?.lastIndex == pokemon.id) arrowRight.visibility = View.GONE
            Glide.with(applicationContext).load(pokemon.sprites.other.officialArtwork.frontDefault).into(pokemonImage)
            pokemonName.text = pokemon.name
            pokemonType1.text = pokemon.types[0].type.name
            pokemonId.text = completeId.plus(pokemon.id.toString())
            if(pokemon.types.size > 1) {
                pokemonType2.text = pokemon.types[1].type.name
                pokemonType2.background.setColorFilter(Color.parseColor(getString(getColorResource(pokemon?.types?.get(1)?.type?.name.toString()))), PorterDuff.Mode.SRC_OVER)
            }
            else pokemonType2.visibility = View.GONE
            pokemonWeight.text = convertPokemonMeasures(pokemon.weight).plus(" kg")
            pokemonHeight.text = convertPokemonMeasures(pokemon.height).plus(" m")
            pokemonMove1.text = pokemon.abilities[0].ability.name
            if(pokemon.abilities.size > 1) pokemonMove2.text = pokemon.abilities[1].ability.name
            else pokemonMove2.visibility = View.GONE
            pokemonType1.background.setColorFilter(Color.parseColor(getString(getColorResource(pokemon?.types?.get(0)?.type?.name.toString()))), PorterDuff.Mode.SRC_OVER)
            pokemonDetails.setBackgroundColor(resources.getColor(getColorResource(pokemon?.types?.get(0)?.type?.name.toString())))
            titleAbout.setTextColor(resources.getColor(getColorResource(pokemon?.types?.get(0)?.type?.name.toString())))
            titleStatsLayout.setTextColor(resources.getColor(getColorResource(pokemon?.types?.get(0)?.type?.name.toString())))
        }
    }

    private fun setupCarousel(arrow: Arrow) {
        with(binding) {
            when (arrow) {
                Arrow.RIGHT -> {
                    binding.arrowLeft.visibility = View.VISIBLE
                    pokemonNumber++
                }

                Arrow.LEFT -> {
                    binding.arrowRight.visibility = View.VISIBLE
                    pokemonNumber--
                }
            }
            pokemonMove2.visibility = View.VISIBLE
            pokemonType2.visibility = View.VISIBLE
            bindPokemonDetails(pokemonsList!![pokemonNumber])
        }
    }

    private fun convertPokemonMeasures(measure: Int?) : String {
       return measure?.toFloat()?.div(10).toString()
    }

    private fun getColorResource(type: String) : Int {
        return when(type) {
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