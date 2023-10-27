package com.example.pokedex.data.remote.model


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class PokemonStatDetails(
    @SerializedName("base_stat")
    val baseStat: Int = 0,
    @SerializedName("effort")
    val effort: Int = 0,
    @SerializedName("stat")
    val stat: Stat = Stat()
)