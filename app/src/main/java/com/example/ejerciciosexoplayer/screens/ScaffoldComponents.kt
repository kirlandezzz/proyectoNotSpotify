package com.example.ejerciciosexoplayer.screens

import android.support.v4.os.IResultReceiver2.Default
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
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
import com.example.ejerciciosexoplayer.R
import com.example.ejerciciosexoplayer.ui.theme.AzulOsc
import com.google.common.base.Defaults


@Composable
fun BarraInferior(
    funcionNavegarPlayer: () -> Unit,
    funcionNavegarFoto: () -> Unit
) {
    BottomAppBar(
        modifier = Modifier.fillMaxWidth(),
        containerColor = AzulOsc
    ) {
        Row() {
            IconButton(onClick = funcionNavegarPlayer, modifier = Modifier.weight(1f)) {
                Icon(Icons.Default.Home, contentDescription = "")
            }
            IconButton(onClick = funcionNavegarFoto, modifier = Modifier.weight(1f)) {
                Icon(Icons.Default.PictureInPicture, contentDescription = "")
            }

        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BarraSuperior(titulo: String) {
    CenterAlignedTopAppBar(
        title = { Text(titulo) },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = AzulOsc,
            titleContentColor = Color.White
        )
    )
}