package com.altokloud.app.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.altokloud.app.Opinion
import com.altokloud.app.R

// El adapter necesita una lista de opiniones y una función para manejar el clic de eliminación.
class OpinionAdapter(
    private val opiniones: List<Opinion>, // Cambiado a val y a una lista inmutable
    private val onEliminarClick: (Opinion) -> Unit
) : RecyclerView.Adapter<OpinionAdapter.OpinionViewHolder>() {

    // El ViewHolder representa cada "fila" de nuestra lista.
    class OpinionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvOpinion: TextView = itemView.findViewById(R.id.tvOpinionItem)
        val ivEliminar: ImageView = itemView.findViewById(R.id.ivEliminar)
    }

    // Crea una nueva vista (fila) para la lista.
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OpinionViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_opinion, parent, false)
        return OpinionViewHolder(view)
    }

    // Vincula los datos de una opinión específica a una vista (fila).
    override fun onBindViewHolder(holder: OpinionViewHolder, position: Int) {
        val opinion = opiniones[position]
        holder.tvOpinion.text = opinion.texto

        // Configuramos el clic en el ícono de eliminar.
        holder.ivEliminar.setOnClickListener {
            onEliminarClick(opinion)
        }
    }

    // Devuelve el número total de elementos en la lista.
    override fun getItemCount(): Int = opiniones.size

    // La función problemática ha sido eliminada.
}
