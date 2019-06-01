package br.unifor.pokedex.service

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object PokeApiService {

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("https://pokeapi.co/api/v2/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun getPokemonService():PokemonService {

        return retrofit.create(PokemonService::class.java)
    }
}