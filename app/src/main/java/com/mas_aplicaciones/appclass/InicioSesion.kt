package com.mas_aplicaciones.appclass


import android.database.Cursor
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.Navigation
import com.google.android.material.snackbar.Snackbar
import com.mas_aplicaciones.appclass.modelo.Usuario
import kotlinx.android.synthetic.main.fragment_inicio_sesion.*

//
//  InicioSesion.kt
//  AppClass
//
//  Created by Roberto Loaeza Valerio on 2019-11-26.
//  Copyright © 2019 Roberto Loaeza Valerio. All rights reserved.
//
class InicioSesion : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_inicio_sesion, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bRegistrar.setOnClickListener {

            var bundle = bundleOf("usuario" to etUsuario.text.toString(), "clave" to etClave.text.toString() )
            Navigation.findNavController(it).navigate(R.id.action_inicioSesion_to_registroInstructor, bundle)
            etUsuario.text.clear()
            etClave.text.clear()
        }

        bEntrar.setOnClickListener {
            val usuario: Usuario? = existeUsuario()
            if( usuario != null) {
                var bundle = bundleOf("usuario" to usuario)
                Navigation.findNavController(it).navigate(R.id.action_inicioSesion_to_listadoMaterias, bundle)

                etUsuario.text.clear()
                etClave.text.clear()

            } else {
                Snackbar.make(it, R.string.error_usuario_y_o_clave, Snackbar.LENGTH_LONG).show()
            }
        }
    }

    private fun existeUsuario(): Usuario? {
        val baseDatos = BaseDatos(context!!).readableDatabase
        val sql: String = String.format(
            "SELECT * FROM usuarios WHERE usuario='%s' AND clave='%s'",
            etUsuario.text.toString(),
            etClave.text.toString()
        )
        val fila: Cursor = baseDatos.rawQuery(sql, null)

        if (fila.moveToFirst()) {
            val usuario: Usuario = Usuario(
                fila.getInt(fila.getColumnIndex("id")),
                fila.getString(fila.getColumnIndex("nombreCompleto")),
                fila.getString(fila.getColumnIndex("correoElectronico")),
                fila.getString(fila.getColumnIndex("telefono")),
                fila.getString(fila.getColumnIndex("usuario")),
                fila.getString(fila.getColumnIndex("clave"))
            )
            return usuario
        } else {
            return null
        }
    }
}
