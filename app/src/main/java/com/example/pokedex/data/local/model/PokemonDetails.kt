package com.example.pokedex.data.local.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class PokemonDetails(
    val height: Int = 0,
    val weight: Int = 0,
    val pokemonTypes: ArrayList<String> = ArrayList(),
    val imageUrls: ArrayList<String> = ArrayList(),
    val abilities: ArrayList<String> = ArrayList(),
    val stat: ArrayList<String> = ArrayList(),
) : Parcelable
