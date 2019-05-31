package br.unifor.pokedex.Adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import br.unifor.pokedex.Card
import br.unifor.pokedex.R

class CardAdapter(val context: Context, val card: Card) : RecyclerView.Adapter<CardAdapter.UserViewHolder>() {

//    var listener: ItemClickHandle?=null

    val layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater


    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): CardAdapter.UserViewHolder {

        val itemView = layoutInflater.inflate(R.layout.card_pokemon, p0, false)
        val userViewHolder = UserViewHolder(itemView)

        return userViewHolder
    }

    override fun getItemCount(): Int {

        return 1
    }

    override fun onBindViewHolder(p0: CardAdapter.UserViewHolder, p1: Int) {

        val card = card

        p0.name.text = card.name
    }

    class UserViewHolder (item: View): RecyclerView.ViewHolder(item){

        var image:ImageView
        var name:TextView

        init {
            image = item.findViewById(R.id.imageViewCard)
            name = item.findViewById(R.id.textViewCard)

//            val layout = item.findViewById<ConstraintLayout>(R.id.card_constraintLayout)
//            layout.setOnClickListener {
//                listener.onClick(it, adapterPosition)
//            }
//
//            layout.setOnLongClickListener {
//                listener.onLongClick(it, adapterPosition)
//                true
//            }
        }
    }
}