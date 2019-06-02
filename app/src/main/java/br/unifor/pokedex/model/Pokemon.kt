package br.unifor.pokedex.model

import com.google.gson.annotations.SerializedName

data class Pokemon(
    val id: Int,
    val name: String,
    @SerializedName("base_experience")
    val baseExperience: Int,
    val height: Int,
    val weight: Int,
    val sprites: Sprites,
    val types: List<PokemonType>
) {
    data class Sprites(
        @SerializedName("front_default")
        val frontDefault: String
    )

    data class PokemonType (
        val slot: Int,
        val type: NamedAPIResource
    )

    data class NamedAPIResource (
        val name: String,
        val url: String
    )
}

