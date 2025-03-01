package com.meally.meally.common.barcodeAnalyzer

import android.content.Context
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.camera.view.LifecycleCameraController
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner

@Composable
fun <T> BarcodeScanView(
    barcodeAnalyzer: BarcodeAnalyzer<T>,
    modifier: Modifier = Modifier,
) {
    val context: Context = LocalContext.current
    val lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current
    val cameraController: LifecycleCameraController = remember { LifecycleCameraController(context) }
    AndroidView(
        modifier = modifier.fillMaxWidth().aspectRatio(16f / 9f).clipToBounds(),
        factory = { viewContext ->
            PreviewView(viewContext)
                .apply {
                    layoutParams =
                        LinearLayout.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.MATCH_PARENT,
                        )
                    setBackgroundColor(0xF23456)
                    implementationMode = PreviewView.ImplementationMode.COMPATIBLE
                    scaleType = PreviewView.ScaleType.FILL_CENTER
                }.also { previewView ->
                    cameraController.setImageAnalysisAnalyzer(
                        ContextCompat.getMainExecutor(viewContext),
                        barcodeAnalyzer,
                    )
                    cameraController.bindToLifecycle(lifecycleOwner)
                    previewView.controller = cameraController
                }
        },
    )
}
