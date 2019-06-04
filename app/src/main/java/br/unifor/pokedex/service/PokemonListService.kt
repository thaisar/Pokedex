package br.unifor.pokedex.service

import br.unifor.pokedex.model.PokemonList
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface PokemonListService {

    @GET("pokemon/")
    fun getPokemonList(): Call<PokemonList>

    @GET("pokemon/")
    fun getPokemonList(@Query("offset") offset: Int, @Query("limit") limit: Int): Call<PokemonList>


}