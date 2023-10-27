package com.example.pokedex.data.remote.service

import com.example.pokedex.data.remote.model.GetPokemonDetailsResponseModel
import com.example.pokedex.data.remote.model.GetPokemonListResponseModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class PokedexNetworkRepositoryImpl @Inject constructor(
    private val pokedexApiService: PokedexApiService,
) : PokedexNetworkRepository {

    override fun getPokemonList(offset: Int): Flow<GetPokemonListResponseModel> = flow {
        emit(pokedexApiService.getPokemonList(offset = offset))
    }

    override fun getPokemonDetails(pokemonId: Int): Flow<GetPokemonDetailsResponseModel> = flow {
        emit(pokedexApiService.getPokemonDetail(pokemonId))
    }
}