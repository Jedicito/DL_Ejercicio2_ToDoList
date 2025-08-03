package chl.ancud.dl_ejercicio2_todolist.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import chl.ancud.dl_ejercicio2_todolist.R
import chl.ancud.dl_ejercicio2_todolist.model.Tarea

class TareasAdapter(
    private val listaTareas: MutableList<Tarea>, // Lista de tareas que mostrará el adapter
    private val listener: OnTareaCheckedChangeListener // Listener para manejar cambios de estado de completada
) : RecyclerView.Adapter<TareasAdapter.TareasViewHolder>() {


    // --- ViewHolder Interno ---
    inner class TareasViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // Mantiene las referencias a las vistas dentro de item_todo.xml
        val tvTitulo: TextView = itemView.findViewById(R.id.tvTitulo)
        val cbSeleccionar: CheckBox = itemView.findViewById(R.id.cbSeleccionar)
        val tvDescripcion: TextView = itemView.findViewById(R.id.tvDescripcion)
        val tvCreacion: TextView = itemView.findViewById(R.id.tvCreacion)


        // Opcional: un método para enlazar los datos a las vistas
        fun bind(tarea: Tarea) {
            tvTitulo.text = tarea.titulo
            tvDescripcion.text = tarea.descripcion
            tvCreacion.text = tarea.fechaCreacion

            cbSeleccionar.setOnCheckedChangeListener (null)
            cbSeleccionar.isChecked = tarea.completada

            // Aquí también puedes manejar los listeners para el CheckBox
            cbSeleccionar.setOnCheckedChangeListener { _, isChecked ->
                tarea.completada = isChecked
                listener.onTareaCheckedChanged()
                // Podrías añadir lógica aquí si necesitas notificar a alguien más
                // sobre el cambio, o actualizar la UI del TextView (ej. tachar texto)
            }
        }
    }

    interface OnTareaCheckedChangeListener {
        fun onTareaCheckedChanged() // Se llamará cuando cualquier tarea cambie su estado de completada
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TareasViewHolder {
        // Infla el layout del ítem (item_todo.xml)
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.activity_tarea_item, parent, false)
        return TareasViewHolder(view)
    }

    /**
     * Se llama por RecyclerView para mostrar los datos en la posición especificada.
     * Este método debe actualizar el contenido del itemView del ViewHolder para reflejar
     * el ítem en la posición dada.
     */
    override fun onBindViewHolder(holder: TareasViewHolder, position: Int) {
        val currentTodo = listaTareas[position]
        Log.d("TareasAdapter", "onBindViewHolder: ${currentTodo.titulo}")
        holder.bind(currentTodo) // Usa el método bind del ViewHolder
    }

    /**
     * Devuelve el número total de ítems en el conjunto de datos que tiene el adapter.
     */
    override fun getItemCount(): Int {
        return listaTareas.size
    }

    fun getPendientes(): Int {
        return listaTareas.count { !it.completada }
    }

    // --- Métodos Opcionales (pero útiles) ---

    fun addTarea(tarea: Tarea) {
        listaTareas.add(tarea)
        notifyItemInserted(listaTareas.size - 1) // Notifica al adapter que se insertó un ítem
    }

    /*fun removeCompletedTodos() {
        val originalSize = listaTareas.size
        listaTareas.removeAll { it.estaCompletada }
        val newSize = listaTareas.size
        if (originalSize != newSize) {
            notifyDataSetChanged() // Manera simple de notificar, o puedes ser más granular
        }
    }*/
}

