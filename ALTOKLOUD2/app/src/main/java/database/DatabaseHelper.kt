package com.altokloud.app.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.altokloud.app.Opinion

class DatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, "altokloud.db", null, 1) {

    override fun onCreate(db: SQLiteDatabase?) {
        //ejecuta la primera vez que se accede a la base de datos de la aplicación
        // Crear tabla usuarios
        db?.execSQL("""
            CREATE TABLE usuarios (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                email TEXT UNIQUE,
                password TEXT
            )
        """
        )

        // Crear tabla opiniones
        db?.execSQL("""
            CREATE TABLE opiniones (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                email_usuario TEXT,
                opinion TEXT,
                fecha TEXT
            )
        """
        )

        // Usuario por defecto prueba
        db?.execSQL("INSERT INTO usuarios (email, password) VALUES ('admin@altokloud.com', 'admin123')")
    }

    //ejecuta si el número de versión de la base de datos cambia
    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS usuarios") //elimina
        db?.execSQL("DROP TABLE IF EXISTS opiniones")
        onCreate(db) //vuelve crear
    }

    // Verificar login permiten interactuar con los datos de las tablas
    fun verificarLogin(email: String, password: String): Boolean {
        val db = readableDatabase
        val cursor = db.rawQuery(
            "SELECT * FROM usuarios WHERE email=? AND password=?",
            arrayOf(email, password)
        )
        val existe = cursor.count > 0
        cursor.close()
        return existe
    }

    // Guardar opinión
    fun guardarOpinion(email: String, opinion: String): Boolean {
        val db = writableDatabase
        val values = ContentValues().apply {
            put("email_usuario", email)
            put("opinion", opinion)
            put("fecha", System.currentTimeMillis().toString())
        }
        val result = db.insert("opiniones", null, values)
        return result != -1L
    }

    // Obtener opiniones (para verificar)
    fun obtenerOpiniones(): List<String> {
        val lista = mutableListOf<String>() //devuelve, vacia
        val db = readableDatabase //leer
        val cursor = db.rawQuery("SELECT * FROM opiniones", null)

        if (cursor.moveToFirst()) { //bucle opinion cada fila
            do {
                val opinion = cursor.getString(cursor.getColumnIndexOrThrow("opinion"))
                lista.add(opinion)
            } while (cursor.moveToNext())
        }
        cursor.close()
        return lista
    }

    // Nueva función para obtener todas las opiniones con su ID
    fun getAllOpiniones(): List<Opinion> {
        val lista = mutableListOf<Opinion>() //todas las opiniones
        val db = readableDatabase  // base de datos legibles, almacena
        // Ordenamos por ID descendente para ver las más nuevas primero, orden almacen
        val cursor = db.rawQuery("SELECT id, opinion FROM opiniones ORDER BY id DESC", null)

        if (cursor.moveToFirst()) {  //si hay algo en el pedido
            do {
                val id = cursor.getLong(cursor.getColumnIndexOrThrow("id"))
                val opinionText = cursor.getString(cursor.getColumnIndexOrThrow("opinion"))
                lista.add(Opinion(id, opinionText)) //crea un objeto, caja
            } while (cursor.moveToNext())
        }
        cursor.close()
        return lista
    }

    // Nueva función para eliminar una opinión por su ID
    fun deleteOpinionById(id: Long) {
        val db = writableDatabase
        db.delete("opiniones", "id = ?", arrayOf(id.toString()))
    }
}