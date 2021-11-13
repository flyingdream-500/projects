package com.example.voicerecorderapp.utils.permission

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment

object ListeningPermissions {

    private val EXTERNAL_STORAGE_REQUEST_CODE = 333

    fun Context.checkPermission(): Boolean {
        return ActivityCompat.checkSelfPermission(
            this,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED
    }

    fun Fragment.requestPermissions() {
        val permissions = arrayOf(
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE
        )
        requestPermissions(permissions, EXTERNAL_STORAGE_REQUEST_CODE)
    }

    fun checkRequestPermissionResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray,
        showRecords: () -> Unit,
        ) {

        when (requestCode) {
            EXTERNAL_STORAGE_REQUEST_CODE -> {
                if (grantResults.isNotEmpty() &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED
                ) {
                    showRecords()
                }
            }
            else -> {

            }
        }
    }

}