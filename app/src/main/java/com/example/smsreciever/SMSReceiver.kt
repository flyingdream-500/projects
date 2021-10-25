package com.example.smsreciever

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.provider.Telephony
import android.widget.Toast
import java.lang.ref.WeakReference

/**
 * Receiver for catching SMS from [CALLER_NUMBER] and update [MainActivity]
 */
class SMSReceiver(private val smsListener: WeakReference<SMSListener>) : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent != null && context != null) {
            val message = getVerifyCode(context, intent)
            smsListener.get()?.updateVerificationCode(message)
        }
    }

    //get verification code
    private fun getVerifyCode(context: Context, intent: Intent): String {
        if (intent.action == Telephony.Sms.Intents.SMS_RECEIVED_ACTION) {
            val smsMessages = Telephony.Sms.Intents.getMessagesFromIntent(intent)
            val messageBody = StringBuilder()
            var originatingAddress = ""
            for (message in smsMessages) {
                originatingAddress = message.displayOriginatingAddress
                messageBody.append(message.messageBody)
            }
            if (originatingAddress == CALLER_NUMBER) {
                return getVerifyCodeFromMessage(messageBody.toString())
            } else {
                Toast.makeText(
                    context,
                    context.getString(R.string.wrong_caller),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        return ""
    }


}