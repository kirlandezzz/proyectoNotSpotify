@file:OptIn(UnstableApi::class)

package com.example.ejerciciosexoplayer.shared

import android.content.ContentResolver
import android.content.Context
import android.content.res.Resources
import android.net.Uri
import androidx.annotation.AnyRes
import androidx.annotation.OptIn
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import com.example.ejerciciosexoplayer.R
import com.example.ejerciciosexoplayer.objetos.Cancion
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import java.util.Collections


// Este VM se encarga de conectar los datos (reproductor, cancion actual) con la UI.
// Incluye la lógica necesaria para la gestión del reproductor
class ExoPlayerViewModel : ViewModel() {

    /* TODO: Pasos a seguir
     *  1 - Finalizar la función crearExoPlayer
     *  2 - Finalizar la función hacerSonarMusica, sin el listener
     *  3 - Finalizar la funion PausarOSeguirMusica
     *  4 - Finalizar el listener, para gestionar la duracion y el progreso
     *  5 - Finalizar la funcion cambiarCancion
     */

    // El reproductor de musica, empieza a null
    private val _exoPlayer: MutableStateFlow<ExoPlayer?> = MutableStateFlow(null)
    val exoPlayer = _exoPlayer.asStateFlow()

    // La cancion actual que está sonando
    private val _actual = MutableStateFlow(R.raw.songone)
    val actual = _actual.asStateFlow()

    // La duración de la canción
    private val _duracion = MutableStateFlow(0)
    val duracion = _duracion.asStateFlow()

    // El progreso (en segundos) actual de la cancion
    private val _progreso = MutableStateFlow(0)
    val progreso = _progreso.asStateFlow()


    private val original = listOf(
        R.raw.songone, R.raw.songtwo, R.raw.songthree, R.raw.songfour, R.raw.songfive
    )
    private var listaCanciones = original.toList()


    //Sirve para Shuffle
    private var isShuffled = false

    //Bucle
    private var isLooped = false


    private val datosCanciones = mapOf(
        R.raw.songone to Cancion("Song One", R.drawable.ibai, Color.Red),
        R.raw.songtwo to Cancion("Song Two", R.drawable.bunny, Color.Green),
        R.raw.songthree to Cancion("Song Three", R.drawable.crab, Color.Blue),
        R.raw.songfour to Cancion("Song Four", R.drawable.lethal, Color.Yellow),
        R.raw.songfive to Cancion("Song Five", R.drawable.covid, Color.Magenta)

    )
    private val _imagenActual = MutableStateFlow(R.drawable.ibai) // Imagen predeterminada
    val imagenActual = _imagenActual.asStateFlow()

    private val _titulo =
        MutableStateFlow(datosCanciones[R.raw.songone]!!.titulo) // titulo predeterminado
    val titulo = _titulo.asStateFlow()

    private val _posSlider = MutableStateFlow(0f)
    val posSlider = _posSlider.asStateFlow()

    private val _isUserInteracting = MutableStateFlow(false)

    fun crearExoPlayer(context: Context) {/* TODO : Crear el _exoPlayer usando el build(), prepare() y playWhenReady */
        _exoPlayer.value = ExoPlayer.Builder(context).build()
        _exoPlayer.value!!.prepare()
        _exoPlayer.value!!.playWhenReady = true

    }

    fun hacerSonarMusica(context: Context) {/* TODO: 1 - Crear un mediaItem con la cancion actual
         *  2 - Establecer dicho mediaItem
         *  3 - Activar el playWhenReady
         */

        // Este listener se mantendrá mientras NO se librere el _exoPlayer
        // Asi que no hace falta crearlo más de una vez.
        var cancion = MediaItem.fromUri(obtenerRuta(context, _actual.value))
        _exoPlayer.value!!.setMediaItem(cancion)
        _exoPlayer.value!!.playWhenReady = true
        _exoPlayer.value!!.addListener(object : Player.Listener {
            override fun onPlaybackStateChanged(playbackState: Int) {
                if (playbackState == Player.STATE_READY) {
                    // El Player está preparado para empezar la reproducción.
                    // Si playWhenReady es true, empezará a sonar la música.\
                    val posActual = _exoPlayer.value!!.currentPosition.toFloat() / _duracion.value
                    _posSlider.value = posActual
                    /* TODO: Actualizar la duración*/
                    _duracion.value = _exoPlayer.value!!.duration.toInt()

                    viewModelScope.launch {
                        while (isActive) {
                            _progreso.value = _exoPlayer.value!!.currentPosition.toInt()
                            _posSlider.value = _progreso.value.toFloat() / _duracion.value
                            delay(1000)
                        }
                    }

                    viewModelScope.launch {
                        /* TODO: Actualizar el progreso usando currentPosition cada segundo */
                        while (isActive) {
                            _progreso.value = _exoPlayer.value!!.currentPosition.toInt()
                            delay(1000)
                        }

                    }
                } else if (playbackState == Player.STATE_BUFFERING) {
                    // El Player está cargando el archivo, preparando la reproducción.
                    // No está listo, pero está en ello.
                } else if (playbackState == Player.STATE_ENDED) {
                    if (!isLooped) {
                        CambiarCancion(context)
                    } else {
                        _exoPlayer.value!!.seekTo(0)
                        _exoPlayer.value!!.play()
                    }

                } else if (playbackState == Player.STATE_IDLE) {
                    // El player se ha creado, pero no se ha lanzado la operación prepared.
                }

            }
        })


    }

