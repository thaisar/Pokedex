package br.unifor.pokedex

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.SearchView
import android.util.Log
import android.view.Menu
import android.view.View
import android.widget.ImageView
import br.unifor.pokedex.adapter.ItemClickHandle
import br.unifor.pokedex.adapter.OnBottomReachedListener
import br.unifor.pokedex.adapter.PokemonListAdapter
import br.unifor.pokedex.model.PokemonList
import br.unifor.pokedex.service.PokeApiService
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response





class MainActivity : AppCompatActivity(), ItemClickHandle, OnBottomReachedListener, SearchView.OnQueryTextListener {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: PokemonListAdapter
    private lateinit var layoutManager: LinearLayoutManager

    private lateinit var pokemonList: PokemonList
    private lateinit var pokemonListResults: ArrayList<PokemonList.Results>
    private lateinit var pokemonListResultsFavorite: ArrayList<PokemonList.Results>
    private lateinit var pokemonListResultsSearch: List<PokemonList.Results>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        pokemonListResults = arrayListOf()
        pokemonListResultsFavorite = arrayListOf()
        pokemonListResultsSearch = arrayListOf()

        recyclerView = findViewById(R.id.main_recyclerView)

        adapter = PokemonListAdapter(this, pokemonListResults)
        adapter.itemClickHandleListener = this
        adapter.onBottomReachedListener = this

        layoutManager = LinearLayoutManager(this)

        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter

        navView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)

        val pokemonListS = PokeApiService.getPokemonListService().getPokemonList(0, 720)
        pokemonListS.enqueue(pokemonListCallbackHandler)

    }

    private val onNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {

                adapter = PokemonListAdapter(this, pokemonListResults)
                recyclerView.adapter = adapter
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_favorites -> {

                adapter = PokemonListAdapter(this, pokemonListResultsFavorite)
                recyclerView.adapter = adapter
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onClick(view: View, position: Int) {

        val intent = Intent(this, PokemonProfileActivity::class.java)

        if (pokemonListResultsSearch.isEmpty()){
            val pokemonId = pokemonListResults[position].name
            intent.putExtra("pokemonId", pokemonId)
        }else {
            val pokemonId = pokemonListResultsSearch[position].name
            intent.putExtra("pokemonId", pokemonId)
        }

        startActivity(intent)
    }

    override fun onClickFavoriteIcon(view: View, position: Int) {
        //TODO
    }

    //não usado
    override fun onLongClick(view: View, position: Int) {
    }
    //não usado
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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menuInflater.inflate(R.menu.menu_item, menu)

        val menuItem = menu?.findItem(R.id.search)
        val searchView = menuItem?.actionView as SearchView
        searchView.setOnQueryTextListener(this)

        return true
    }

    override fun onQueryTextSubmit(p0: String?): Boolean {
        return false
    }

    override fun onQueryTextChange(p0: String?): Boolean {

        val userInput = p0.toString().toLowerCase()
        val newList: ArrayList<PokemonList.Results> = ArrayList()

        for ((cont, _) in pokemonListResults.withIndex()) {
            if(pokemonListResults[cont].name.toLowerCase().contains(userInput)) {
                newList.add(pokemonListResults[cont])
            }
        }

        if (userInput == ""){
            adapter.updateList(pokemonListResults)
            pokemonListResultsSearch = ArrayList()
        } else {
            pokemonListResultsSearch = newList
            adapter.updateList(newList)
        }

        return true
    }
}
