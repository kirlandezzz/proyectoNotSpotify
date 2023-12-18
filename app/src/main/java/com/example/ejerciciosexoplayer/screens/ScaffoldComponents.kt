package com.example.ejerciciosexoplayer.screens

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PictureInPicture
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun BarraInferior(funcionNavegarPlayer: () -> Unit,
funcionNavegarFoto: () -> Unit){
    BottomAppBar(modifier = Modifier.fillMaxWidth(),
    containerColor = Color.Red) {
        Row(){
           IconButton(onClick = funcionNavegarPlayer, modifier = Modifier.weight(1f)) {
               Icon(Icons.Default.PlayArrow, contentDescription = "")
           }
            IconButton(onClick = funcionNavegarFoto, modifier = Modifier.weight(1f)) {
                Icon(Icons.Default.PictureInPicture, contentDescription = "")
            }

        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BarraSuperior(titulo : String){
    CenterAlignedTopAppBar(title = { Text(titulo) }
    )
}