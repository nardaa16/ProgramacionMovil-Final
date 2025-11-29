package com.altokloud.app

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.altokloud.app.database.DatabaseHelper

class PrincipalActivity : AppCompatActivity() {

    private lateinit var etOpinion: EditText
    private lateinit var btnEnviar: Button
    private lateinit var btnVerOpiniones: Button // Declaramos el nuevo botón

    private lateinit var db: DatabaseHelper
    private var emailUsuario = ""
   //inicializa
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_principal)   //carga diseño

        // Obtener email del usuario, ?
        emailUsuario = intent.getStringExtra("EMAIL") ?: ""

        // Inicializar base de datos
        db = DatabaseHelper(this)

        // Referenciar vistas, conectan
        etOpinion = findViewById(R.id.etOpinion)
        btnEnviar = findViewById(R.id.btnEnviar)
        btnVerOpiniones = findViewById(R.id.btnVerOpiniones) // Referenciamos el nuevo botón

        // Botón Enviar
        btnEnviar.setOnClickListener {
            val opinion = etOpinion.text.toString().trim()  //convierte en texto
            // Validar que el campo no esté vacío
            if (opinion.isEmpty()) {
                Toast.makeText(this, "Escribe una opinión", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            // Guardar la opinión en la base de datos
            if (db.guardarOpinion(emailUsuario, opinion)) {
                Toast.makeText(this, "¡Gracias por tu opinión!", Toast.LENGTH_LONG).show()
                etOpinion.text.clear()
            } else {
                Toast.makeText(this, "Error al guardar", Toast.LENGTH_SHORT).show()
            }
        }

        // Lógica del nuevo botón "Ver Opiniones"
        btnVerOpiniones.setOnClickListener { // Cuando se hace clic en el botón
            val intent = Intent(this, ListaOpinionesActivity::class.java) // Abre la nueva actividad
            startActivity(intent)  //solicitud
        }
    }
}
