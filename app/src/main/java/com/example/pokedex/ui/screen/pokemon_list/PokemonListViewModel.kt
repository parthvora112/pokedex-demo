package com.example.pokedex.ui.screen.pokemon_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokedex.data.local.model.Pokemon
import com.example.pokedex.data.remote.service.PokedexNetworkRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokemonListViewModel @Inject constructor(
    private val pokedexNetworkRepository: PokedexNetworkRepositoryImpl
) : ViewModel() {
    private var _pokemonListScreenState = MutableStateFlow(PokemonListScreenState())
    val pokemonListScreenState = _pokemonListScreenState.asStateFlow()

    private var currentOffset: Int = 0
    private val pokemonList: ArrayList<Pokemon> = ArrayList()

    init {
        fetchPokemonList()
    }

    fun fetchPokemonList() {
        viewModelScope.launch {
            _pokemonListScreenState.update {
                _pokemonListScreenState.value.copy(
                    isLoading = true,
                    onError = null
                )
            }
            pokedexNetworkRepository.getPokemonList(currentOffset).flowOn(Dispatchers.IO)
                .catch { e ->
                    _pokemonListScreenState.update {
                        _pokemonListScreenState.value.copy(
                            isLoading = false,
                            onError = e.localizedMessage ?: "Something wrong."
                        )
                    }
                }
                .collect { pokemonListResponse ->
                    if (pokemonListResponse.results.isNotEmpty()) {
                        val newPokemonList = pokemonListResponse.results.map { pokemonResult ->
                            pokemonResult.toPokemon()
                        }
                        getPokemonDetails(newPokemonList)
                    }
                }
        }
    }

    private fun getPokemonDetails(pokemonListResponse: List<Pokemon>) {
        for (i in pokemonListResponse.indices) {
            viewModelScope.launch {
                pokedexNetworkRepository.getPokemonDetails(pokemonListResponse[i].id)
                    .flowOn(Dispatchers.IO)
                    .catch {}
                    .collect { pokemonDetails ->
                        pokemonListResponse[i].details = pokemonDetails.toPokemonDetails()
                        if (i == pokemonListResponse.size - 1) {
                            _pokemonListScreenState.update {
                                _pokemonListScreenState.value.copy(
                                    isLoading = false,
                                    pokemonList = pokemonList.apply {
                                        addAll(pokemonListResponse)
                                    }
                                )
                            }
                        }
                    }
            }
        }

    }
}