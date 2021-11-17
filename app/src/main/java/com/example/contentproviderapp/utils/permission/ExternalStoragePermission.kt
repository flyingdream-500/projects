package com.example.contentproviderapp.utils.permission

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.provider.Settings
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import com.example.contentproviderapp.R


object ExternalStoragePermission {

    private val STORAGE_REQUEST_CODE = 98

    /**
     * Check External Storage permission
     */
    fun Context.checkStoragePermission(): Boolean {
        return ActivityCompat.checkSelfPermission(
            this,
            Manifest.permission.READ_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED
    }

    /**
     * Request External Storage permission
     */
    fun DialogFragment.requestExternalPermission() {
        requestPermissions(
            arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
            STORAGE_REQUEST_CODE
        )
    }

    /**
     * Request External Storage result
     */
    fun DialogFragment.requestExternalPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray,
        loadImages: () -> Unit
    ) {
        when (requestCode) {
            STORAGE_REQUEST_CODE ->
                if (grantResults.isNotEmpty() &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED
                ) {
                    Toast.makeText(
                        requireContext(),
                        getString(R.string.permissions_granted),
                        Toast.LENGTH_SHORT
                    ).show()
                    loadImages.invoke()

                } else {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                        requireContext().sendToSettings()
                    }
                    Toast.makeText(
                        requireContext(),
                        getString(R.string.permissions_denied),
                        Toast.LENGTH_SHORT
                    ).show()
                    dialog?.cancel()
                }
        }
    }


    /**
     * Open settings screen
     * because in Android R  permission request window is not displayed again
     */
    private fun Context.sendToSettings() {
        startActivity(
            Intent(
                Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                Uri.fromParts("package", packageName, null)
            )
        )
    }

}