package com.altokloud.app

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.altokloud.app.database.DatabaseHelper // Importa tu Base de Datos

class MainActivity : AppCompatActivity() {

    // Declaración de variables (usando 'db' como lo tienes)
    private lateinit var etEmail: EditText
    private lateinit var etPassword: EditText
    private lateinit var btnIngresar: Button
    private lateinit var btnRegistrarse: Button
    private lateinit var db: DatabaseHelper // La variable de tu DB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 1. Inicialización de la Base de Datos
        db = DatabaseHelper(this)

        // 2. Referenciar e Inicializar vistas (findViewById)
        etEmail = findViewById(R.id.etEmail)
        etPassword = findViewById(R.id.etPassword)
        btnIngresar = findViewById(R.id.btnIngresar)
        btnRegistrarse = findViewById(R.id.btnRegistrarse)

        // 3. Lógica del botón INGRESAR
        btnIngresar.setOnClickListener {
            // Usamos .trim() para quitar espacios y evitar problemas de login
            val email = etEmail.text.toString().trim()
            val password = etPassword.text.toString().trim()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Llamada a la función que SÍ existe en tu DatabaseHelper.kt
            if (db.verificarLogin(email, password)) {
                // Login exitoso
                val intent = Intent(this, PrincipalActivity::class.java)
                intent.putExtra("EMAIL", email)
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(this, "Credenciales incorrectas", Toast.LENGTH_SHORT).show()
            }
        }

        // 4. Lógica del botón REGISTRARSE (AÑADIMOS la lógica de registro)
        btnRegistrarse.setOnClickListener {

            Toast.makeText(this, "Función de Registro Pendiente", Toast.LENGTH_SHORT).show()
        }
    }
}