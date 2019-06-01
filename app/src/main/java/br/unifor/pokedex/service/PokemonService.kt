package br.unifor.pokedex.service

import br.unifor.pokedex.model.Pokemon
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface PokemonService {

    @GET("pokemon/{id}/")
    fun getPokemon(@Path("id") id: Int): Call<Pokemon>

    @GET("pokemon/{name}/")
    fun getPokemon(@Path("name") name: String): Call<Pokemon>

}
