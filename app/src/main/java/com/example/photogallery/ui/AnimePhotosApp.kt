package com.example.photogallery.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.photogallery.R
import com.example.photogallery.ui.screens.HomeScreen
import com.example.photogallery.ui.screens.AnimeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AnimePhotosApp() {
    Scaffold(
        topBar = { AnimeTopAppBar() } // 移除滚动行为，让 TopAppBar 固定显示
    ) { innerPadding ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding) // 确保内容不会被 TopAppBar 遮挡
        ) {
            val animeViewModel: AnimeViewModel = viewModel(
                factory = AnimeViewModel.Factory
            )
            HomeScreen(
                animeUiState = animeViewModel.animeUiState,
                contentPadding = innerPadding, // 将 padding 传递给 LazyVerticalGrid
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AnimeTopAppBar(modifier: Modifier = Modifier) {
    CenterAlignedTopAppBar(
        title = {
            Text(
                text = stringResource(R.string.app_name),
                style = MaterialTheme.typography.headlineSmall,
            )
        },
        modifier = modifier
    )
}