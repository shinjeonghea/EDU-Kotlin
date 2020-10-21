package com.example.flashlight

import android.content.Context
import android.hardware.camera2.CameraManager

class Torch(context: Context) {
    private var cameraId: String? = null
    private val cameraManager = context.getSystemService(Context.CAMERA_SERVICE) as CameraManager
    init {
        cameraId = getcameraId()
    }

    private fun getcameraId(): String? {

    }
}