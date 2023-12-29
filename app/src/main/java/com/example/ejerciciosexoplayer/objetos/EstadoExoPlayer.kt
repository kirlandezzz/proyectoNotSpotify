package com.example.ejerciciosexoplayer.objetos

import androidx.compose.runtime.Composable
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.ejerciciosexoplayer.shared.ExoPlayerViewModel

data class EstadoExoPlayer (
    val duracion: Int,
    val posicion: Int,
    val imagenActual: Int,
    val tituloActual: String,
    val posSlider: Float
)
@Composable
fun ExoPlayerViewModel.collectAsStateWithLifecycle(): EstadoExoPlayer {
    return EstadoExoPlayer(
        duracion = duracion.collectAsStateWithLifecycle().value,
        posicion = progreso.collectAsStateWithLifecycle().value,
        imagenActual = imagenActual.collectAsStateWithLifecycle().value,
        tituloActual = titulo.collectAsStateWithLifecycle().value,
        posSlider = posSlider.collectAsStateWithLifecycle().value
    )
}
