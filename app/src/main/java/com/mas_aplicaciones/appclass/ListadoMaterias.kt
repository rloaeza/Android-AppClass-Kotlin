package com.mas_aplicaciones.appclass


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.google.android.material.snackbar.Snackbar
import com.mas_aplicaciones.appclass.modelo.Usuario
import kotlinx.android.synthetic.main.fragment_listado_materias.*

//
//  ListadoMaterias.kt
//  AppClass
//
//  Created by Roberto Loaeza Valerio on 2019-11-26.
//  Copyright © 2019 Roberto Loaeza Valerio. All rights reserved.
//
class ListadoMaterias : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_listado_materias, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val usuario: Usuario = arguments?.get("usuario") as Usuario
        Snackbar.make(view, String.format(getString(R.string.msg_bienvenido), usuario.nombreCompleto), Snackbar.LENGTH_LONG).show()


        bSalir.setOnClickListener {
            Navigation.findNavController(it).popBackStack()
        }
    }
}
