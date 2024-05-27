package com.example.uasdoalety.basededatos

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class BaseDeDatos(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "datos_usuarios.db"
        private const val DATABASE_VERSION = 1
        const val TABLE_NAME = "datos"
        const val COLUMN_NAME_ID = "id"
        const val COLUMN_NAME_NOMBRE = "nombre"
        const val COLUMN_NAME_EMAIL = "email"
        const val COLUMN_NAME_PASSWORD = "password"
        const val COLUMN_NAME_FECHA = "fecha"
        const val COLUMN_NAME_TIPO = "tipo"
        const val COLUMN_NAME_PRECIO = "precio"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createTableSQL = "CREATE TABLE $TABLE_NAME (" +
                "$COLUMN_NAME_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "$COLUMN_NAME_NOMBRE TEXT," +
                "$COLUMN_NAME_EMAIL TEXT," +
                "$COLUMN_NAME_PASSWORD TEXT," +
                "$COLUMN_NAME_FECHA TEXT," +
                "$COLUMN_NAME_TIPO TEXT," +
                "$COLUMN_NAME_PRECIO TEXT)"
        db.execSQL(createTableSQL)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
    }
}