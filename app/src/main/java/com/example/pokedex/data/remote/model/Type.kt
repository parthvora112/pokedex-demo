package com.example.pokedex.data.remote.model


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class Type(
    @SerializedName("slot")
    val slot: Int = 0,
    @SerializedName("type")
    val type: TypeX = TypeX()
)