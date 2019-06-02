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
    private lateinit var type1: TextView
    private lateinit var type2: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pokemon_profile)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)

        image = findViewById(R.id.imageViewPokemonProfile)
        name = findViewById(R.id.textViewPokemonProfile)
        type1 = findViewById(R.id.textViewType1)
        type2 = findViewById(R.id.textViewType2)

        val pokemonId = intent.getIntExtra("pokemonId", 0)
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
            name.text = pokemonProfile.name

            if(pokemonProfile.types.size == 2){
                type1.text = pokemonProfile.types[1].type.name
                type2.text = pokemonProfile.types[0].type.name
            }else{
                type1.text = pokemonProfile.types[0].type.name
                type2.text = ""
            }

            val num = pokemonProfile.id
            Picasso.get()
                .load("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/$num.png")
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