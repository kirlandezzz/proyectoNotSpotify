@file:OptIn(UnstableApi::class)

package com.example.ejerciciosexoplayer.shared

import android.content.ContentResolver
import android.content.Context
import android.content.res.Resources
import android.net.Uri
import android.util.Log
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
import com.example.ejerciciosexoplayer.objetos.SampleData
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import java.util.Collections


// Este VM se encarga de conectar los datos (reproductor, cancion actual) con la UI.
// Incluye la lógica necesaria para la gestión del reproductor
class ExoPlayerViewModel : ViewModel() {

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

    private var original = listOf(
        R.raw.songone, R.raw.songtwo, R.raw.songthree, R.raw.songfour, R.raw.songfive
    )
    private var listaCanciones = original.toList()

    //Sirve para Shuffle
    private var isShuffled = false

    //Bucle
    private var isLooped = false

    private var datosCanciones = mapOf(
        R.raw.songone to Cancion(1,"Ibai Mason - IA", R.drawable.ibai, Color.Red),
        R.raw.songtwo to Cancion(2,"Bad Bunny Me cago - IA", R.drawable.bunny, Color.Green),
        R.raw.songthree to Cancion(3,"Crab Rave - Noise Storm", R.drawable.crab, Color.Blue),
        R.raw.songfour to Cancion(4,"Icecream - Lethal Company", R.drawable.lethal, Color.Yellow),
        R.raw.songfive to Cancion(5,"Laboratory - Dville Santa", R.drawable.covid, Color.Magenta)
    )

    private val _imagenActual = MutableStateFlow(datosCanciones[R.raw.songone]!!.imagen) // Imagen predeterminada
    val imagenActual = _imagenActual.asStateFlow()

    private val _titulo =
        MutableStateFlow(datosCanciones[R.raw.songone]!!.titulo) // titulo predeterminado
    val titulo = _titulo.asStateFlow()

    private val _posSlider = MutableStateFlow(0f)
    val posSlider = _posSlider.asStateFlow()

    private val _isUserInteracting = MutableStateFlow(false)

    fun crearExoPlayer(context: Context, albumId: Int, cancionId : Int) {
        Log.d("ExoPlayer", "crear exoplayer")
        _exoPlayer.value = ExoPlayer.Builder(context).build()

        val album = SampleData.albumsWithSongsMap[albumId]
        if (album != null ){
            val cancionesAlbum = album.canciones
            val startIndex = cancionesAlbum.indexOfFirst { it.id == cancionId }

            val cancionesOrdenadas = cancionesAlbum.subList(startIndex, cancionesAlbum.size) +
                    cancionesAlbum.subList(0, startIndex)

            original = cancionesOrdenadas.map { it.id }
            listaCanciones = original.toList()
            datosCanciones = cancionesOrdenadas.associate { it.id to datosCanciones[it.id]!! }


            for (cancion in cancionesOrdenadas) {
                val mediaItem = MediaItem.fromUri(obtenerRuta(context, cancion.id))
                _exoPlayer.value!!.addMediaItem(mediaItem)
            }
        }

        _exoPlayer.value!!.prepare()
        _exoPlayer.value!!.playWhenReady = true
    }

    fun hacerSonarMusica(context: Context) {
        _exoPlayer.value!!.addListener(object : Player.Listener {
            override fun onPlaybackStateChanged(playbackState: Int) {
                if (playbackState == Player.STATE_READY) {
                    val posActual = _exoPlayer.value!!.currentPosition.toFloat() / _duracion.value
                    _posSlider.value = posActual
                    _duracion.value = _exoPlayer.value!!.duration.toInt()

                    viewModelScope.launch {
                        while (isActive) {
                            _progreso.value = _exoPlayer.value!!.currentPosition.toInt()
                            _posSlider.value = _progreso.value.toFloat() / _duracion.value
                            delay(100)
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

    override fun onCleared() {
        _exoPlayer.value!!.release()
        super.onCleared()
    }

    fun PausarOSeguirMusica() {
        if (_exoPlayer.value!!.isPlaying) {
            _exoPlayer.value!!.pause()
        } else {
            _exoPlayer.value!!.play()
        }
    }

    fun CambiarCancion(context: Context) {
        _exoPlayer.value?.apply {
            stop()
            clearMediaItems()

            val currentIndex = listaCanciones.indexOf(_actual.value)
            val nextIndex = (currentIndex + 1) % listaCanciones.size

            _actual.value = listaCanciones[nextIndex]
            val nextSong = datosCanciones[_actual.value]

            _imagenActual.value = nextSong?.imagen ?: R.drawable.ibai
            _titulo.value = nextSong?.titulo ?: datosCanciones[R.raw.songone]!!.titulo

            setMediaItem(MediaItem.fromUri(obtenerRuta(context, _actual.value)))
            prepare()
            playWhenReady = true
        }
    }

    fun volverCancion(context: Context) {
        _exoPlayer.value?.apply {
            stop()
            clearMediaItems()

            val currentIndex = listaCanciones.indexOf(_actual.value)
            val prevIndex = if (currentIndex <= 0) listaCanciones.size - 1 else currentIndex - 1

            _actual.value = listaCanciones[prevIndex]
            _imagenActual.value = datosCanciones[_actual.value]?.imagen ?: R.drawable.ibai
            _titulo.value =
                datosCanciones[_actual.value]?.titulo ?: datosCanciones[R.raw.songone]!!.titulo

            setMediaItem(MediaItem.fromUri(obtenerRuta(context, _actual.value)))
            prepare()
            playWhenReady = true
        }
    }

    fun shuffleSongs(context: Context) {
        if (!isShuffled) {
            Collections.shuffle(listaCanciones)
        } else {
            listaCanciones = original.toList()
        }

        isShuffled = !isShuffled

        _exoPlayer.value?.let {
            it.stop()
            it.clearMediaItems()

            listaCanciones.forEach { songResId ->
                it.addMediaItem(MediaItem.fromUri(obtenerRuta(context, songResId)))
            }

            it.prepare()

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

