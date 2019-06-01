package br.unifor.pokedex.model

data class PokemonList(
    val count: Int,
    val next: String?,
    val previous: String?,
    val results: ArrayList<Results>
) {

    data class Results(
        val name: String,
        val url: String
    )
}