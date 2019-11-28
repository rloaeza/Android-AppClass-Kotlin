package com.mas_aplicaciones.appclass


import android.app.AlertDialog
import android.content.ContentValues
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.graphics.scale
import androidx.navigation.Navigation
import com.google.android.material.snackbar.Snackbar
import com.mas_aplicaciones.appclass.modelo.Usuario
import kotlinx.android.synthetic.main.fragment_registro_instructor.*
import kotlinx.android.synthetic.main.fragment_registro_materia.*
import kotlinx.android.synthetic.main.fragment_registro_materia.bCancelar
import kotlinx.android.synthetic.main.fragment_registro_materia.bRegistrar
import java.io.*
import java.util.*

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
                insertarMateria(usuario.id)
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

    private fun saveImageToInternalStorage(drawableId:Int):Uri{
        // Get the image from drawable resource as drawable object
        //val drawable = ContextCompat.getDrawable(context!!,drawableId)

        // Get the bitmap from drawable object
        //val bitmap = (drawable as BitmapDrawable).bitmap


        var bitmap: Bitmap = ((ivMateriaLogo.drawable as BitmapDrawable).bitmap)
        val ancho: Int = bitmap.width
        val alto: Int = bitmap.height
        val nuevoAncho = 400
        val factor: Double = (nuevoAncho*100.0)/ancho

        Log.e("Imagen", "ancho=${ancho}, alto=${alto}, factor=${factor}")

        bitmap = bitmap.scale(nuevoAncho, (factor*alto/100).toInt())
        //val stream: ByteArrayOutputStream = ByteArrayOutputStream()
        //bitmap.compress(Bitmap.CompressFormat.PNG, 0, stream)


        // Get the context wrapper instance
        val wrapper = ContextWrapper(context!!)

        // Initializing a new file
        // The bellow line return a directory in internal storage
        var file = wrapper.getDir("images", Context.MODE_PRIVATE)


        // Create a file to save the image
        file = File(file, "${UUID.randomUUID()}.png")

        try {
            // Get the file output stream
            val stream: OutputStream = FileOutputStream(file)

            // Compress bitmap
            bitmap.compress(Bitmap.CompressFormat.PNG, 85, stream)

            Log.e("imagen", file.absolutePath)
            // Flush the stream
            stream.flush()

            // Close stream
            stream.close()
        } catch (e: IOException){ // Catch the exception
            e.printStackTrace()
        }

        // Return the saved image uri
        return Uri.parse(file.absolutePath)
    }
    private fun insertarMateria(idUsuario: Int) {





        val uri: Uri = saveImageToInternalStorage(ivMateriaLogo.id)
        val baseDatos = BaseDatos(context!!).writableDatabase
        var valores: ContentValues = ContentValues()
        valores.put("nombre", etMateriaNombre.text.toString())
        valores.put("descripcion", etMateriaDescripcion.text.toString())
        valores.put("logo", uri.toString())
        valores.put("idUsuario", idUsuario)
        baseDatos.insert("materias", null, valores)
    }
}
