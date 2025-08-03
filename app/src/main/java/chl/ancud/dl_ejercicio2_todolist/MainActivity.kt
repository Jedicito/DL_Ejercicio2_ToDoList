package chl.ancud.dl_ejercicio2_todolist

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import chl.ancud.dl_ejercicio2_todolist.databinding.ActivityMainBinding
import chl.ancud.dl_ejercicio2_todolist.model.Tarea
import chl.ancud.dl_ejercicio2_todolist.adapter.TareasAdapter
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlin.uuid.ExperimentalUuidApi

class MainActivity : ComponentActivity(), TareasAdapter.OnTareaCheckedChangeListener {
    private lateinit var mainBinding: ActivityMainBinding
    private lateinit var tareasAdapter: TareasAdapter
    private val listaTareas = mutableListOf<Tarea>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)

        inicializarTareas()

        tareasAdapter = TareasAdapter(listaTareas, this)
        mainBinding.rvMain.adapter = tareasAdapter

        mainBinding.tvTareasPendientes.text = "Tareas pendientes: ${tareasAdapter.getPendientes()}"

        mainBinding.btNuevaTarea.setOnClickListener {
            Log.d("MainActivity", "btNuevaTarea: click")
            mostrarDialogoNuevaTarea()
        }



        val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(
            0,
            ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {

                return false
            }

            override fun onSwiped(
                viewHolder: RecyclerView.ViewHolder,
                direction: Int
            ) {
                val posicion = viewHolder.absoluteAdapterPosition
                if (posicion != RecyclerView.NO_POSITION) {
                    val tareaEliminada = listaTareas[posicion]

                    listaTareas.removeAt(posicion)
                    tareasAdapter.notifyItemRemoved(posicion)
                    mainBinding.tvTareasPendientes.text = "Tareas pendientes: ${tareasAdapter.getPendientes()}"

                    Toast.makeText(this@MainActivity, "Tarea ${tareaEliminada.titulo} eliminada", Toast.LENGTH_SHORT).show()

                }
            }
        }
        val itemTouchHelper = ItemTouchHelper(itemTouchHelperCallback)
        itemTouchHelper.attachToRecyclerView(mainBinding.rvMain)

    }

    fun OnTareaCheckedChangeListener() {
        mainBinding.tvTareasPendientes.text = "Tareas pendientes: ${tareasAdapter.getPendientes()}"
    }

    @OptIn(ExperimentalUuidApi::class)
    private fun inicializarTareas() {
        listaTareas.add(
            Tarea(titulo = "Título 1",
                descripcion = "Descripción 1",
                fechaCreacion = "2025-07-11",
                completada = false)
        )
        listaTareas.add(
            Tarea(titulo = "Título 2",
                descripcion = "Descripción 2",
                fechaCreacion = "2025-07-12",
                completada = false)
        )
        listaTareas.add(
            Tarea(titulo = "Título 3",
                descripcion = "Descripción 3",
                fechaCreacion = "2025-07-13",
                completada = false)
        )
        listaTareas.add(
            Tarea(titulo = "Título 4",
                descripcion = "Descripción 4",
                fechaCreacion = "2025-07-14",
                completada = false)
        )
    }


    @OptIn(ExperimentalUuidApi::class)
    private fun mostrarDialogoNuevaTarea() {
        val builderAlerta = AlertDialog.Builder(this)
        val inflater = LayoutInflater.from(this);
        val vistaAlerta = inflater.inflate(R.layout.activity_nueva_tarea, null)

        val etNuevaTitulo = vistaAlerta.findViewById<EditText>(R.id.etNuevaTitulo)
        val etNuevaDescripcion = vistaAlerta.findViewById<EditText>(R.id.etNuevaDescripcion)
        val btNuevaAgregar = vistaAlerta.findViewById<Button>(R.id.btNuevaAgregar)

        builderAlerta.setView(vistaAlerta)

        val dialogo = builderAlerta.create()

        btNuevaAgregar.setOnClickListener {
            val titulo = etNuevaTitulo.text.toString()
            val descripcion = etNuevaDescripcion.text.toString()
            val formatoFecha = SimpleDateFormat("yyyy/MM/dd", Locale.getDefault())
            val fechaActual = formatoFecha.format(Date())
            val nuevaTarea = Tarea(titulo = titulo, descripcion = descripcion, fechaCreacion = fechaActual)
            tareasAdapter.addTarea(nuevaTarea)
            tareasAdapter.notifyItemInserted(listaTareas.size - 1)
            mainBinding.rvMain.scrollToPosition(listaTareas.size - 1)
            mainBinding.tvTareasPendientes.text = "Tareas pendientes: ${tareasAdapter.getPendientes()}"
            dialogo.dismiss()
        }

        dialogo.show()

    }

    override fun onTareaCheckedChanged() {
        mainBinding.tvTareasPendientes.text = "Tareas pendientes: ${tareasAdapter.getPendientes()}"
    }

}