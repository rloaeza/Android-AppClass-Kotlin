package com.mas_aplicaciones.appclass


import android.app.AlertDialog
import android.content.ContentValues
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.google.android.material.snackbar.Snackbar
import com.mas_aplicaciones.appclass.modelo.Usuario
import kotlinx.android.synthetic.main.fragment_registro_instructor.*
import kotlinx.android.synthetic.main.fragment_registro_materia.*
import kotlinx.android.synthetic.main.fragment_registro_materia.bCancelar
import kotlinx.android.synthetic.main.fragment_registro_materia.bRegistrar
import java.io.ByteArrayOutputStream

//
//  RegistroMateria.kt
//  AppClass
//
//  Created by Roberto Loaeza Valerio on 2019-11-27.
//  Copyright © 2019 Roberto Loaeza Valerio. All rights reserved.
//
class RegistroMateria : Fragment() {

    private val GALERIA = 1
    private val CAMARA = 2

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_registro_materia, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val usuario: Usuario = arguments?.get("usuario") as Usuario

        bCancelar.setOnClickListener{
            Navigation.findNavController(it).popBackStack()
        }
        bMateriaSeleccionaImagen.setOnClickListener {
            val dialog = AlertDialog.Builder(context)
            dialog.setTitle(getString(R.string.imagen_seleccionar))
            val dialogItems = arrayOf(getString(R.string.imagen_galeria), getString(R.string.imagen_camara))
            dialog.setItems(dialogItems) {
                dialog, which ->
                    when(which){
                        0 ->  {
                            val galeriaIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                            startActivityForResult(galeriaIntent, GALERIA)
                        }
                        1 -> {
                            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                            startActivityForResult(intent, CAMARA)
                        }
                    }
            }
            dialog.show()

        }

        bRegistrar.setOnClickListener {

            if( etMateriaNombre.text.isEmpty()
                or etMateriaDescripcion.text.isEmpty()) {
                Snackbar.make(it, R.string.error_faltan_datos, Snackbar.LENGTH_LONG).show()
            }
            else {
                val logo: Bitmap = (ivMateriaLogo.drawable as BitmapDrawable).bitmap
                val stream: ByteArrayOutputStream = ByteArrayOutputStream()
                logo.compress(Bitmap.CompressFormat.PNG, 0, stream)
                insertarMateria(stream, usuario.id)
                Snackbar.make(it, R.string.registro_exitoso, Snackbar.LENGTH_LONG).show()
                Navigation.findNavController(it).popBackStack()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when(requestCode) {
            GALERIA -> {
                if( data != null) {
                    val imagenData = data!!.data
                    ivMateriaLogo.setImageURI(imagenData)
                }
            }
            CAMARA -> {
                val imagenData = data!!.extras!!.get("data") as Bitmap
                ivMateriaLogo.setImageBitmap(imagenData)
            }

        }
    }


    private fun insertarMateria(logo: ByteArrayOutputStream, idUsuario: Int) {
        val baseDatos = BaseDatos(context!!).writableDatabase
        var valores: ContentValues = ContentValues()
        valores.put("nombre", etMateriaNombre.text.toString())
        valores.put("descripcion", etMateriaDescripcion.text.toString())
        valores.put("logo", logo.toByteArray())
        valores.put("idUsuario", idUsuario)
        baseDatos.insert("materias", null, valores)
    }
}
