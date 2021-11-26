package com.example.finalproject.presentation.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.provider.Telephony
import android.widget.Toast
import com.example.finalproject.R
import com.example.finalproject.presentation.dialog.settings.AuthenticationDialog
import com.example.finalproject.utils.common.SMSReceiver.getVerifyCodeFromMessage
import com.example.finalproject.utils.constants.DefaultConstants.CALLER_NUMBER
import java.lang.ref.WeakReference

/**
 * Broadcast Receiver для отлавливания SMS от [CALLER_NUMBER] и обновления [AuthenticationDialog]
 */
class SmsReceiver(private val smsListener: WeakReference<SmsListener>) : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent != null && context != null) {
            val message = getVerifyCode(context, intent)
            smsListener.get()?.updateVerificationCode(message)
        }
    }

    /**
     * Получаем входящее СМС сообщение, проверяем его отправителя, после этого обновляем UI листенера
     */
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
                    context.getString(R.string.wrong_phone_number),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        return ""
    }


}