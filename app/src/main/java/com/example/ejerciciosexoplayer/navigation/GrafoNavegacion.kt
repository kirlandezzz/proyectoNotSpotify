package com.example.ejerciciosexoplayer.navigation

import android.util.Log
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.ejerciciosexoplayer.screens.AlbumScreen
import com.example.ejerciciosexoplayer.screens.BarraInferior
import com.example.ejerciciosexoplayer.screens.BarraSuperior
import com.example.ejerciciosexoplayer.screens.ExoPlayerScreen
import com.example.ejerciciosexoplayer.screens.PictureScreen
import com.example.ejerciciosexoplayer.screens.homeScreen
import com.example.ejerciciosexoplayer.shared.Rutas
import com.example.ejerciciosexoplayer.shared.ScaffoldViewModel
import com.example.ejerciciosexoplayer.ui.theme.Blanco

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GrafoNavegacion() {

    val navController = rememberNavController()

    // El parametro route nos devuelve, en formato string, la ruta actual.
    val entradaNavActual by navController.currentBackStackEntryAsState()

    val rutaActual = entradaNavActual?.destination?.route

    // View model general que controla diversos valores del Scaffold -> el título y la barra de navegación
    val viewModelScaffold: ScaffoldViewModel = viewModel()

    Scaffold(topBar = { BarraSuperior(titulo = "Not Spotify") }, bottomBar = {
        BarraInferior(funcionNavegarPlayer = {
            navController.navigate(Rutas.Home.ruta)
            if (rutaActual == Rutas.Player.ruta){
            }
        }, funcionNavegarFoto = {

            //esto haace que no se reproduzca exoplayer screen doble

            //if (rutaActual != Rutas.Foto.ruta) {
            //  navController.navigate(Rutas.Foto.ruta)
            //}

        })
    }, content = {
        // paddingValues representa los dp que hay para evitar que el contenido se solape con las barras
            paddingValues ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues), color = Blanco
        ) {
            NavHost(navController = navController, startDestination = Rutas.Home.ruta) {
                composable("${Rutas.Player.ruta}/{albumId}/{cancionId}") {
                    val albumId = it.arguments?.getString("albumId")
                    val cancionId = it.arguments?.getString("cancionId")

                    if (albumId != null && cancionId != null) {
                        ExoPlayerScreen(albumId, cancionId)
                    }
                }
                composable(Rutas.Foto.ruta) {
                    PictureScreen(viewModelScaffold)
                }
                composable(Rutas.Home.ruta) {
                    homeScreen(viewModelScaffold, navController)
                }
                composable("${Rutas.Album.ruta}/{albumId}") {
                    val albumId = it.arguments?.getString("albumId")

                    if (albumId != null) {
                        AlbumScreen(albumId, navController)
                    }
                }
            }
        }
    })
}
