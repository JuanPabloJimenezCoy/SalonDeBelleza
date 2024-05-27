package com.example.uasdoalety.ui.cita

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.uasdoalety.MainActivity
import com.example.uasdoalety.R
import com.example.uasdoalety.basededatos.BaseDeDatos

class crearUsuario : AppCompatActivity() {

    private lateinit var dbHelper: BaseDeDatos

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.crearusuario)

        dbHelper = BaseDeDatos(this)

        val editTextNombre = findViewById<EditText>(R.id.editTextNombre)
        val editTextCorreo = findViewById<EditText>(R.id.editTextCorreo)
        val editTextPassword = findViewById<EditText>(R.id.editTextPassword)
        val editTextConfirmPassword = findViewById<EditText>(R.id.editTextConfirmPassword)
        val btnCrear = findViewById<Button>(R.id.btnCrear)

        btnCrear.setOnClickListener {
            val nombre = editTextNombre.text.toString()
            val correo = editTextCorreo.text.toString()
            val password = editTextPassword.text.toString()
            val confirmPassword = editTextConfirmPassword.text.toString()

            if (password == confirmPassword) {
                val inserted = insertarUsuario(nombre, correo, password)
                if (inserted) {
                    mostrarMensaje("Usuario creado exitosamente")
                    val intent = Intent(this, iniciarSesion::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    mostrarMensaje("Error al crear el usuario")
                }
            } else {
                mostrarMensaje("Las contraseñas no coinciden")
            }
        }
    }

    private fun insertarUsuario(nombre: String, correo: String, password: String): Boolean {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put(BaseDeDatos.COLUMN_NAME_NOMBRE, nombre)
            put(BaseDeDatos.COLUMN_NAME_EMAIL, correo)
            put(BaseDeDatos.COLUMN_NAME_PASSWORD, password)
        }
        val newRowId = db?.insert(BaseDeDatos.TABLE_NAME, null, values)
        return newRowId != -1L
    }

    private fun mostrarMensaje(mensaje: String) {
        Toast.makeText(this@crearUsuario, mensaje, Toast.LENGTH_SHORT).show()
    }
}