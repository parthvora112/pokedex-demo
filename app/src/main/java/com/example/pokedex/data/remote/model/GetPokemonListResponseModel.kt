package com.example.pokedex.data.remote.model


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class GetPokemonListResponseModel(
    @SerializedName("count")
    val count: Int = 0,
    @SerializedName("next")
    val next: String? = null,
    @SerializedName("previous")
    val previous: String = "",
    @SerializedName("results")
    val results: List<PokemonResult> = listOf()
)