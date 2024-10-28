package com.example.videoapp.ui.viewmodel

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.videoapp.data.VideoRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.io.File

class VideoViewModel(private val repository: VideoRepository) : ViewModel() {

    private val _videos = MutableStateFlow<List<Uri>>(emptyList())
    val videos = _videos.asStateFlow()

    fun loadVideos() {
        viewModelScope.launch {
            _videos.value = repository.getSavedVideos()
        }
    }

    fun createVideoFile(): File {
        return repository.createVideoFile()
    }
}

