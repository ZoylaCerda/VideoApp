package com.example.videoapp.data

import android.content.Context
import android.net.Uri
import java.io.File

class VideoRepository(private val context: Context) {

    fun getSavedVideos(): List<Uri> {
        val videoDir = context.getExternalFilesDir("videos")
        return videoDir?.listFiles()?.map { Uri.fromFile(it) } ?: emptyList()
    }

    fun createVideoFile(): File {
        val videoDir = context.getExternalFilesDir("videos")
        if (videoDir != null && !videoDir.exists()) {
            videoDir.mkdirs()
        }
        return File(videoDir, "video_${System.currentTimeMillis()}.mp4")
    }
}
