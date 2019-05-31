package br.unifor.pokedex

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import br.unifor.pokedex.Adapter.CardAdapter
import kotlinx.android.synthetic.main.card_pokemon.*
import kotlinx.android.synthetic.main.card_pokemon.view.*

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    lateinit var  adapter: CardAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        recyclerView = findViewById(R.id.main_recyclerView)
        adapter = CardAdapter(this, Card(null, "home"))
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        navView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)

    }

    @SuppressLint("ResourceAsColor")
    private val onNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {

                cardView.textViewCard.setText("home")
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_dashboard -> {

                cardView.textViewCard.setText("dashboard")
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_notifications -> {

                cardView.textViewCard.setText("notifications")
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }
}
