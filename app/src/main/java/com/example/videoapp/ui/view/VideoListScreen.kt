package com.example.videoapp.ui.view

import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.videoapp.ui.viewmodel.VideoViewModel

@Composable
fun VideoListScreen(viewModel: VideoViewModel, onVideoSelected: (Uri) -> Unit) {
    val videos by viewModel.videos.collectAsState()

    LazyColumn {
        items(videos) { uri ->
            VideoListItem(uri = uri, onVideoSelected = onVideoSelected)
        }
    }
}

@Composable
fun VideoListItem(uri: Uri, onVideoSelected: (Uri) -> Unit) {
    Column(
        modifier = Modifier
            .clickable { onVideoSelected(uri) }
            .padding(16.dp)
    ) {
        Text(text = uri.lastPathSegment ?: "Video sin nombre")
    }
}
