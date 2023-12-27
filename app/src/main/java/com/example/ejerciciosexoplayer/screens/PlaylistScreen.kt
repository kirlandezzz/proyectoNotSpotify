package com.example.ejerciciosexoplayer.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
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
import com.example.ejerciciosexoplayer.shared.ScaffoldViewModel
import com.example.ejerciciosexoplayer.R

data class Album(val id: Int, val title: String, val imageUrl: String)

val albumsSample = listOf(
    Album(1, "Album 1", "url"),
    Album(2, "Album 2", "url"),
    Album(3, "Album 3", "url"),
    Album(4, "Album 4", "url"),
    Album(5, "Album 5", "url"),

    )

@Composable
fun SpotifyLikeScreen(viewModelScaffold: ScaffoldViewModel = viewModel(), navController: NavController) {
    Column {
        Text(
            text = "Escucha tus albums"
            ,modifier = Modifier
                .padding(8.dp)
        )
        LazyRow(
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),

        ) {
            items(albumsSample) { album ->
                AlbumItem(album, navController)
            }
        }
        Text(
            text = "Playlist para ti"
            ,modifier = Modifier
                .padding(8.dp)
        )
        LazyRow(
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),

        ) {
            items(albumsSample) { album ->
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
            .clickable { navController.navigate("AlbumScreen/${album.id}") }, // Replace with your actual navigation route
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.ibai),
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