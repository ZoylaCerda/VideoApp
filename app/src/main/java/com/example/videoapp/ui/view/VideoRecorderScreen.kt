import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.core.content.ContextCompat
import com.example.videoapp.ui.viewmodel.VideoViewModel
import java.util.concurrent.Executors

@Composable
fun VideoRecorderScreen(viewModel: VideoViewModel, onVideoSaved: (Uri) -> Unit) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val cameraExecutor = remember { Executors.newSingleThreadExecutor() }

    var recording: Recording? by remember { mutableStateOf(null) }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions(),
        onResult = { permissions ->
            val allGranted = permissions.values.all { it }
            if (!allGranted) {
                return@rememberLauncherForActivityResult
            }
        }
    )

    fun checkPermissions(): Boolean {
        return ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(context, Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED
    }

    val videoCapture = remember {
        val recorder = Recorder.Builder()
            .setQualitySelector(QualitySelector.from(Quality.HIGHEST))
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
                try {
                    cameraProvider.bindToLifecycle(
                        lifecycleOwner,
                        CameraSelector.DEFAULT_BACK_CAMERA,
                        preview,
                        videoCapture
                    )
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        )

        Button(
            onClick = {
                if (!checkPermissions()) {
                    launcher.launch(
                        arrayOf(
                            Manifest.permission.CAMERA,
                            Manifest.permission.RECORD_AUDIO
                        )
                    )
                    return@Button
                }

                if (recording == null) {
                    val videoFile = viewModel.createVideoFile()
                    val outputOptions = FileOutputOptions.Builder(videoFile).build()

                    recording = videoCapture.output
                        .prepareRecording(context, outputOptions)
                        .withAudioEnabled()
                        .start(ContextCompat.getMainExecutor(context)) { event ->
                            when (event) {
                                is VideoRecordEvent.Finalize -> {
                                    if (event.hasError()) {
                                        event.cause?.printStackTrace()
                                    } else {
                                        val savedUri = Uri.fromFile(videoFile)
                                        onVideoSaved(savedUri)
                                    }
                                    recording = null
                                }
                            }
                        }
                } else {
                    recording?.stop()
                    recording = null
                }
            },
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(16.dp)
        ) {
            Text(if (recording == null) "Grabar" else "Detener")
        }
    }
}
