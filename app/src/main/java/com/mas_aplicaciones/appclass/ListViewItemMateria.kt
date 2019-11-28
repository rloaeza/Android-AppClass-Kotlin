package com.mas_aplicaciones.appclass

import android.content.Context
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.navigation.Navigation
import com.mas_aplicaciones.appclass.modelo.Materia

// 
//  ListViewItemMateria.kt
//  Android-AppClass-Kotlin
//
//  Created by Roberto Loaeza Valerio on 2019-11-28.
//  Copyright © 2019 Roberto Loaeza Valerio. All rights reserved.
//
class ListViewItemMateria(var context: Context, var lista: ArrayList<Materia>): BaseAdapter() {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var view = convertView
        if( view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.listview_item_materia, parent, false)

        }
        val materia: Materia = getItem(position) as Materia

        val tvNombre = view!!.findViewById<TextView>(R.id.tvNombre)
        tvNombre.setText(materia.nombre)
        val tvDescripcion = view!!.findViewById<TextView>(R.id.tvDescripcion)
        tvDescripcion.setText(materia.descripcion)

        val ivLogo = view!!.findViewById<ImageView>(R.id.ivLogo)
        //ivLogo.setImageBitmap(BitmapFactory.decodeByteArray(materia.logo,0, materia.logo.size))
        ivLogo.setImageURI(materia.logo)

        view.setOnClickListener{
            var bundle = bundleOf("materia" to materia)
            Navigation.findNavController(view).navigate(R.id.action_listadoMaterias_to_materia, bundle)
        }
        return view!!

    }

    override fun getItem(position: Int): Any {
        return lista[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return lista.size
    }

}