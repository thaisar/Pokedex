package br.unifor.pokedex

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import br.unifor.pokedex.model.Pokemon
import br.unifor.pokedex.service.PokeApiService
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PokemonProfileActivity: AppCompatActivity() {

    private lateinit var pokemonProfile: Pokemon
    private lateinit var image: ImageView
    private lateinit var name: TextView
    private lateinit var type1: ImageView
    private lateinit var type2: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pokemon_profile)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)

        image = findViewById(R.id.imageViewPokemonProfile)
        name = findViewById(R.id.textViewPokemonProfile)
        type1 = findViewById(R.id.imageViewType1)
        type2 = findViewById(R.id.imageViewType2)

        val pokemonId = intent.getStringExtra("pokemonId")

//        val pokemonTeste = PokeApiService.getPokemonService().getPokemon(pokemonId)

        val pokemon = PokeApiService.getPokemonService().getPokemon(pokemonId)
        pokemon.enqueue(pokemonCallbackHandler)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if(item!!.itemId == android.R.id.home){
            onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private val pokemonCallbackHandler = object : Callback<Pokemon> {
        override fun onResponse(call: Call<Pokemon>, response: Response<Pokemon>) {
            pokemonProfile = response.body()!!
            name.text = pokemonProfile.name.capitalize()

            val type1s: String?
            val type2s: String?

            if(pokemonProfile.types.size == 2){
                type1s = pokemonProfile.types[1].type.name
                type2s = pokemonProfile.types[0].type.name
            }else{
                type1s = pokemonProfile.types[0].type.name
                type2s = ""
            }

            val type1i = when(type1s){
                "bug" -> R.drawable.type_bug
                "dark" -> R.drawable.type_dark
                "dragon" -> R.drawable.type_dragon
                "electric" -> R.drawable.type_electric
                "fairy" -> R.drawable.type_fairy
                "fighting" -> R.drawable.type_fighting
                "fire" -> R.drawable.type_fire
                "flying" -> R.drawable.type_flying
                "ghost" -> R.drawable.type_ghost
                "grass" -> R.drawable.type_grass
                "ground" -> R.drawable.type_ground
                "ice" -> R.drawable.type_ice
                "normal" -> R.drawable.type_normal
                "poison" -> R.drawable.type_poison
                "psychic" -> R.drawable.type_psychic
                "rock" -> R.drawable.type_rock
                "steel" -> R.drawable.type_steel
                "water" -> R.drawable.type_water
                else -> R.drawable.type_none
            }
            val type2i = when(type2s){
                "bug" -> R.drawable.type_bug
                "dark" -> R.drawable.type_dark
                "dragon" -> R.drawable.type_dragon
                "electric" -> R.drawable.type_electric
                "fairy" -> R.drawable.type_fairy
                "fighting" -> R.drawable.type_fighting
                "fire" -> R.drawable.type_fire
                "flying" -> R.drawable.type_flying
                "ghost" -> R.drawable.type_ghost
                "grass" -> R.drawable.type_grass
                "ground" -> R.drawable.type_ground
                "ice" -> R.drawable.type_ice
                "normal" -> R.drawable.type_normal
                "poison" -> R.drawable.type_poison
                "psychic" -> R.drawable.type_psychic
                "rock" -> R.drawable.type_rock
                "steel" -> R.drawable.type_steel
                "water" -> R.drawable.type_water
                else -> R.drawable.type_none
            }

            Picasso.get()
                .load(type1i)
                .fit().centerCrop()
                .into(type1)

            Picasso.get()
                .load(type2i)
                .fit().centerCrop()
                .into(type2)


            val num = pokemonProfile.id
            Picasso.get()
                //.load("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/$num.png")
                .load("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other-sprites/official-artwork/$num.png")
                .fit()
                .centerCrop()
                .placeholder(R.drawable.question_mark)
                .error(R.drawable.question_mark)
                .into(image)
        }

        override fun onFailure(call: Call<Pokemon>, t: Throwable) {
            Log.e("App", "Lascou!!!")
        }
    }
}