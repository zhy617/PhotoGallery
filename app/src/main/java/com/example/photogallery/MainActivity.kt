package com.example.photogallery

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
//import com.example.affirmations.data.Datasource
//import com.example.affirmations.ui.theme.AffirmationsTheme
import androidx.compose.ui.graphics.Color
import com.example.photogallery.ui.theme.PhotoGalleryTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PhotoGalleryTheme {
//                Scaffold(modifier = Modifier.fillMaxSize()) {
//                    innerPadding ->
//                    Greeting(
//                        name = "Android",
//                        modifier = Modifier.padding(innerPadding)
//                    )
//                }
//                ScrollableList()
                SimpleLazyGrid()
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Composable
fun ScrollableList() {
    val list = ('A'..'Z').map { it.toString() }
    LazyColumn {
        items(list) { letter: String ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
            ) {
                Text(
                    text = letter,
                    textAlign = TextAlign.Center,
                    fontSize = 20.sp,
                    modifier = Modifier
                        .fillMaxSize()
                        .wrapContentHeight(Alignment.CenterVertically)
                )
            }
        }
    }
}

@Composable
fun SimpleLazyGrid() {
    LazyVerticalGrid(
        modifier = Modifier.fillMaxWidth(),
        // 固定两列
        columns = GridCells.Fixed(3),
        content = {
            items(1000) {
                RandomColorBox(modifier = Modifier.height(200.dp))
            }
        }
    )
}

@Composable
fun RandomColorBox(modifier: Modifier) {
    val color = Color(
        red = (0..255).random(),
        green = (0..255).random(),
        blue = (0..255).random()
    )
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(color)
    )
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
//    val text = Datasource().loadAffirmations().size.toString()
    PhotoGalleryTheme() {
        SimpleLazyGrid()
    }
}