package com.example.pokedex.data.remote.service

import com.example.pokedex.data.remote.model.GetPokemonDetailsResponseModel
import com.example.pokedex.data.remote.model.GetPokemonListResponseModel
import kotlinx.coroutines.flow.Flow

interface PokedexNetworkRepository {
    fun getPokemonList(offset: Int = 0): Flow<GetPokemonListResponseModel>
    fun getPokemonDetails(pokemonId: Int): Flow<GetPokemonDetailsResponseModel>
}