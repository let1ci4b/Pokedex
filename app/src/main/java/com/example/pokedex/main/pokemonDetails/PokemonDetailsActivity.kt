package com.example.pokedex.main.pokemonDetails

import android.app.Activity
import android.content.Intent
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
    private var selectedPokemonID: Int = 0
    private var position: Int = 0
    private lateinit var viewModel: PokemonDetailsViewModel
    private var pokemonsList: List<PokemonEntity>? = null
    private enum class Arrow {
        RIGHT, LEFT
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = PokemonDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupViewModel()
        selectedPokemonID = intent.getIntExtra("POKEMON", 0)
        Log.e("details", selectedPokemonID.toString())
        getPokemon()
        setupObservers()
        setupOnClickListeners()
    }

    fun getPokemonPositionById() {
        position = pokemonsList?.indexOfFirst { it.id == selectedPokemonID }!!
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
            getPokemonPositionById()
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
        with(binding) {
            if (pokemonList.first() == pokemonList[position]) arrowLeft.visibility = View.GONE
            if (pokemonList.lastIndex == pokemonList[position].id) arrowRight.visibility = View.GONE

            var name = pokemonList[position].name
            pokemonName.text = name[0].toUpperCase().plus(name.substring(1))
            Glide.with(applicationContext).load(pokemonList[position].sprite).into(pokemonImage)
            pokemonId.text = "#".plus(completeNumber(pokemonList[position].id.toString()).plus(pokemonList[position].id.toString()))
            pokemonDescription.text = pokemonList[position].flavor_text

            var type1 = pokemonList[position].types[0]
            pokemonType1.text = type1[0].toUpperCase().plus(type1.substring(1))
            pokemonType1.background.setColorFilter(Color.parseColor(getString(getColorResource(pokemonList[position].types[0], false))), PorterDuff.Mode.SRC_OVER)
            if(pokemonList[position].types.size > 1) {
                var type2 = pokemonList[position].types[1]
                pokemonType2.text = type2[0].toUpperCase().plus(type2.substring(1))
                pokemonType2.background.setColorFilter(Color.parseColor(getString(getColorResource(pokemonList[position].types[1], false))), PorterDuff.Mode.SRC_OVER)
            }
            else pokemonType2.visibility = View.GONE

            pokemonWeight.text = convertPokemonMeasures(pokemonList[position].weight).plus(" kg")
            pokemonHeight.text = convertPokemonMeasures(pokemonList[position].height).plus(" m")
            var move1 = pokemonList[position].abilities[0]
            pokemonMove1.text = move1[0].toUpperCase().plus(move1.substring(1))
            if(pokemonList[position].abilities.size > 1) {
                var move2 = pokemonList[position].abilities[1]
                pokemonMove2.text = move2[0].toUpperCase().plus(move2.substring(1))
            }
            else pokemonMove2.visibility = View.GONE

            hpValue.text = completeNumber(pokemonList[position].base_stats[0]).plus(pokemonList[position].base_stats[0])
            atkValue.text = completeNumber(pokemonList[position].base_stats[0]).plus(pokemonList[position].base_stats[1])
            defValue.text = completeNumber(pokemonList[position].base_stats[0]).plus(pokemonList[position].base_stats[2])
            satkValue.text = completeNumber(pokemonList[position].base_stats[0]).plus(pokemonList[position].base_stats[3])
            sdefValue.text = completeNumber(pokemonList[position].base_stats[0]).plus(pokemonList[position].base_stats[4])
            spdValue.text = completeNumber(pokemonList[position].base_stats[0]).plus(pokemonList[position].base_stats[5])

            hpTitle.setTextColor(resources.getColor(getColorResource(pokemonList[position].types[0], false)))
            atkTitle.setTextColor(resources.getColor(getColorResource(pokemonList[position].types[0], false)))
            defTitle.setTextColor(resources.getColor(getColorResource(pokemonList[position].types[0], false)))
            satkTitle.setTextColor(resources.getColor(getColorResource(pokemonList[position].types[0], false)))
            sdefTitle.setTextColor(resources.getColor(getColorResource(pokemonList[position].types[0], false)))
            spdTitle.setTextColor(resources.getColor(getColorResource(pokemonList[position].types[0], false)))

            hpProgressBar.progress = pokemonList[position].base_stats[0].toInt()
            atkProgressBar.progress = pokemonList[position].base_stats[1].toInt()
            defProgressBar.progress = pokemonList[position].base_stats[2].toInt()
            satkProgressBar.progress = pokemonList[position].base_stats[3].toInt()
            sdefProgressBar.progress = pokemonList[position].base_stats[4].toInt()
            spdProgressBar.progress = pokemonList[position].base_stats[5].toInt()

            hpProgressBar.setIndicatorColor(resources.getColor(getColorResource(pokemonList[position].types[0], false)))
            hpProgressBar.setBackgroundColor(resources.getColor(getColorResource(pokemonList[position].types[0], true)))
            atkProgressBar.setIndicatorColor(resources.getColor(getColorResource(pokemonList[position].types[0], false)))
            atkProgressBar.setBackgroundColor(resources.getColor(getColorResource(pokemonList[position].types[0], true)))
            defProgressBar.setIndicatorColor(resources.getColor(getColorResource(pokemonList[position].types[0], false)))
            defProgressBar.setBackgroundColor(resources.getColor(getColorResource(pokemonList[position].types[0], true)))
            satkProgressBar.setIndicatorColor(resources.getColor(getColorResource(pokemonList[position].types[0], false)))
            satkProgressBar.setBackgroundColor(resources.getColor(getColorResource(pokemonList[position].types[0], true)))
            sdefProgressBar.setIndicatorColor(resources.getColor(getColorResource(pokemonList[position].types[0], false)))
            sdefProgressBar.setBackgroundColor(resources.getColor(getColorResource(pokemonList[position].types[0], true)))
            spdProgressBar.setIndicatorColor(resources.getColor(getColorResource(pokemonList[position].types[0], false)))
            spdProgressBar.setBackgroundColor(resources.getColor(getColorResource(pokemonList[position].types[0], true)))

            pokemonDetails.setBackgroundColor((resources.getColor(getColorResource(pokemonList[position].types[0], false))))
            titleAbout.setTextColor(resources.getColor(getColorResource(pokemonList[position].types[0], false)))
            titleStatsLayout.setTextColor(resources.getColor(getColorResource(pokemonList[position].types[0], false)))
        }
    }

    private fun completeNumber(num : String) : String {
        return when (num.length) {
            1 -> "00"
            2 -> "0"
            else -> ""
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

    private fun getColorResource(type: String, isFaded: Boolean) : Int {
        return when(type) {
            "electric" -> {
                if(isFaded) {
                    R.color.faded_electric_pokemon
                } else R.color.electric_pokemon
            }
            "bug" -> {
                if(isFaded) {
                    R.color.faded_bug_pokemon
                } else R.color.bug_pokemon
            }
            "dark" -> {
                if(isFaded) {
                    R.color.faded_dark_pokemon
                } else R.color.dark_pokemon
            }
            "dragon" -> {
                if(isFaded) {
                    R.color.faded_dragon_pokemon
                } else R.color.dragon_pokemon
            }
            "fairy" -> {
                if(isFaded) {
                    R.color.faded_fairy_pokemon
                } else R.color.fairy_pokemon
            }
            "fighting" -> {
                if(isFaded) {
                    R.color.faded_fighting_pokemon
                } else R.color.fighting_pokemon
            }
            "fire" -> {
                if(isFaded) {
                    R.color.faded_fire_pokemon
                } else R.color.fire_pokemon
            }
            "flying" -> {
                if(isFaded) {
                    R.color.faded_flying_pokemon
                } else R.color.flying_pokemon
            }
            "ghost" -> {
                if(isFaded) {
                    R.color.faded_ghost_pokemon
                } else R.color.ghost_pokemon
            }
            "normal" -> {
                if(isFaded) {
                    R.color.faded_normal_pokemon
                } else R.color.normal_pokemon
            }
            "grass" -> {
                if(isFaded) {
                    R.color.faded_grass_pokemon
                } else R.color.grass_pokemon
            }
            "ground" -> {
                if(isFaded) {
                    R.color.faded_ground_pokemon
                } else R.color.ground_pokemon
            }
            "ice" -> {
                if(isFaded) {
                    R.color.faded_ice_pokemon
                } else R.color.ice_pokemon
            }
            "poison" -> {
                if(isFaded) {
                    R.color.faded_poison_pokemon
                } else R.color.poison_pokemon
            }
            "psychic" -> {
                if(isFaded) {
                    R.color.faded_psychic_pokemon
                } else R.color.psychic_pokemon
            }
            "rock" -> {
                if(isFaded) {
                    R.color.faded_rock_pokemon
                } else R.color.rock_pokemon
            }
            "steel" -> {
                if(isFaded) {
                    R.color.faded_steel_pokemon
                } else R.color.steel_pokemon
            }
            "water" -> {
                if(isFaded) {
                    R.color.faded_water_pokemon
                } else R.color.water_pokemon
            }
            else -> {
                if(isFaded) {
                    R.color.faded_primary
                } else R.color.primary
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        val intent = Intent()
        intent.putExtra("POSITION", position)
        setResult(Activity.RESULT_OK, intent)
    }
}