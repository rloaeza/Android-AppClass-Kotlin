package com.mas_aplicaciones.appclass


import android.database.Cursor
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.os.bundleOf
import androidx.navigation.Navigation
import com.google.android.material.snackbar.Snackbar
import com.mas_aplicaciones.appclass.modelo.Usuario
import com.mas_aplicaciones.appclass.modelo.Materia
import kotlinx.android.synthetic.main.fragment_inicio_sesion.*
import kotlinx.android.synthetic.main.fragment_listado_materias.*

//
//  ListadoMaterias.kt
//  AppClass
//
//  Created by Roberto Loaeza Valerio on 2019-11-26.
//  Copyright © 2019 Roberto Loaeza Valerio. All rights reserved.
//
class ListadoMaterias : Fragment() {

    val listaMaterias = arrayListOf<Materia>()
    lateinit var usuario: Usuario

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_listado_materias, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        usuario = arguments?.get("usuario") as Usuario

        val adapter = ArrayAdapter(context!!,  android.R.layout.simple_expandable_list_item_1, listaMaterias)
        lvMaterias.adapter = adapter
        cargarMaterias()



        bSalir.setOnClickListener {
            Navigation.findNavController(it).popBackStack()
        }

        bAgregarMateria.setOnClickListener {
            var bundle = bundleOf("usuario" to usuario)
            Navigation.findNavController(it).navigate(R.id.action_listadoMaterias_to_registroMateria, bundle)
        }
    }

    private fun cargarMaterias() {
        listaMaterias.clear()
        val baseDatos = BaseDatos(context!!).readableDatabase
        val sql: String = String.format(
            "SELECT * FROM materias WHERE idUsuario='%d'", usuario.id)
        val fila: Cursor = baseDatos.rawQuery(sql, null)

        if (fila.moveToFirst()) {
            do {

                val materia: Materia =  Materia(
                    fila.getInt(fila.getColumnIndex("id")),
                    fila.getString(fila.getColumnIndex("nombre")),
                    fila.getString(fila.getColumnIndex("descripcion")),
                    fila.getBlob(fila.getColumnIndex("logo")),
                    fila.getInt(fila.getColumnIndex("idUsuario"))
                    )
                listaMaterias.add(materia)

            } while( fila.moveToNext())

        }
    }

}
