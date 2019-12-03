package com.mas_aplicaciones.appclass.modelo

import android.net.Uri
import java.io.Serializable

// 
//  Alumno.kt
//  Android-AppClass-Kotlin
//
//  Created by Roberto Loaeza Valerio on 2019-12-03.
//  Copyright © 2019 Roberto Loaeza Valerio. All rights reserved.
//


open class Alumno ( open val id: Int,
                    open val nombre: String,
                    open val apellidos: String,
                    open val matricula: String,
                    open val correoElectronico: String,
                    open val telefono: String,
                    open val btMAC: String)  : Serializable {


    override fun toString(): String {
        return "$apellidos, $nombre"
    }
}