package com.example.smsreciever

import android.Manifest
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.Telephony
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.smsreciever.databinding.ActivityMainBinding
import java.lang.ref.WeakReference

/**
 * Main activity
 ** Check Request of receiving SMS permission
 ** Update TextView by caller phone number [CALLER_NUMBER]
 ** Register SMS Receiver [SMSReceiver] in onResume()
 ** Unregister SMS Receiver [SMSReceiver] in onStop()
 */
class MainActivity : AppCompatActivity(), SMSListener {

    private lateinit var binding: ActivityMainBinding
    private val smsReceiver = SMSReceiver(WeakReference(this))

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        checkSMSPermission()

        binding.callerText.text =
            String.format(resources.getString(R.string.caller), CALLER_NUMBER)
    }


    override fun onResume() {
        super.onResume()
        registerReceiver(
            smsReceiver,
            IntentFilter(Telephony.Sms.Intents.SMS_RECEIVED_ACTION)
        )
    }

    override fun onStop() {
        super.onStop()
        unregisterReceiver(smsReceiver)
    }

    override fun updateVerificationCode(code: String) {
        binding.verifyText.setText(code)
    }


    /**
     * Check the permission of receiving SMS
     */
    private fun checkSMSPermission() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.RECEIVE_SMS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.RECEIVE_SMS),
                SMS_RECEIVE_CODE
            )
        }
    }

    /**
     * Check request permission result and show Toast about permission state
     */
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when (requestCode) {
            SMS_RECEIVE_CODE -> if (grantResults.isNotEmpty() &&
                grantResults[0] == PackageManager.PERMISSION_GRANTED
            ) {
                Toast.makeText(this, getString(R.string.permissions_granted), Toast.LENGTH_SHORT)
                    .show()
            } else {
                Toast.makeText(this, getString(R.string.permissions_denied), Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }
}