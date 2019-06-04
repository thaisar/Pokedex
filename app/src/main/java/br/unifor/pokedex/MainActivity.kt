package br.unifor.pokedex

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.SearchView
import android.util.Log
import android.view.Menu
import android.view.View
import android.widget.Toast
import br.unifor.pokedex.adapter.ItemClickHandle
import br.unifor.pokedex.adapter.OnBottomReachedListener
import br.unifor.pokedex.adapter.PokemonListAdapter
import br.unifor.pokedex.model.PokemonList
import br.unifor.pokedex.service.PokeApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity(), ItemClickHandle, OnBottomReachedListener, SearchView.OnQueryTextListener {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: PokemonListAdapter
    private lateinit var layoutManager: LinearLayoutManager

    private var state: Parcelable? = null

    private lateinit var pokemonList: PokemonList
    private lateinit var pokemonListResults: ArrayList<PokemonList.Results>
    var listAux:List<PokemonList.Results> = ArrayList<PokemonList.Results>()


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

        val pokemonListS = PokeApiService.getPokemonListService().getPokemonList(0, 720)
        pokemonListS.enqueue(pokemonListCallbackHandler)

    }

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
//        val intent = Intent(this, PokemonProfileActivity::class.java)
//        val pokemonid = pokemonListResults[position].url.drop(34).dropLast(1).toInt()
//        intent.putExtra("pokemonId", pokemonid)
//        startActivity(intent)

        val i = Intent(this, PokemonProfileActivity::class.java)

        if (listAux.isEmpty()){
            val pokemonId = pokemonListResults[position].name
            i.putExtra("pokemonId", pokemonId)
        }else {
            val pokemonId = listAux[position].name
            i.putExtra("pokemonId", pokemonId)

        }

        startActivity(i)
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

    @Override
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        getMenuInflater().inflate(R.menu.menu_item, menu)

        val menuItem = menu?.findItem(R.id.search)
        val searchView = menuItem?.actionView as SearchView
        searchView.setOnQueryTextListener(this)

        return true
    }

    override fun onQueryTextSubmit(p0: String?): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onQueryTextChange(p0: String?): Boolean {

        val userInput = p0.toString().toLowerCase()
        val newList: ArrayList<PokemonList.Results> = ArrayList<PokemonList.Results>()
        var cont = 0

        Toast.makeText(this, p0, Toast.LENGTH_SHORT).show()

        for (Pokemon in pokemonListResults) {

            if(pokemonListResults[cont].name.toLowerCase().contains(userInput)) {

                newList.add(pokemonListResults[cont])
            }
            cont++
        }

        if (userInput == ""){
            adapter.updateList(pokemonListResults)
        } else {
            listAux = newList
            adapter.updateList(newList)
        }

        return true
    }
}
