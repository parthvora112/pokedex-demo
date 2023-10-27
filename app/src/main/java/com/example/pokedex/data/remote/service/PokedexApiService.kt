package com.example.pokedex.data.remote.service

import com.example.pokedex.data.remote.model.GetPokemonDetailsResponseModel
import com.example.pokedex.data.remote.model.GetPokemonListResponseModel
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PokedexApiService {

    @GET("pokemon")
    suspend fun getPokemonList(
        @Query("limit") limit: Int = 20,
        @Query("offset") offset: Int = 0,
    ) : GetPokemonListResponseModel

    @GET("pokemon/{pokemon_id}")
    suspend fun getPokemonDetail(
        @Path("pokemon_id") pokemonId: Int
    ) : GetPokemonDetailsResponseModel
}