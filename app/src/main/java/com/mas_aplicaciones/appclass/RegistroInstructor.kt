package com.mas_aplicaciones.appclass


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation
import kotlinx.android.synthetic.main.fragment_registro_instructor.*

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
                or etClave1.text.isEmpty()
                or etClave2.text.isEmpty()

            ) {
                Toast.makeText(context, "Faltan datos", Toast.LENGTH_SHORT).show()
            }
            else {
                if( etClave1.text.toString().equals(etClave2.text.toString()) ) {
                    //buscar usuario y si no existe crearlo
                    Navigation.findNavController(it).popBackStack()
                }
                else {
                    Toast.makeText(context, "Las claves no coinciden", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
