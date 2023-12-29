package com.example.ejerciciosexoplayer.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.ejerciciosexoplayer.objetos.Album
import com.example.ejerciciosexoplayer.objetos.SampleData
import com.example.ejerciciosexoplayer.objetos.SampleData.Companion.albumsSample
import com.example.ejerciciosexoplayer.shared.ScaffoldViewModel

@Composable
fun homeScreen(viewModelScaffold: ScaffoldViewModel = viewModel(), navController: NavController) {
    val albums = albumsSample
    Column {
        Text(
            text = "Escucha tus albums", modifier = Modifier.padding(8.dp)
        )
        LazyRow(
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),

            ) {
            try {
                items(albums) { album ->
                    AlbumItem(album, navController)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        Text(
            text = "Playlist para ti", modifier = Modifier.padding(8.dp)
        )
        LazyRow(
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),

            ) {
            items(albums) { album ->
                AlbumItem(album, navController)
            }
        }
    }
}

@Composable
fun AlbumItem(album: Album, navController: NavController) {
    Column(
        modifier = Modifier
            .width(160.dp)
            .padding(vertical = 8.dp)
            .background(Color.Gray)
            .clickable { navController.navigate("album/${album.id}") },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = album.imagenID),
            contentDescription = "Album cover for ${album.title}",
            modifier = Modifier.size(150.dp)
        )
        Text(
            text = album.title,
            modifier = Modifier
                .padding(top = 8.dp)
                .align(Alignment.CenterHorizontally),
            maxLines = 1
        )
    }
}