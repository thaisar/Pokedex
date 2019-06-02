package br.unifor.pokedex

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import br.unifor.pokedex.adapter.ItemClickHandle
import br.unifor.pokedex.adapter.OnBottomReachedListener
import br.unifor.pokedex.adapter.PokemonListAdapter
import br.unifor.pokedex.model.PokemonList
import br.unifor.pokedex.service.PokeApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity(), ItemClickHandle, OnBottomReachedListener {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: PokemonListAdapter
    private lateinit var layoutManager: LinearLayoutManager

    private var state: Parcelable? = null

    private lateinit var pokemonList: PokemonList
    private lateinit var pokemonListResults: ArrayList<PokemonList.Results>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        pokemonListResults = arrayListOf()

        recyclerView = findViewById(R.id.main_recyclerView)

        adapter = PokemonListAdapter(this, pokemonListResults)
        adapter.itemClickHandleListener = this
        adapter.onBottomReachedListener = this

        layoutManager = LinearLayoutManager(this)

        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter

        navView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)

        val pokemonListS = PokeApiService.getPokemonListService().getPokemonList(0, 807)
        pokemonListS.enqueue(pokemonListCallbackHandler)

    }


    @SuppressLint("ResourceAsColor")
    private val onNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {

//                cardView.textViewCard.text = "home"
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_favorites -> {

//                cardView.textViewCard.text = "favorites"
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onClick(view: View, position: Int) {
        val intent = Intent(this, PokemonProfileActivity::class.java)
        intent.putExtra("pokemonId", position+1)
        startActivityForResult(intent, 0)
    }

    override fun onLongClick(view: View, position: Int) {
        //TODO "ideias?"
    }

    override fun onBottomReached(position: Int) {
//        if(pokemonList.next != null){
//            val strings = pokemonList.next!!.split("https://pokeapi.co/api/v2/pokemon/?offset=", "&limit=")
//            val offset = strings[1].toInt()
//            val limit = strings[2].toInt()
//            val pokemonListS = PokeApiService.getPokemonListService().getPokemonList(offset, limit)
//            pokemonListS.enqueue(pokemonListCallbackHandler)
//        }
    }

    private val pokemonListCallbackHandler = object : Callback<PokemonList> {
        override fun onResponse(call: Call<PokemonList>, response: Response<PokemonList>) {
            pokemonList = response.body()!!
            pokemonListResults.addAll(pokemonList.results)
            adapter.notifyItemRangeInserted(pokemonListResults.size, pokemonList.results.size)
        }

        override fun onFailure(call: Call<PokemonList>, t: Throwable) {
            Log.e("App", "Lascou!!!")
        }
    }

}
