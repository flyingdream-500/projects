package com.example.networkrequestsproject.data

import com.example.networkrequestsproject.data.converter.UserConverter
import com.example.networkrequestsproject.domain.model.Person
import com.example.networkrequestsproject.domain.model.User
import okio.IOException
import java.io.OutputStreamWriter
import java.net.URL
import java.nio.charset.StandardCharsets
import javax.net.ssl.HttpsURLConnection

class UrlConnectionUserRepository(converter: UserConverter) : BaseUserRepository(converter) {


    override fun postPerson(person: Person): String {
        val requestUrl = URL(REQUEST_URL_POST)
        val urlConnection = requestUrl.openConnection() as HttpsURLConnection
        urlConnection.apply {
            setRequestProperty("Content-Type", "application/json")
            requestMethod = "POST"
            doOutput = true
        }

        OutputStreamWriter(urlConnection.outputStream, StandardCharsets.UTF_8).use {
            it.write(getRequestBody(person))
        }

        try {
            urlConnection.inputStream.bufferedReader().use { reader ->
                if (urlConnection.responseCode != HttpsURLConnection.HTTP_CREATED) throw IOException(urlConnection.responseMessage)
                return reader.readLine()
            }
        } finally {
            urlConnection.disconnect()
        }
    }


    override fun getUsers(): List<User>? {
        val requestUrl = URL(REQUEST_URL)
        val urlConnection = requestUrl.openConnection() as HttpsURLConnection
        urlConnection.apply {
            requestMethod = "GET"
            doInput = true
        }
        try {
            urlConnection.inputStream.bufferedReader().use { reader ->
                if (urlConnection.responseCode != HttpsURLConnection.HTTP_OK) throw IOException(urlConnection.responseMessage)
                return getListResponse(reader.readLine())?.data
            }
        } finally {
            urlConnection.disconnect()
        }

    }

}