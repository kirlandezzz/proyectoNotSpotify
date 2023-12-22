package com.example.ejerciciosexoplayer.screens

import android.graphics.drawable.Drawable
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ButtonDefaults.shape
import androidx.compose.material3.Icon
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.media3.exoplayer.ExoPlayer
import com.example.ejerciciosexoplayer.R
import com.example.ejerciciosexoplayer.shared.ExoPlayerViewModel
import com.example.ejerciciosexoplayer.shared.ScaffoldViewModel

@Composable
fun ExoPlayerScreen(viewModelScaffold: ScaffoldViewModel = viewModel()) {
    val contexto = LocalContext.current

    /* Variables de estado */
    val exoPlayerViewModel: ExoPlayerViewModel = viewModel()
    val duracion by exoPlayerViewModel.duracion.collectAsStateWithLifecycle()
    val posicion by exoPlayerViewModel.progreso.collectAsStateWithLifecycle()
    val imagenActual by exoPlayerViewModel.imagenActual.collectAsStateWithLifecycle()
    val tituloActual by exoPlayerViewModel.titulo.collectAsStateWithLifecycle()
    /* TODO: Llamar a crearExoPlayer y hacerSonarMusica */


    LaunchedEffect(Unit) {
        exoPlayerViewModel.crearExoPlayer(contexto)
        exoPlayerViewModel.hacerSonarMusica(contexto)
    }


    Column(
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(text = tituloActual, style = TextStyle(color = Color.Black))


        Image(painter = painterResource(id = imagenActual), contentDescription = null)

        Slider(value = 0f, onValueChange = {})

        Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
            Text("${duracion / 1000}", Modifier.fillMaxWidth())
            Text(text = "${posicion / 1000}", Modifier.fillMaxWidth())
        }


        Row {
            Button(
                onClick = {exoPlayerViewModel.shuffleSongs(contexto)},
                shape = CircleShape,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White,
                    contentColor = Color.Black
                )
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_shuffle_24),
                    contentDescription = null
                )
            }
            Button(
                onClick = {exoPlayerViewModel.volverCancion(contexto)},
                shape = CircleShape,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White,
                    contentColor = Color.Black
                )
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_arrow_left_24),
                    contentDescription = null
                )
            }
            Button(
                onClick = { exoPlayerViewModel.PausarOSeguirMusica() },
                shape = CircleShape,
                colors = ButtonDefaults.buttonColors(containerColor = Color.Blue)
            ) {
                Icon(painter = painterResource(id = R.drawable.play), contentDescription = null)
            }
            Button(
                onClick = { exoPlayerViewModel.CambiarCancion(contexto);  },
                shape = CircleShape,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White,
                    contentColor = Color.Black
                )
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_arrow_right_24),
                    contentDescription = null
                )
            }
            Button(
                onClick = {exoPlayerViewModel.loop()},
                shape = CircleShape,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White,
                    contentColor = Color.Black
                )
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_loop_24),
                    contentDescription = null
                )
            }
        }
    }
}