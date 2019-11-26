package com.mas_aplicaciones.appclass

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.mas_aplicaciones.appclass.modelo.Usuario

//
//  BaseDatos.kt
//  AppClass
//
//  Created by Roberto Loaeza Valerio on 2019-11-26.
//  Copyright © 2019 Roberto Loaeza Valerio. All rights reserved.
//

class BaseDatos(context: Context) : SQLiteOpenHelper(context, dbName, null, dbVersion) {

    companion object {
        const val dbVersion = 1
        const val dbName = "appclass.db"

        var usuarioAppClass: Usuario? = null

        val dbUsuarios: String = "CREATE TABLE IF NOT EXISTS usuarios (" +
                "id INTEGER PRIMARY KEY autoincrement," +
                "nombreCompleto TEXT," +
                "correoElectronico TEXT," +
                "telefono TEXT," +
                "usuario TEXT," +
                "clave TEXT" +
                ")"

    }




    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(dbUsuarios)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }








}