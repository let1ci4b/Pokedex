package com.example.pokedex.main.pokemonDetails

import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.pokedex.R
import com.example.pokedex.databinding.PokemonDetailsBinding
import com.example.pokedex.main.database.PokemonEntity

class PokemonDetailsActivity : AppCompatActivity() {
    private lateinit var binding: PokemonDetailsBinding
    private var position: Int = 0
    private lateinit var viewModel: PokemonDetailsViewModel
    private var pokemonsList: List<PokemonEntity>? = null
    private enum class Arrow {
        RIGHT, LEFT
    }

    /// TODO implements pokemon animation
    /// TODO setup progress bar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = PokemonDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupViewModel()
        position = intent.getIntExtra("POKEMON", 0)
        Log.e("details", position.toString())
        getPokemon()
        setupObservers()
        setupOnClickListeners()
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(this)[PokemonDetailsViewModel::class.java]
    }

    private fun getPokemon() {
        viewModel.getPokemonList()
    }

    private fun setupObservers() {
        viewModel.pokemonCompleteList.observe(this@PokemonDetailsActivity) { pokemonCompleteList ->
            pokemonsList = pokemonCompleteList
            bindPokemonDetails(pokemonsList as MutableList<PokemonEntity>)
        }
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

    private fun bindPokemonDetails(pokemonList: MutableList<PokemonEntity>) {
        var completeId : String? =
            when(pokemonList[position].id.toString().length) {
                1 -> "#00"
                2 -> "#0"
                else -> "#"
            }
        with(binding) {
            if (pokemonList?.first() == pokemonList[position]) arrowLeft.visibility = View.GONE
            if (pokemonList?.lastIndex == pokemonList[position].id) arrowRight.visibility = View.GONE
            Glide.with(applicationContext).load(pokemonList[position].sprite).into(pokemonImage)
            pokemonName.text = pokemonList[position].name
            pokemonType1.text = pokemonList[position].types[0]
            pokemonId.text = completeId.plus(pokemonList[position].id.toString())
            if(pokemonList[position].types.size > 1) {
                pokemonType2.text = pokemonList[position].types[1]
                pokemonType2.background.setColorFilter(Color.parseColor(getString(getColorResource(pokemonList[position].types[1]))), PorterDuff.Mode.SRC_OVER)
            }
            else pokemonType2.visibility = View.GONE
            pokemonWeight.text = convertPokemonMeasures(pokemonList[position].weight).plus(" kg")
            pokemonHeight.text = convertPokemonMeasures(pokemonList[position].height).plus(" m")
            pokemonMove1.text = pokemonList[position].abilities[0]
            if(pokemonList[position].abilities.size > 1) pokemonMove2.text = pokemonList[position].abilities[1]
            else pokemonMove2.visibility = View.GONE
            pokemonType1.background.setColorFilter(Color.parseColor(getString(getColorResource(pokemonList[position].types[0]))), PorterDuff.Mode.SRC_OVER)
            pokemonDetails.setBackgroundColor(resources.getColor(getColorResource(pokemonList[position].types[0])))
            titleAbout.setTextColor(resources.getColor(getColorResource(pokemonList[position].types[0])))
            titleStatsLayout.setTextColor(resources.getColor(getColorResource(pokemonList[position].types[0])))
        }
    }

    private fun setupCarousel(arrow: Arrow) {
        with(binding) {
            when (arrow) {
                Arrow.RIGHT -> {
                    binding.arrowLeft.visibility = View.VISIBLE
                    position++
                }

                Arrow.LEFT -> {
                    binding.arrowRight.visibility = View.VISIBLE
                    position--
                }
            }
            pokemonMove2.visibility = View.VISIBLE
            pokemonType2.visibility = View.VISIBLE
            pokemonsList?.let { bindPokemonDetails(it as MutableList<PokemonEntity>) }
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