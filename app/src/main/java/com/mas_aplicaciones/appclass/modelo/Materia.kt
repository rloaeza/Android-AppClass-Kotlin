package com.mas_aplicaciones.appclass.modelo

import java.io.Serializable

//
//  Materia.kt
//  AppClass
//
//  Created by Roberto Loaeza Valerio on 2019-11-27.
//  Copyright © 2019 Roberto Loaeza Valerio. All rights reserved.
//

open class Materia (open val id: Int,
                    open val nombre: String,
                    open val descripcion: String,
                    open val logo: ByteArray,
                    open val idUsuario: Int)  : Serializable {


    override fun toString(): String {
        return nombre
    }
}