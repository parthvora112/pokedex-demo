package com.example.pokedex.data.remote.model


import androidx.annotation.Keep
import com.example.pokedex.data.local.model.Pokemon
import com.google.gson.annotations.SerializedName

@Keep
data class PokemonResult(
    @SerializedName("name")
    val name: String = "",
    @SerializedName("url")
    val url: String = ""
) {
    fun toPokemon(): Pokemon {
        val urlSplits = this.url.split("/")
        return Pokemon(
            id = urlSplits[urlSplits.lastIndex - 1].toIntOrNull() ?: -1,
            name = this.name,
            dataUrl = this.url
        )
    }
}