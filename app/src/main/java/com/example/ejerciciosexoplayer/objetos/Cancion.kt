package com.example.ejerciciosexoplayer.objetos

import com.example.ejerciciosexoplayer.R

import androidx.annotation.DrawableRes
import androidx.compose.ui.graphics.Color

data class Cancion(
    val titulo: String,
    @DrawableRes val imagen: Int,
    val colorFondo: Color
)
