package com.example.pokedex.data.remote.model


import androidx.annotation.Keep
import com.example.pokedex.data.local.model.PokemonDetails
import com.google.gson.annotations.SerializedName

@Keep
data class GetPokemonDetailsResponseModel(
    @SerializedName("form_name")
    val formName: String = "",
    @SerializedName("form_names")
    val formNames: List<Any> = listOf(),
    @SerializedName("form_order")
    val formOrder: Int = 0,
    @SerializedName("id")
    val id: Int = 0,
    @SerializedName("is_battle_only")
    val isBattleOnly: Boolean = false,
    @SerializedName("is_default")
    val isDefault: Boolean = false,
    @SerializedName("is_mega")
    val isMega: Boolean = false,
    @SerializedName("name")
    val name: String = "",
    @SerializedName("names")
    val names: List<Any> = listOf(),
    @SerializedName("order")
    val order: Int = 0,
    @SerializedName("pokemon")
    val pokemon: Pokemon = Pokemon(),
    @SerializedName("sprites")
    val sprites: Sprites = Sprites(),
    @SerializedName("types")
    val types: List<Type> = listOf(),
    @SerializedName("abilities")
    val abilities: List<PokemonAbilityDetails> = listOf(),
    @SerializedName("stats")
    val stats: List<PokemonStatDetails> = listOf(),
    @SerializedName("version_group")
    val versionGroup: VersionGroup = VersionGroup(),
    @SerializedName("height")
    val height: Int = 0,
    @SerializedName("weight")
    val weight: Int = 0,
) {
    fun toPokemonDetails(): PokemonDetails {
        val pokemonTypeList = ArrayList<String>()
        val pokemonImageUrlList = ArrayList<String>()
        val pokemonAbilities = ArrayList<String>()
        val pokemonStats = ArrayList<String>()
        this.types.forEach { type ->
            pokemonTypeList.add(type.type.name)
        }
        this.sprites.apply {
            if (other != null) {
                other.apply {
                    home?.apply {
                        frontDefault?.let {
                            pokemonImageUrlList.add(it)
                        }
                        frontFemale?.let {
                            pokemonImageUrlList.add(it)
                        }
                        backDefault?.let {
                            pokemonImageUrlList.add(it)
                        }
                        backFemale?.let {
                            pokemonImageUrlList.add(it)
                        }
                        backShiny?.let {
                            pokemonImageUrlList.add(it)
                        }
                        backShinyFemale?.let {
                            pokemonImageUrlList.add(it)
                        }
                        frontShiny?.let {
                            pokemonImageUrlList.add(it)
                        }
                        frontShinyFemale?.let {
                            pokemonImageUrlList.add(it)
                        }
                    }
                    dreamWorld?.apply {
                        frontDefault?.let {
                            pokemonImageUrlList.add(it)
                        }
                        frontFemale?.let {
                            pokemonImageUrlList.add(it)
                        }
                        backDefault?.let {
                            pokemonImageUrlList.add(it)
                        }
                        backFemale?.let {
                            pokemonImageUrlList.add(it)
                        }
                        backShiny?.let {
                            pokemonImageUrlList.add(it)
                        }
                        backShinyFemale?.let {
                            pokemonImageUrlList.add(it)
                        }
                        frontShiny?.let {
                            pokemonImageUrlList.add(it)
                        }
                        frontShinyFemale?.let {
                            pokemonImageUrlList.add(it)
                        }
                    }
                    officialArtwork?.apply {
                        frontDefault?.let {
                            pokemonImageUrlList.add(it)
                        }
                        frontFemale?.let {
                            pokemonImageUrlList.add(it)
                        }
                        backDefault?.let {
                            pokemonImageUrlList.add(it)
                        }
                        backFemale?.let {
                            pokemonImageUrlList.add(it)
                        }
                        backShiny?.let {
                            pokemonImageUrlList.add(it)
                        }
                        backShinyFemale?.let {
                            pokemonImageUrlList.add(it)
                        }
                        frontShiny?.let {
                            pokemonImageUrlList.add(it)
                        }
                        frontShinyFemale?.let {
                            pokemonImageUrlList.add(it)
                        }
                    }
                }
            } else {
                frontDefault?.let {
                    pokemonImageUrlList.add(it)
                }
                frontFemale?.let {
                    pokemonImageUrlList.add(it)
                }
                backDefault?.let {
                    pokemonImageUrlList.add(it)
                }
                backFemale?.let {
                    pokemonImageUrlList.add(it)
                }
                backShiny?.let {
                    pokemonImageUrlList.add(it)
                }
                backShinyFemale?.let {
                    pokemonImageUrlList.add(it)
                }
                frontShiny?.let {
                    pokemonImageUrlList.add(it)
                }
                frontShinyFemale?.let {
                    pokemonImageUrlList.add(it)
                }
            }
        }
        this.abilities.forEach {
            pokemonAbilities.add(it.ability.name)
        }
        this.stats.forEach {
            pokemonStats.add(it.stat.name)
        }
        return PokemonDetails(
            height = height,
            weight = weight,
            pokemonTypes = pokemonTypeList,
            imageUrls = pokemonImageUrlList,
            abilities = pokemonAbilities,
            stat = pokemonStats
        )
    }
}