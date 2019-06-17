package br.unifor.pokedex.adapter

import android.view.View
import android.widget.ImageView

interface ItemClickHandle {

    fun onClick(view: View, position: Int)

    fun onLongClick(view: View, position: Int)

}