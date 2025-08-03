package chl.ancud.dl_ejercicio2_todolist.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import chl.ancud.dl_ejercicio2_todolist.R
import chl.ancud.dl_ejercicio2_todolist.model.Tarea

class TareasAdapter(
    private val listaTareas: MutableList<Tarea>,
    private val listener: OnTareaCheckedChangeListener
) : RecyclerView.Adapter<TareasAdapter.TareasViewHolder>() {


    inner class TareasViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvTitulo: TextView = itemView.findViewById(R.id.tvTitulo)
        val cbSeleccionar: CheckBox = itemView.findViewById(R.id.cbSeleccionar)
        val tvDescripcion: TextView = itemView.findViewById(R.id.tvDescripcion)
        val tvCreacion: TextView = itemView.findViewById(R.id.tvCreacion)


        fun bind(tarea: Tarea) {
            tvTitulo.text = tarea.titulo
            tvDescripcion.text = tarea.descripcion
            tvCreacion.text = tarea.fechaCreacion

            cbSeleccionar.setOnCheckedChangeListener (null)
            cbSeleccionar.isChecked = tarea.completada

            cbSeleccionar.setOnCheckedChangeListener { _, isChecked ->
                tarea.completada = isChecked
                listener.onTareaCheckedChanged()

            }
        }
    }

    interface OnTareaCheckedChangeListener {
        fun onTareaCheckedChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TareasViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.activity_tarea_item, parent, false)
        return TareasViewHolder(view)
    }


    override fun onBindViewHolder(holder: TareasViewHolder, position: Int) {
        val currentTodo = listaTareas[position]
        holder.bind(currentTodo) // Usa el método bind del ViewHolder
    }


    override fun getItemCount(): Int {
        return listaTareas.size
    }

    fun getPendientes(): Int {
        return listaTareas.count { !it.completada }
    }


    fun addTarea(tarea: Tarea) {
        listaTareas.add(tarea)
        notifyItemInserted(listaTareas.size - 1)
    }
}

