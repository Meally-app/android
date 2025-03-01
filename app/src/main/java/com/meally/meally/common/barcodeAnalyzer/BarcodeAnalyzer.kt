package com.meally.meally.common.barcodeAnalyzer

import androidx.annotation.OptIn
import androidx.camera.core.ExperimentalGetImage
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.common.InputImage
import java.util.concurrent.atomic.AtomicBoolean

@OptIn(ExperimentalGetImage::class)
class BarcodeAnalyzer<T>(
    private val process: (String?) -> T,
    private val onSuccess: (T) -> Unit,
    private val onFailure: (Exception) -> Unit,
) : ImageAnalysis.Analyzer {
    private val scanner = BarcodeScanning.getClient()

    private val isProcessingBarcode = AtomicBoolean(false)
    private val hasFoundBarcode = AtomicBoolean(false)

    override fun analyze(imageProxy: ImageProxy) {
        if (!isProcessingBarcode.compareAndSet(false, true)) {
            imageProxy.close()
            return
        }

        if (hasFoundBarcode.get()) {
            imageProxy.close()
            return
        }

        val mediaImage =
            imageProxy.image ?: run {
                imageProxy.close()
                return
            }
        val image = InputImage.fromMediaImage(mediaImage, imageProxy.imageInfo.rotationDegrees)
        scanner
            .process(image)
            .addOnSuccessListener {
                it
                    .map { process(it.rawValue) }
                    .firstOrNull()
                    ?.let {
                        if (hasFoundBarcode.compareAndSet(false, true)) {
                            onSuccess(it)
                        }
                    }
            }.addOnFailureListener(onFailure)
            .addOnCompleteListener {
                isProcessingBarcode.set(false)
                imageProxy.close()
            }
    }

    fun resetScanner() {
        isProcessingBarcode.set(false)
        hasFoundBarcode.set(false)
    }
}
