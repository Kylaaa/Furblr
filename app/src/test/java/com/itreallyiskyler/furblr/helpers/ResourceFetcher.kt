package com.itreallyiskyler.furblr.helpers

import java.io.File
import java.nio.charset.Charset

object ResourceFetcher {
    private val RESOURCE_PATH : String = "src/test/res/"

    fun ReadTextFromResource(fileName : String, charset : Charset? = Charsets.UTF_32) : String
    {
        val resFile : File = File(RESOURCE_PATH + fileName)
        if (charset != null)
            return resFile.readText(charset!!)

        return resFile.readText()
    }
}