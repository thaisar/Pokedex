package br.unifor.pokedex.adapter

import android.content.Context
import android.support.constraint.ConstraintLayout
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import br.unifor.pokedex.R
import br.unifor.pokedex.model.PokemonList
import com.squareup.picasso.Picasso


class PokemonListAdapter(context: Context, private val pokemonList: ArrayList<PokemonList.Results>) :
    RecyclerView.Adapter<PokemonListAdapter.PokemonViewHolder>() {

    var listener: ItemClickHandle? = null

    private val layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonViewHolder {
        val itemView = layoutInflater.inflate(R.layout.card_pokemon, parent, false)
        return PokemonViewHolder(itemView, listener!!)
    }

    override fun getItemCount(): Int {
        return pokemonList.size
    }

    override fun onBindViewHolder(holder: PokemonViewHolder, position: Int) {
        if (pokemonList.isNotEmpty()) {
            holder.name.text = pokemonList[position].name
            val num = pokemonList[position].url.drop(34).dropLast(1)
            Picasso.get()
                .load("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/$num.png")
                .resize(250, 250)
                .centerCrop()
                .placeholder(R.drawable.question_mark)
                .into(holder.image)
        }
    }

    class PokemonViewHolder(item: View, listener: ItemClickHandle) : RecyclerView.ViewHolder(item) {

        var image: ImageView = item.findViewById(R.id.imageViewCard)
        var name: TextView = item.findViewById(R.id.textViewCard)

        init {
            val layout = item.findViewById<ConstraintLayout>(R.id.card_constraintLayout)
            layout.setOnClickListener {
                listener.onClick(it, adapterPosition)
            }

            layout.setOnLongClickListener {
                listener.onLongClick(it, adapterPosition)
                true
            }
        }
    }
}