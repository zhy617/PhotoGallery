package com.example.photogallery

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.photogallery.ui.AnimePhotosApp
import com.example.photogallery.ui.theme.AnimePhotosTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        setContent {
            AnimePhotosTheme() {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                ) {
                    AnimePhotosApp()
                }
            }
        }
    }
}
