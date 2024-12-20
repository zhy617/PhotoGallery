package com.example.photogallery.ui.screens


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.example.photogallery.R
import com.example.photogallery.network.AnimePhoto
import com.example.photogallery.ui.theme.AnimePhotosTheme
import kotlinx.serialization.InternalSerializationApi
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color

@OptIn(InternalSerializationApi::class)
@Composable
fun HomeScreen(
    animeUiState: AnimeUiState,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp),
) {
    when (animeUiState) {
        is AnimeUiState.Success ->
            PhotosGridScreen(photos = animeUiState.photos, modifier = modifier)

        is AnimeUiState.Loading -> LoadingScreen(modifier)
        is AnimeUiState.Error -> ErrorScreen(modifier)
    }
}


@Composable
fun LoadingScreen(modifier: Modifier = Modifier) {
    Image(
        modifier = modifier.size(200.dp),
        painter = painterResource(R.drawable.loading_img),
        contentDescription = stringResource(R.string.loading)
    )
}

@Composable
fun ErrorScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_connection_error),
            contentDescription = ""
        )

        Text(text = stringResource(R.string.loading_failed), modifier = Modifier.padding(16.dp))
    }
}


/**
 * ResultScreen displaying number of photos retrieved.
 */
@Composable
fun ResultScreen(photos: String, modifier: Modifier = Modifier) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
    ) {
        Text(text = photos)
    }
}

@OptIn(InternalSerializationApi::class)
@Composable
fun AnimePhotoCard(photo: AnimePhoto, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier,
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        shape = MaterialTheme.shapes.medium // 使用圆角框
    ) {
        AsyncImage(
            model = ImageRequest.Builder(context = LocalContext.current)
                .data(photo.imgSrc)
                .crossfade(true)
                .build(),
            contentScale = ContentScale.Crop,
            contentDescription = stringResource(R.string.anime_photo),
            modifier = modifier.fillMaxSize(),
            error = painterResource(R.drawable.ic_broken_image),
            placeholder = painterResource(R.drawable.loading_img)
        )
    }
}

@OptIn(InternalSerializationApi::class)
@Composable
fun PhotosGridScreen(
    photos: List<AnimePhoto>,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp)
) {
    // 1. 定义全屏状态和选中的图片
    val (isFullScreen, setFullScreen) = remember { mutableStateOf(false) }
    val (selectedPhoto, setSelectedPhoto) = remember { mutableStateOf<AnimePhoto?>(null) }

    if (isFullScreen && selectedPhoto != null) {
        // 2. 全屏显示图片
        FullScreenPhoto(
            photo = selectedPhoto,
            onDismiss = { setFullScreen(false) } // 点击全屏图片退出全屏
        )
    } else {
        // 3. 显示网格图片
        LazyVerticalGrid(
            columns = GridCells.Adaptive(150.dp),
            modifier = modifier.padding(horizontal = 4.dp, vertical = 4.dp),
            contentPadding = contentPadding
        ) {
            items(items = photos, key = { photo -> photo.imgSrc }) { photo ->
                AnimePhotoCard(
                    photo,
                    modifier = Modifier
//                        .padding(4.dp)
                        .fillMaxWidth()
                        .aspectRatio(1.5f)
                        .clickable { // 点击图片进入全屏模式
                            setSelectedPhoto(photo)
                            setFullScreen(true)
                        }
                )
            }
        }
    }
}

@OptIn(InternalSerializationApi::class)
@Composable
fun FullScreenPhoto(photo: AnimePhoto, onDismiss: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .clickable { onDismiss() }, // 点击全屏图片退出
        contentAlignment = Alignment.Center
    ) {
        AsyncImage(
            model = ImageRequest.Builder(context = LocalContext.current)
                .data(photo.imgSrc)
                .crossfade(true)
                .build(),
            contentDescription = null,
            contentScale = ContentScale.Fit, // 按比例全屏显示
            modifier = Modifier.fillMaxSize(),
            error = painterResource(R.drawable.ic_broken_image),
            placeholder = painterResource(R.drawable.loading_img)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ResultScreenPreview() {
    AnimePhotosTheme {
        ResultScreen(stringResource(R.string.placeholder_result))
    }
}