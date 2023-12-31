package com.example.ejerciciosexoplayer.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.ejerciciosexoplayer.objetos.SampleData.Companion.albumsWithSongsMap

@Composable
fun AlbumScreen(albumId: String, navController: NavController) {
    val albumWithSongs = albumsWithSongsMap.getValue(albumId.toInt())
    val album = albumWithSongs.album
    val canciones = albumWithSongs.canciones

    val imagePainter = painterResource(id = album.imagenID)
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()
        ) {
            Image(
                painter = imagePainter, contentDescription = null, modifier = Modifier.size(200.dp)
            )
            Text(
                text = "What You Don't See",
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = 16.dp)
            )
            Text(
                text = "Album by The Story So Far • 2013", modifier = Modifier.padding(top = 4.dp)
            )
            Button(
                onClick = { /* TODO: Implement shuffle play */ },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp)
            ) {
                Text("Shuffle Play")
            }
        }
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(6f)
        ) {
            items(canciones) { cancion ->
                Text(text = cancion.titulo,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp, horizontal = 16.dp)
                        .clickable { navController.navigate("player/${album.id}/${cancion.id}") })
            }
        }
        Text(
            text = "2013 • 11 songs - 29 min 52 sec",
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .weight(1f)
                .padding(8.dp)

        )
    }
}