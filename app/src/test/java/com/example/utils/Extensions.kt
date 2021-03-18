package com.example.utils

import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.buffer
import okio.source
import java.nio.charset.StandardCharsets

fun MockWebServer.enqueueResponse(jsonFile: String, code: Int) {
    val inputStream = javaClass.classLoader?.getResourceAsStream(jsonFile)
    val source = inputStream?.let { inputStream.source().buffer() }

    source?.let {
        enqueue(
            MockResponse()
                .setResponseCode(code)
                .setBody(source.readString(StandardCharsets.UTF_8))
        )
    }
}