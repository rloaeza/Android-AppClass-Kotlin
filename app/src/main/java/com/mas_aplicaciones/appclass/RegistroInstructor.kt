package com.mas_aplicaciones.appclass


import android.content.ContentValues
import android.database.Cursor
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.mas_aplicaciones.appclass.modelo.Usuario
import kotlinx.android.synthetic.main.fragment_registro_instructor.*
import kotlinx.coroutines.newFixedThreadPoolContext


/**
 * A simple [Fragment] subclass.
 */
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
                Toast.makeText(context, "Faltan datos", Toast.LENGTH_SHORT).show()
            }
            else {
                if( etClave1.text.toString().equals(etClave2.text.toString()) ) {
                    //buscar usuario y si no existe crearlo
                    var usuario = existeUsuario()
                    if( usuario == null ) {
                        insertarUsuario()
                        Navigation.findNavController(it).popBackStack()
                    }
                    else {
                        Toast.makeText(context, "El usuario ya existe en la bd", Toast.LENGTH_SHORT).show()
                    }
                }
                else {
                    Toast.makeText(context, "Las claves no coinciden", Toast.LENGTH_SHORT).show()
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

    private fun existeUsuario(): Usuario? {
        val baseDatos = BaseDatos(context!!).readableDatabase
        val sql: String = String.format("SELECT * FROM usuarios WHERE usuario='%s'", etUsuario.text.toString())
        val fila: Cursor = baseDatos.rawQuery(sql, null)

        if( fila.moveToFirst()) {
            val usuario: Usuario = Usuario(
                fila.getInt(fila.getColumnIndex("id")),
                fila.getString(fila.getColumnIndex("nombreCompleto")),
                fila.getString(fila.getColumnIndex("correoElectronico")),
                fila.getString(fila.getColumnIndex("telefono")),
                fila.getString(fila.getColumnIndex("usuario")),
                fila.getString(fila.getColumnIndex("clave"))
            )
             return usuario
        }
        else {
            return null
        }
    }
}
