package com.example.videoapp.ui.view

import android.net.Uri
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.videoapp.ui.viewmodel.VideoViewModel

@Composable
fun AppNavHost(viewModel: VideoViewModel) {
    var selectedVideo by remember { mutableStateOf<Uri?>(null) }

    if (selectedVideo != null) {
        VideoPlayerScreen(uri = selectedVideo!!)
    } else {
        Column {
            VideoRecorderScreen(viewModel) { uri ->
                viewModel.loadVideos()
                selectedVideo = uri
            }
            Spacer(modifier = Modifier.height(16.dp))
            VideoListScreen(viewModel) { uri -> selectedVideo = uri }
        }
    }
}
