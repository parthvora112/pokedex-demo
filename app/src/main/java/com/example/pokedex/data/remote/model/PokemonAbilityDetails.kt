package com.example.pokedex.data.remote.model


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class PokemonAbilityDetails(
    @SerializedName("ability")
    val ability: Ability = Ability(),
    @SerializedName("is_hidden")
    val isHidden: Boolean = false,
    @SerializedName("slot")
    val slot: Int = 0
)