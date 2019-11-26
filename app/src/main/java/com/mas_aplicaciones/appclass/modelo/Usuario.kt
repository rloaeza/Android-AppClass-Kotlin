package com.mas_aplicaciones.appclass.modelo

import java.io.Serializable

//
//  Usuario.kt
//  AppClass
//
//  Created by Roberto Loaeza Valerio on 2019-11-26.
//  Copyright © 2019 Roberto Loaeza Valerio. All rights reserved.
//

open class Usuario (open val id: Int,
                    open val nombreCompleto: String,
                    open val correoElectronico: String,
                    open val telefono: String,
                    open val usuario: String,
                    open val clave: String)  : Serializable {



}