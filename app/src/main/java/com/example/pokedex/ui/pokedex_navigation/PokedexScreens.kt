package com.example.pokedex.ui.pokedex_navigation

sealed class PokedexScreens(val route: String) {
    data object PokemonListScreen : PokedexScreens("pokemon_list")
    data object PokemonDetailsScreen : PokedexScreens("pokemon_detail")
}