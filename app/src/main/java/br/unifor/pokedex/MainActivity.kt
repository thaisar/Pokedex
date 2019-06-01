package br.unifor.pokedex

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import br.unifor.pokedex.adapter.ItemClickHandle
import br.unifor.pokedex.adapter.PokemonListAdapter
import br.unifor.pokedex.model.Pokemon
import br.unifor.pokedex.model.PokemonList
import br.unifor.pokedex.service.PokeApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity(), ItemClickHandle {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: PokemonListAdapter
    private lateinit var pokemonList: ArrayList<PokemonList.Results>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        pokemonList = arrayListOf()

        recyclerView = findViewById(R.id.main_recyclerView)

        adapter = PokemonListAdapter(this, pokemonList)
        adapter.listener = this

        recyclerView.layoutManager = LinearLayoutManager(this)
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
            R.id.navigation_dashboard -> {

//                cardView.textViewCard.text = "dashboard"
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_notifications -> {

//                cardView.textViewCard.text = "notifications"
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onClick(view: View, position: Int) {
        //TODO Proxima tela "Detalhes do Pokemon"
    }

    override fun onLongClick(view: View, position: Int) {
        //TODO "ideias?"
    }

    private val pokemonListCallbackHandler = object : Callback<PokemonList> {
        override fun onResponse(call: Call<PokemonList>, response: Response<PokemonList>) {
            val results = response.body()!!.results
            pokemonList.addAll(results)
            adapter.notifyItemRangeInserted(0, results.size)
        }

        override fun onFailure(call: Call<PokemonList>, t: Throwable) {
            Log.e("App", "Lascou!!!")
        }
    }

    private val pokemonCallbackHandler = object : Callback<Pokemon> {
        override fun onResponse(call: Call<Pokemon>, response: Response<Pokemon>) {

            Log.i("App", response.body()?.sprites?.frontDefault)
        }

        override fun onFailure(call: Call<Pokemon>, t: Throwable) {
            Log.e("App", "Lascou!!!")
        }
    }
}
