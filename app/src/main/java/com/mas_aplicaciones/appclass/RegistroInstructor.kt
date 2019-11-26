package com.mas_aplicaciones.appclass


import android.content.ContentValues
import android.database.Cursor
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_registro_instructor.*


//
//  RegistroInstructor.kt
//  AppClass
//
//  Created by Roberto Loaeza Valerio on 2019-11-26.
//  Copyright © 2019 Roberto Loaeza Valerio. All rights reserved.
//
class RegistroInstructor : Fragment() {



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_registro_instructor, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        etUsuario.setText(arguments?.getString("usuario"))
        etClave1.setText(arguments?.getString("clave"))
        bCancelar.setOnClickListener {  Navigation.findNavController(it).popBackStack() }
        bRegistrar.setOnClickListener {
            if(
                etNombreCompleto.text.isEmpty()
                or etCorreo.text.isEmpty()
                or etTelefono.text.isEmpty()
                or etCorreo.text.isEmpty()
                or etUsuario.text.isEmpty()
                or etClave1.text.isEmpty()
                or etClave2.text.isEmpty()

            ) {
                Snackbar.make(it, R.string.error_faltan_datos, Snackbar.LENGTH_LONG).show()
            }
            else {
                if( etClave1.text.toString().equals(etClave2.text.toString()) ) {
                    //buscar usuario y si no existe crearlo
                    if( !existeUsuario() ) {
                        insertarUsuario()
                        Navigation.findNavController(it).popBackStack()
                    }
                    else {
                        Snackbar.make(it, R.string.error_usuario_existe, Snackbar.LENGTH_LONG).show()
                    }
                }
                else {
                    Snackbar.make(it, R.string.error_claves_no_coinciden, Snackbar.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun insertarUsuario() {
        val baseDatos = BaseDatos(context!!).writableDatabase
        var valores: ContentValues = ContentValues()
        valores.put("nombreCompleto", etNombreCompleto.text.toString())
        valores.put("correoElectronico", etCorreo.text.toString())
        valores.put("telefono", etTelefono.text.toString())
        valores.put("usuario", etUsuario.text.toString())
        valores.put("clave", etClave1.text.toString())
        baseDatos.insert("usuarios", null, valores)
    }

    private fun existeUsuario(): Boolean {
        val baseDatos = BaseDatos(context!!).readableDatabase
        val sql: String = String.format("SELECT * FROM usuarios WHERE usuario='%s'", etUsuario.text.toString())
        val fila: Cursor = baseDatos.rawQuery(sql, null)
        return fila.moveToFirst()
    }
}
