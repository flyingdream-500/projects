package com.example.smsreciever

const val SMS_RECEIVE_CODE = 98
const val CALLER_NUMBER = "900"
private const val START_CHAR = ": "
private const val END_CHAR = "."
private const val NUMBER_REGEX = "[0-9]+"

//get verification code from sms body by Regex
fun getVerifyCodeFromMessage(message: String): String {
    val regex = NUMBER_REGEX.toRegex()
    val matcher = regex.find(message)
    return matcher?.value ?: ""
}

//get verification code from sms body by substring
fun oldVerifyCodeFromMessage(message: String): String {
    return message
            .substringAfter(START_CHAR)
            .substringBefore(END_CHAR)
}
