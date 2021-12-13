package com.example.voicerecorderapp.utils.permission

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.provider.Settings
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.example.voicerecorderapp.R
import com.example.voicerecorderapp.presentation.viewmodel.SharedViewModel

object RecordingPermissions {

    private val RECORDING_REQUEST_CODE = 444

    fun Context.checkPermission(): Boolean {
        return ActivityCompat.checkSelfPermission(
            this,
            Manifest.permission.RECORD_AUDIO
        ) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
            this,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED
    }

    fun Fragment.requestPermissions() {
        val permissions = arrayOf(
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE
        )
        requestPermissions(permissions, RECORDING_REQUEST_CODE)
    }

    fun checkRequestPermissionResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray,
        startRecord: () -> Unit,
        createFolder: () -> Unit,
        context: Context
    ) {
        when (requestCode) {
            RECORDING_REQUEST_CODE -> {
                if (grantResults.isNotEmpty() &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED
                    && grantResults[1] == PackageManager.PERMISSION_GRANTED
                ) {
                    //createFolder()
                    //startRecord()
                    Toast.makeText(
                        context,
                        context.getString(R.string.permissions_granted),
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                        context.sendToSettings()
                    }
                    Toast.makeText(
                        context,
                        context.getString(R.string.permissions_denied),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    /**
     * Открываем экран настроек приложения,
     * т.к. с Android R окно запроса разрешений повторно не отображается
     */
    private fun Context.sendToSettings() {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        val uri = Uri.fromParts("package", packageName, null)
        intent.data = uri
        startActivity(intent)
    }


}