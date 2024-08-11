package com.example.progress

import android.content.Context
import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

fun checkPermissions(context: Context): Boolean {
    val REQUIRED_PERMISSIONS = arrayOf(
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE
    )

    // Флаг, указывающий, что все разрешения предоставлены
    var allPermissionsGranted = true

    // Проверка каждого разрешения
    REQUIRED_PERMISSIONS.forEach {
        if (
            ContextCompat.checkSelfPermission(
                context, it
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            allPermissionsGranted = false
            // Запрос разрешений, если хотя бы одно не предоставлено
            if (context is Activity) {
                ActivityCompat.requestPermissions(
                    context, REQUIRED_PERMISSIONS, 101
                )
            }
        }
    }

    return allPermissionsGranted
}
