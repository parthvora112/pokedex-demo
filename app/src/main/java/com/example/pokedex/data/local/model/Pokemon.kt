package com.example.pokedex.data.local.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class Pokemon (
    val id: Int,
    val name: String,
    val dataUrl: String,
    var details: PokemonDetails? = null
) : Parcelable