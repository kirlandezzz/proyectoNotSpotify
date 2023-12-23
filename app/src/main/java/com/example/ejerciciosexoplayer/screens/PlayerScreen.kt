package com.example.ejerciciosexoplayer.screens

import android.content.res.Resources.Theme
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import com.example.ejerciciosexoplayer.ui.theme.Azul
import com.example.ejerciciosexoplayer.ui.theme.AzulOsc
import com.example.ejerciciosexoplayer.ui.theme.Blanco

@Composable
fun ExoPlayerScreen(viewModelScaffold: ScaffoldViewModel = viewModel()) {
    val contexto = LocalContext.current

    /* Variables de estado */
    val exoPlayerViewModel: ExoPlayerViewModel = viewModel()
    val duracion by exoPlayerViewModel.duracion.collectAsStateWithLifecycle()
    val posicion by exoPlayerViewModel.progreso.collectAsStateWithLifecycle()
    val imagenActual by exoPlayerViewModel.imagenActual.collectAsStateWithLifecycle()
    val tituloActual by exoPlayerViewModel.titulo.collectAsStateWithLifecycle()
    val posSlider by exoPlayerViewModel.posSlider.collectAsStateWithLifecycle()

    //Barra duracion en min y seg
    val minutosDuracion = duracion / 1000 / 60
    val segundosDuracion = duracion / 1000 % 60
    val minutosPosicion = posicion / 1000 / 60
    val segundosPosicion = posicion / 1000 % 60

    var loopButtonColor by remember {
        mutableStateOf(AzulOsc)
    }

    var ShufleColor by remember {
        mutableStateOf(AzulOsc)
    }

    //colore https://colorhunt.co/palette/ecf4d69ad0c22d9596265073
    /* TODO: Llamar a crearExoPlayer y hacerSonarMusica */


    LaunchedEffect(Unit) {
        exoPlayerViewModel.crearExoPlayer(contexto)
        exoPlayerViewModel.hacerSonarMusica(contexto)
    }


    Column(
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(text = tituloActual)

        Image(painter = painterResource(id = imagenActual), contentDescription = null)

        Slider(
            value = posSlider,
            colors = SliderDefaults.colors(AzulOsc, Azul),
            onValueChange = { value ->
                exoPlayerViewModel.moverSlider(value)
            },
            onValueChangeFinished = {
                exoPlayerViewModel.onSliderInteractionEnd()
            },
            valueRange = 0f..1f
        )

        Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
            Text(text = String.format("%02d:%02d", minutosPosicion, segundosPosicion))
            Text(text = String.format("%02d:%02d", minutosDuracion, segundosDuracion))
        }


        Row {
            Button(
                onClick = {
                    exoPlayerViewModel.shuffleSongs(contexto)
                    ShufleColor = if (ShufleColor == AzulOsc) {
                        Azul
                    } else {
                        AzulOsc
                    }
                }, shape = CircleShape, colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent, contentColor = ShufleColor
                )
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_shuffle_24),
                    contentDescription = null
                )
            }
            Button(
                onClick = { exoPlayerViewModel.volverCancion(contexto) },
                shape = CircleShape,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent, contentColor = AzulOsc
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
                colors = ButtonDefaults.buttonColors(containerColor = AzulOsc)
            ) {
                Icon(painter = painterResource(id = R.drawable.play), contentDescription = null)
            }
            Button(
                onClick = { exoPlayerViewModel.CambiarCancion(contexto); },
                shape = CircleShape,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent, contentColor = AzulOsc
                )
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_arrow_right_24),
                    contentDescription = null
                )
            }
            Button(
                onClick = {
                    exoPlayerViewModel.loop()
                    loopButtonColor = if (loopButtonColor == AzulOsc) {
                        Azul
                    } else {
                        AzulOsc
                    }
                }, shape = CircleShape, colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent, contentColor = loopButtonColor
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