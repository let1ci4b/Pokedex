package com.example.pokedex.main.model

object Filter {
    enum class FilterBy {
       NAME, NUMBER
    }
    var filter : FilterBy = FilterBy.NUMBER
}