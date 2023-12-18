package com.example.ejerciciosexoplayer.screens

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.res.painterResource
import com.example.ejerciciosexoplayer.R
import com.example.ejerciciosexoplayer.shared.ScaffoldViewModel


@Composable
fun PictureScreen(viewModel: ScaffoldViewModel){
    Image(
        painter = painterResource(id = viewModel.cancionActual.collectAsState().value),
        contentDescription =""
    )
}