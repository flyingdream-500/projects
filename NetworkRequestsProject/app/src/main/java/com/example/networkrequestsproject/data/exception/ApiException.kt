package com.example.networkrequestsproject.data.exception

import okio.IOException

sealed class ApiException(override val message: String?) : IOException() {

    data class RequestBodyException(override val message: String? = "Request Body Error") :
        ApiException(message)

    data class ResponseStatusException(override val message: String? = "Response is not successful") :
        ApiException(message)

    data class ResponseBodyException(override val message: String? = "Response Body Exception") :
        ApiException(message)

    data class ParsingException(override val message: String? = "Parsing Exception") :
        ApiException(message)
}