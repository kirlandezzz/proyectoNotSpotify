package com.example.ejerciciosexoplayer.shared

sealed class Rutas(val ruta : String) {
    object Player : Rutas("player")
    object Foto : Rutas("foto")
}