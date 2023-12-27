package com.example.ejerciciosexoplayer.shared

sealed class Rutas(val ruta : String) {
    object Player : Rutas("player")
    object Foto : Rutas("foto")
    object Home : Rutas("home")
    object Album : Rutas("album")
}