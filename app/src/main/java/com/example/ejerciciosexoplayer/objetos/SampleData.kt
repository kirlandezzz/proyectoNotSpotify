package com.example.ejerciciosexoplayer.objetos

import androidx.compose.ui.graphics.Color
import com.example.ejerciciosexoplayer.R

class SampleData {
    companion object {
        val albumsSample = listOf(
            Album(1, "Album 1", R.drawable.ibai),
            Album(2, "Album 2", R.drawable.bunny),
            Album(3, "Album 3", R.drawable.crab),
            Album(4, "Album 4", R.drawable.lethal),
            Album(5, "Album 5", R.drawable.covid)
        )

        val songsSample = listOf(
            Cancion("All Wrong", R.drawable.ibai, Color.Red),
            Cancion("Things I Can't Change", R.drawable.lethal, Color.Blue),
            Cancion("All Wrong", R.drawable.ibai, Color.Red),
            Cancion("Things I Can't Change", R.drawable.lethal, Color.Blue),
            Cancion("All Wrong", R.drawable.ibai, Color.Red),
            Cancion("Things I Can't Change", R.drawable.lethal, Color.Blue),
            Cancion("All Wrong", R.drawable.ibai, Color.Red),
            Cancion("Things I Can't Change", R.drawable.lethal, Color.Blue),
            Cancion("All Wrong", R.drawable.ibai, Color.Red),
            Cancion("Things I Can't Change", R.drawable.lethal, Color.Blue),
            Cancion("All Wrong", R.drawable.ibai, Color.Red),
            Cancion("Things I Can't Change", R.drawable.lethal, Color.Blue),
            // Add more songs as needed
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