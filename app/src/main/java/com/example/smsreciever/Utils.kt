package com.example.smsreciever

const val SMS_RECEIVE_CODE = 98
const val CALLER_NUMBER = "900"
private const val START_CHAR = ": "
private const val END_CHAR = "."

//get verification code from sms body
fun getVerifyCodeFromMessage(message: String): String {
    return message
            .substringAfter(START_CHAR)
            .substringBefore(END_CHAR)
}