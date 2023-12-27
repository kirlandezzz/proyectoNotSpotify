package com.example.ejerciciosexoplayer.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.ejerciciosexoplayer.R

data class Song(val title: String)

// Dummy data for preview
val songsSample = listOf(
    Song("All Wrong"),
    Song("Things I Can't Change"),
    Song("All Wrong"),
    Song("Things I Can't Change"),
    Song("All Wrong"),
    Song("Things I Can't Change"),
    Song("All Wrong"),
    Song("Things I Can't Change"),
    Song("All Wrong"),
    Song("Things I Can't Change"),


    )

@Composable
fun AlbumScreen() {
    // In a real app, you might load the image from a URL using Coil or another image loading library
    val imagePainter =
        painterResource(id = R.drawable.ibai)



    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Column(
            horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier
                .fillMaxWidth()
        ) {
            Image(
                painter = imagePainter,
                contentDescription = "Album Cover",
                modifier = Modifier
                    .size(200.dp)

            )
            Text(
                text = "What You Don't See",
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = 16.dp)
            )
            Text(
                text = "Album by The Story So Far • 2013",
                modifier = Modifier.padding(top = 4.dp)
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
        )
        {
            items(songsSample) { song ->
                Text(
                    text = song.title,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp, horizontal = 16.dp)
                )
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