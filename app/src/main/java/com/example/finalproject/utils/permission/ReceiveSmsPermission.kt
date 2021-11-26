package com.example.finalproject.utils.permission

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
import com.example.finalproject.R

object ReceiveSmsPermission {

    private const val SMS_RECEIVE_CODE = 98


    /**
     * Проверка разрешения на получение SMS
     */
    fun DialogFragment.checkSmsPermission(): Boolean {
        return ActivityCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.RECEIVE_SMS
        ) == PackageManager.PERMISSION_GRANTED
    }

    /**
     * Запрос разрешения на получение SMS
     */
    fun DialogFragment.requestSmsPermission() {
        requestPermissions(
            arrayOf(Manifest.permission.RECEIVE_SMS),
            SMS_RECEIVE_CODE
        )
    }

    /**
     * Получение результата запроса на разрешения
     */
    fun DialogFragment.requestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray,
    ) {

        when (requestCode) {
            SMS_RECEIVE_CODE ->
                if (grantResults.isNotEmpty() &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED
                ) {
                    Toast.makeText(
                        requireContext(),
                        getString(R.string.permissions_granted),
                        Toast.LENGTH_SHORT
                    ).show()
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
     * Открываем экран настроек приложения,
     * т.к. с Android R окно запроса разрешений повторно не отображается
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