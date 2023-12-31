package com.example.ejerciciosexoplayer.objetos

import androidx.compose.ui.graphics.Color
import com.example.ejerciciosexoplayer.R.drawable
import com.example.ejerciciosexoplayer.R.raw

class SampleData {
    companion object {
        val songsSample = listOf(
            Cancion(raw.songone, "Ibai Mason - IA", drawable.ibai, Color.Red),
            Cancion(raw.songtwo, "Bad Bunny Me cago - IA", drawable.bunny, Color.Green),
            Cancion(raw.songthree, "Crab Rave - Noise Storm", drawable.crab, Color.Blue),
            Cancion(raw.songfour, "Icecream - Lethal Company", drawable.lethal, Color.Yellow),
            Cancion(raw.songfive, "Laboratory - Dville Santa", drawable.covid, Color.Magenta),
            Cancion(raw.songone, "Ibai Mason - IA", drawable.ibai, Color.Red),
            Cancion(raw.songtwo, "Bad Bunny Me cago - IA", drawable.bunny, Color.Green),
            Cancion(raw.songthree, "Crab Rave - Noise Storm", drawable.crab, Color.Blue),
            Cancion(raw.songfour, "Icecream - Lethal Company", drawable.lethal, Color.Yellow),
            Cancion(raw.songfive, "Laboratory - Dville Santa", drawable.covid, Color.Magenta)
        )

        val albumsSample = listOf(
            Album(1, "Album 1", drawable.ibai),
            Album(2, "Album 2", drawable.bunny),
            Album(3, "Album 3", drawable.crab),
            Album(4, "Album 4", drawable.lethal),
            Album(5, "Album 5", drawable.covid),
            Album(6, "Album 6", drawable.ibai)
        )

        val albumsWithSongsMap: Map<Int, AlbumWithSongs> = mapOf(
            1 to AlbumWithSongs(albumsSample[0], songsSample.subList(0, 2)),
            2 to AlbumWithSongs(albumsSample[1], songsSample.subList(2, 4)),
            3 to AlbumWithSongs(albumsSample[2], songsSample.subList(4, 6)),
            4 to AlbumWithSongs(albumsSample[3], songsSample.subList(6, 8)),
            5 to AlbumWithSongs(albumsSample[4], songsSample.subList(8, 10))
        )
    }
}