package com.example.videoapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.ui.platform.LocalContext
import com.example.videoapp.data.VideoRepository
import com.example.videoapp.ui.theme.VideoAppTheme
import com.example.videoapp.ui.view.AppNavHost
import com.example.videoapp.ui.viewmodel.VideoViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val context = LocalContext.current
            val viewModel = VideoViewModel(VideoRepository(context))

            viewModel.loadVideos()

            VideoAppTheme {
                AppNavHost(viewModel = viewModel)
            }
        }
    }
}