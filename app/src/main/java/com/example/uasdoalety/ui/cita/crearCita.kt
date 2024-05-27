package com.example.uasdoalety.ui.cita

import android.app.DatePickerDialog
import android.content.ContentValues
import android.os.Bundle
import android.widget.Button
import android.widget.RadioButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.uasdoalety.R
import com.example.uasdoalety.basededatos.BaseDeDatos
import java.util.Calendar

class crearCita : AppCompatActivity() {

    private lateinit var estiloUnasSeleccionado: String
    private lateinit var precioUnasSeleccionado: String
    private lateinit var selectDateButton: Button
    private lateinit var dbHelper: BaseDeDatos
    private var usuarioEmail: String = ""
    private var usuarioHaIniciadoSesion: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.crearcita)

        dbHelper = BaseDeDatos(this)

        usuarioEmail = intent.getStringExtra("USUARIO_EMAIL") ?: ""
        usuarioHaIniciadoSesion = !usuarioEmail.isNullOrEmpty()

        selectDateButton = findViewById(R.id.selectDateButton)

        val radioButtonUñasTrasparentes = findViewById<RadioButton>(R.id.radioButonUñastrasparentes)
        val radioButtonUñasMulticolor = findViewById<RadioButton>(R.id.radioButonUñasMulticolor)
        val radioButtonUñasMyr = findViewById<RadioButton>(R.id.radioButonUñasMyr)
        val radioButtonUñasOndas = findViewById<RadioButton>(R.id.radioButonUñasOndas)

        radioButtonUñasTrasparentes.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                estiloUnasSeleccionado = "Normales"
                precioUnasSeleccionado = "$23.000"
            }
        }

        radioButtonUñasMulticolor.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                estiloUnasSeleccionado = "Multicolor"
                precioUnasSeleccionado = "$28.000"
            }
        }

        radioButtonUñasMyr.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                estiloUnasSeleccionado = "Rosas con morado"
                precioUnasSeleccionado = "$25.000"
            }
        }

        radioButtonUñasOndas.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                estiloUnasSeleccionado = "Ondas"
                precioUnasSeleccionado = "$30.000"
            }
        }

        selectDateButton.setOnClickListener {
            showDatePickerDialog()
        }

        val sendButton = findViewById<Button>(R.id.sendButton)
        sendButton.setOnClickListener {
            if (!usuarioHaIniciadoSesion) {
                Toast.makeText(this, "Por favor, inicia sesión para programar una cita", Toast.LENGTH_SHORT).show()
            } else {
                enviarDatos()
            }
        }
    }

    fun showDatePickerDialog() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(this, { _, selectedYear, selectedMonth, selectedDay ->
            val selectedDate = "$selectedDay/${selectedMonth + 1}/$selectedYear"
            selectDateButton.text = selectedDate
        }, year, month, day)

        datePickerDialog.datePicker.minDate = calendar.timeInMillis
        datePickerDialog.show()
    }

    private fun enviarDatos() {
        val fecha = selectDateButton.text.toString()

        if (::estiloUnasSeleccionado.isInitialized && fecha.isNotEmpty() && usuarioEmail.isNotEmpty()) {
            val db = dbHelper.writableDatabase
            val values = ContentValues().apply {
                put(BaseDeDatos.COLUMN_NAME_FECHA, fecha)
                put(BaseDeDatos.COLUMN_NAME_TIPO, estiloUnasSeleccionado)
                put(BaseDeDatos.COLUMN_NAME_PRECIO, precioUnasSeleccionado)
            }

            val whereClause = "${BaseDeDatos.COLUMN_NAME_EMAIL} = ?"
            val whereArgs = arrayOf(usuarioEmail)
            val rowCount = db.update(BaseDeDatos.TABLE_NAME, values, whereClause, whereArgs)

            if (rowCount > 0) {
                Toast.makeText(this, "Cita creada exitosamente", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "No se encontró la cita para actualizar", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(this, "Seleccione un estilo de uñas y una fecha", Toast.LENGTH_SHORT).show()
        }
    }
}