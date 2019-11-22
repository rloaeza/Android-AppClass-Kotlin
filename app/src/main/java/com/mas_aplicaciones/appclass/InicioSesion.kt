package com.mas_aplicaciones.appclass


import android.database.Cursor
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.core.text.set
import androidx.navigation.Navigation
import com.mas_aplicaciones.appclass.modelo.Usuario
import kotlinx.android.synthetic.main.fragment_inicio_sesion.*

/**
 * A simple [Fragment] subclass.
 */
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
            Navigation.findNavController(it).navigate(R.id.action_inicioSesion_to_registroInstructor)
            etUsuario.text.clear()
            etClave.text.clear()
        }

        bEntrar.setOnClickListener( {
            if( existeUsuario() != null) {
                Navigation.findNavController(it).navigate(R.id.action_inicioSesion_to_listadoMaterias)
                etUsuario.text.clear()
                etClave.text.clear()

            }
            else {
                Toast.makeText(context, "Usuario y/o Clave erróneos", Toast.LENGTH_SHORT).show()

            }
        })
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
