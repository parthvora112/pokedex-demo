package com.example.pokedex.data.remote.model


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class Pokemon(
    @SerializedName("name")
    val name: String = "",
    @SerializedName("url")
    val url: String = ""
)