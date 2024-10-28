package com.example.videoapp.ui.view

import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import androidx.camera.core.CameraSelector
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.video.*
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.videoapp.ui.viewmodel.VideoViewModel
import java.util.concurrent.Executors

@Composable
fun VideoRecorderScreen(viewModel: VideoViewModel, onVideoSaved: (Uri) -> Unit) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val cameraExecutor = remember { Executors.newSingleThreadExecutor() }

    // Recordings state
    var recording: Recording? by remember { mutableStateOf(null) }

    // Configuración del Recorder y VideoCapture
    val videoCapture = remember {
        val recorder = Recorder.Builder()
            .setQualitySelector(QualitySelector.from(Quality.HIGHEST)) // Selector de calidad
            .build()
        VideoCapture.withOutput(recorder)
    }

    Box(modifier = Modifier.fillMaxSize()) {
        AndroidView(
            factory = { PreviewView(it) },
            modifier = Modifier.fillMaxSize(),
            update = { previewView ->
                val cameraProvider = ProcessCameraProvider.getInstance(context).get()
                val preview = Preview.Builder().build().apply {
                    setSurfaceProvider(previewView.surfaceProvider)
                }
                cameraProvider.unbindAll()
                cameraProvider.bindToLifecycle(
                    lifecycleOwner,
                    CameraSelector.DEFAULT_BACK_CAMERA,
                    preview,
                    videoCapture
                )
            }
        )

        Button(
            onClick = {
                if (recording == null) {
                    // Iniciar la grabación
                    val videoFile = viewModel.createVideoFile()
                    val outputOptions = FileOutputOptions.Builder(videoFile).build()

                    if (ActivityCompat.checkSelfPermission(
                            this,
                            Manifest.permission.RECORD_AUDIO
                        ) != PackageManager.PERMISSION_GRANTED
                    ) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                        return@Button
                    }
                    recording = videoCapture.output
                        .prepareRecording(context, outputOptions)
                        .withAudioEnabled()
                        .start(ContextCompat.getMainExecutor(context)) { event ->
                            when (event) {
                                is VideoRecordEvent.Finalize -> {
                                    onVideoSaved(event.outputResults.outputUri)
                                    recording = null
                                }
                            }
                        }
                } else {
                    // Detener la grabación
                    recording?.stop()
                    recording = null
                }
            },
            modifier = Modifier.align(Alignment.BottomCenter).padding(16.dp)
        ) {
            Text(if (recording == null) "Grabar" else "Detener")
        }
    }
}
