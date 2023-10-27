package com.example.pokedex.ui.screen.pokemon_list

import com.example.pokedex.data.local.model.Pokemon

data class PokemonListScreenState(
    val isLoading: Boolean? = null,
    val onError: String? = null,
    val pokemonList: ArrayList<Pokemon>? = null
)
