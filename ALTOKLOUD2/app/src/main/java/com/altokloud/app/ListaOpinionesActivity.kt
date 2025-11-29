package com.altokloud.app

import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.altokloud.app.adapter.OpinionAdapter
import com.altokloud.app.database.DatabaseHelper

class ListaOpinionesActivity : AppCompatActivity() {

    private lateinit var db: DatabaseHelper
    private lateinit var rvOpiniones: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_opiniones)

        // 1. Inicializamos la base de datos y las vistas
        db = DatabaseHelper(this)
        rvOpiniones = findViewById(R.id.rvOpiniones)

        val ivVolver: ImageView = findViewById(R.id.ivVolver)

        // 2. Configuramos el LayoutManager. Esto solo se hace una vez.
        rvOpiniones.layoutManager = LinearLayoutManager(this)

        // 3. Cargamos las opiniones por primera vez
        cargarOpiniones()

        // 4. Configuramos el botón para volver
        ivVolver.setOnClickListener {
            finish() // Cierra la actividad actual y vuelve a la anterior
        }
    }

    private fun cargarOpiniones() {
        // Obtenemos la lista FRESCA de opiniones desde la base de datos
        val opiniones = db.getAllOpiniones()

        // Creamos un ADAPTER NUEVO con la lista fresca
        val adapter = OpinionAdapter(opiniones) { opinion ->
            // Lógica para eliminar la opinión
            eliminarOpinion(opinion)
        }

        // Asignamos el nuevo adapter al RecyclerView. Esto fuerza a la lista a redibujarse.
        rvOpiniones.adapter = adapter
    }

    private fun eliminarOpinion(opinion: Opinion) {
        // Eliminamos la opinión de la base de datos
        db.deleteOpinionById(opinion.id)

        // Mostramos un mensaje de confirmación
        Toast.makeText(this, "Opinión eliminada", Toast.LENGTH_SHORT).show()

        // Volvemos a cargar la lista para que se actualice la pantalla
        cargarOpiniones()
    }
}
