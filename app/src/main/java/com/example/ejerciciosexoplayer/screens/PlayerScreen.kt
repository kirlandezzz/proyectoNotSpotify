package com.example.ejerciciosexoplayer.screens

import android.content.Context
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ejerciciosexoplayer.R
import com.example.ejerciciosexoplayer.objetos.EstadoExoPlayer
import com.example.ejerciciosexoplayer.shared.ExoPlayerViewModel
import com.example.ejerciciosexoplayer.ui.theme.Azul
import com.example.ejerciciosexoplayer.ui.theme.AzulOsc

@Composable
fun ExoPlayerScreen(albumId: String, cancionId : String) {
    val contexto = LocalContext.current

    /* Variables de estado */
    val exoPlayerViewModel: ExoPlayerViewModel = viewModel()
    val estadoExoPlayer = RecolectarEstadosExoPlayer(exoPlayerViewModel)

    //Barra duracion en min y seg
    val minutosDuracion = estadoExoPlayer.duracion / 1000 / 60
    val segundosDuracion = estadoExoPlayer.duracion / 1000 % 60
    val minutosPosicion = estadoExoPlayer.posicion / 1000 / 60
    val segundosPosicion = estadoExoPlayer.posicion / 1000 % 60

    LaunchedEffect(Unit) {
        Log.d("Exoplayer", "launched effect")
        exoPlayerViewModel.crearExoPlayer(contexto,albumId.toInt(), cancionId.toInt())
        exoPlayerViewModel.hacerSonarMusica(contexto)
    }

    Column(
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = estadoExoPlayer.tituloActual, fontSize = 32.sp, modifier = Modifier.weight(1.5f)
        )

        Image(
            painter = painterResource(id = estadoExoPlayer.imagenActual),
            contentDescription = null,
            modifier = Modifier.weight(4f),
        )

        SliderReproductor(value = estadoExoPlayer.posSlider, onValueChange = { value ->
            exoPlayerViewModel.moverSlider(value)
        }, onValueChangeFinished = { exoPlayerViewModel.onSliderInteractionEnd() })

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(horizontal = 32.dp)
        ) {
            TextoTiempo(minutos = minutosPosicion, segundos = segundosPosicion)
            TextoTiempo(minutos = minutosDuracion, segundos = segundosDuracion)
        }

        Row(
            modifier = Modifier.weight(1f)
        ) {
            BotonesReproductor(exoPlayerViewModel, contexto)
        }
    }
}

@Composable
fun RecolectarEstadosExoPlayer(exoPlayerViewModel: ExoPlayerViewModel): EstadoExoPlayer {
    return EstadoExoPlayer(
        duracion = exoPlayerViewModel.duracion.collectAsStateWithLifecycle().value,
        posicion = exoPlayerViewModel.progreso.collectAsStateWithLifecycle().value,
        imagenActual = exoPlayerViewModel.imagenActual.collectAsStateWithLifecycle().value,
        tituloActual = exoPlayerViewModel.titulo.collectAsStateWithLifecycle().value,
        posSlider = exoPlayerViewModel.posSlider.collectAsStateWithLifecycle().value
    )
}

@Composable
fun SliderReproductor(
    value: Float,
    onValueChange: (Float) -> Unit,
    onValueChangeFinished: () -> Unit,
    valueRange: ClosedFloatingPointRange<Float> = 0f..1f,
    modifier: Modifier = Modifier,
) {
    Slider(value = value,
        colors = SliderDefaults.colors(AzulOsc, Azul),
        onValueChange = { newValue ->
            onValueChange(newValue)
        },
        onValueChangeFinished = {
            onValueChangeFinished()
        },
        valueRange = valueRange,
        modifier = modifier
    )
}

@Composable
fun TextoTiempo(minutos: Int, segundos: Int) {
    Text(
        text = String.format("%02d:%02d", minutos, segundos)
    )
}

@Composable
fun BotonesReproductor(exoPlayerViewModel: ExoPlayerViewModel, contexto: Context) {
    var loopButtonColor by remember {
        mutableStateOf(AzulOsc)
    }

    var shufleColor by remember {
        mutableStateOf(AzulOsc)
    }

    Boton(iconId = R.drawable.baseline_shuffle_24, contentColor = shufleColor, onClick = {
        exoPlayerViewModel.shuffleSongs(contexto)
        shufleColor = if (shufleColor == AzulOsc) {
            Azul
        } else {
            AzulOsc
        }
    })

    Boton(iconId = R.drawable.baseline_arrow_left_24,
        onClick = { exoPlayerViewModel.volverCancion(contexto) })

    Button(
        onClick = { exoPlayerViewModel.PausarOSeguirMusica() },
        shape = CircleShape,
        colors = ButtonDefaults.buttonColors(containerColor = AzulOsc)
    ) {
        Icon(painter = painterResource(id = R.drawable.play), contentDescription = null)
    }

    Boton(iconId = R.drawable.baseline_arrow_right_24,
        onClick = { exoPlayerViewModel.CambiarCancion(contexto) })

    Boton(iconId = R.drawable.baseline_loop_24, contentColor = loopButtonColor, onClick = {
        exoPlayerViewModel.loop()
        loopButtonColor = if (loopButtonColor == AzulOsc) {
            Azul
        } else {
            AzulOsc
        }
    })
}

@Composable
fun Boton(
    iconId: Int,
    contentColor: Color = AzulOsc,
    onClick: () -> Unit,
    buttonColor: Color = Color.Transparent,
) {
    Button(
        onClick = onClick, colors = ButtonDefaults.buttonColors(
            containerColor = buttonColor, contentColor = contentColor
        )
    ) {
        Icon(
            painter = painterResource(id = iconId), contentDescription = null
        )
    }
}
