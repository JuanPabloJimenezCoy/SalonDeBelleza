package com.example.uasdoalety.ui.cita

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.uasdoalety.R
import com.example.uasdoalety.basededatos.BaseDeDatos

class iniciarSesion : AppCompatActivity() {
    private lateinit var dbHelper: BaseDeDatos

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.iniciarsesion)

        dbHelper = BaseDeDatos(this)

        val editTextCorreo = findViewById<EditText>(R.id.editTextCorreo)
        val editTextPassword = findViewById<EditText>(R.id.editTextPassword)
        val btnEnviar = findViewById<Button>(R.id.btnEnviar)
        val btnCrearUsuario = findViewById<Button>(R.id.btnCrearUsuario)

        btnEnviar.setOnClickListener {
            val correo = editTextCorreo.text.toString()
            val password = editTextPassword.text.toString()

            if (correo.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show()
            } else {
                if (verificarCredenciales(correo, password)) {
                    Toast.makeText(this, "Has iniciado sesión", Toast.LENGTH_SHORT).show()

                    val intent = Intent(this, crearCita::class.java)
                    intent.putExtra("USUARIO_EMAIL", correo)
                    startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(this, "Usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show()
                }
            }
        }
        btnCrearUsuario.setOnClickListener {
            val intent = Intent(this, crearUsuario::class.java)
            startActivity(intent)
        }
    }
    private fun verificarCredenciales(correo: String, password: String): Boolean {
        val db = dbHelper.readableDatabase
        val query = "SELECT * FROM ${BaseDeDatos.TABLE_NAME} WHERE ${BaseDeDatos.COLUMN_NAME_EMAIL} = ? AND ${BaseDeDatos.COLUMN_NAME_PASSWORD} = ?"
        val cursor = db.rawQuery(query, arrayOf(correo, password))
        val existeUsuario = cursor.count > 0
        cursor.close()
        return existeUsuario
    }
}