package chl.ancud.dl_ejercicio2_todolist.model

import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

data class Tarea @OptIn(ExperimentalUuidApi::class) constructor(
    val id: Uuid = Uuid.random(),
    var titulo: String,
    var descripcion: String,
    var fechaCreacion: String,
    var completada: Boolean = false
)