    // Este método se llama cuando el VM se destruya.
    override fun onCleared() {
        _exoPlayer.value!!.release()
        super.onCleared()
    }

    fun PausarOSeguirMusica() {/* TODO: Si el reproductor esta sonando, lo pauso. Si no, lo reproduzco */
        if (_exoPlayer.value!!.isPlaying) {
            _exoPlayer.value!!.pause()
        } else {
            _exoPlayer.value!!.play()
        }
    }

    fun CambiarCancion(context: Context) {
        _exoPlayer.value!!.stop()
        _exoPlayer.value!!.clearMediaItems()
        val currentIndex = listaCanciones.indexOf(_actual.value)
        val nextIndex = (currentIndex + 1) % listaCanciones.size
        _actual.value = listaCanciones[nextIndex]
        _imagenActual.value = datosCanciones[listaCanciones[nextIndex]]?.imagen ?: R.drawable.ibai
        _titulo.value = datosCanciones[listaCanciones[nextIndex]]?.titulo
            ?: datosCanciones[R.raw.songone]!!.titulo
        _exoPlayer.value!!.setMediaItem(MediaItem.fromUri(obtenerRuta(context, _actual.value)))
        _exoPlayer.value!!.prepare()
        _exoPlayer.value!!.playWhenReady = true
    }

    fun volverCancion(context: Context) {
        _exoPlayer.value!!.stop()
        _exoPlayer.value!!.clearMediaItems()

        val currentIndex = listaCanciones.indexOf(_actual.value)
        val previousIndex = if (currentIndex <= 0) listaCanciones.size - 1 else currentIndex - 1

        _actual.value = listaCanciones[previousIndex]
        _imagenActual.value = datosCanciones[_actual.value]?.imagen ?: R.drawable.ibai

        _exoPlayer.value!!.setMediaItem(MediaItem.fromUri(obtenerRuta(context, _actual.value)))
        _exoPlayer.value!!.prepare()
        _exoPlayer.value!!.playWhenReady = true
    }

    fun shuffleSongs(context: Context) {
        if (!isShuffled) {

            Collections.shuffle(listaCanciones)
            isShuffled = true

        } else {

            listaCanciones = original.toList()
            isShuffled = false
        }

        _exoPlayer.value?.let { exoPlayer ->
            exoPlayer.stop()
            exoPlayer.clearMediaItems()

            listaCanciones.forEach { songResId ->
                exoPlayer.addMediaItem(MediaItem.fromUri(obtenerRuta(context, songResId)))
            }

            exoPlayer.prepare()

            if (!isShuffled) {
                CambiarCancion(context)
            }

        }


    }

    fun loop() {
        isLooped = !isLooped

    }

    fun moverSlider(value: Float) {
        _isUserInteracting.value = true
        _posSlider.value = value
        val newPosition = (value * _duracion.value).toLong()
        _exoPlayer.value?.seekTo(newPosition)
    }

    fun onSliderInteractionEnd() {
        _isUserInteracting.value = false
    }


}


@Throws(Resources.NotFoundException::class)
fun obtenerRuta(context: Context, @AnyRes resId: Int): Uri {
    val res: Resources = context.resources
    return Uri.parse(
        ContentResolver.SCHEME_ANDROID_RESOURCE + "://" + res.getResourcePackageName(resId) + '/' + res.getResourceTypeName(
            resId
        ) + '/' + res.getResourceEntryName(resId)
    )
}
