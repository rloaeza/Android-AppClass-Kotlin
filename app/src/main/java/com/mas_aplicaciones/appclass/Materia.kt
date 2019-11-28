package com.mas_aplicaciones.appclass


import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mas_aplicaciones.appclass.modelo.Materia
import kotlinx.android.synthetic.main.fragment_materia.*

//
//  Materia.kt
//  AppClass
//
//  Created by Roberto Loaeza Valerio on 2019-11-26.
//  Copyright © 2019 Roberto Loaeza Valerio. All rights reserved.
//
class Materia : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_materia, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val materia: Materia = arguments?.get("materia") as Materia
        tvNombre.setText(materia.nombre)
        //ivLogo.setImageBitmap(BitmapFactory.decodeByteArray(materia.logo,0, materia.logo.size))
        ivLogo.setImageURI(materia.logo)
        tvDescripcion.setText(materia.descripcion)
    }
}
