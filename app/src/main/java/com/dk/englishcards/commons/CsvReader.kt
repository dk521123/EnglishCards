package com.dk.englishcards.commons

import android.content.res.AssetManager
import java.io.*
import java.nio.charset.Charset

class CsvReader(assertManager: AssetManager, filePath: String) {
    private val assertManager = assertManager
    private val filePath = filePath

    fun readCsv(
        skipLine: Int = 0,
        encoding: String = "UTF-8"
    ): List<List<String>> {
        val delimiter =
            when(File(filePath).absoluteFile.extension.toLowerCase()) {
                "tsv" -> "\t"
                else -> ","
            }
        return this.readCsv(
            this.assertManager.open(this.filePath),
            skipLine,
            encoding,
            delimiter
        )
    }

    private fun readCsv(
        inputCsv: InputStream,
        skipLine: Int,
        encoding: String,
        delimiter: String
    ): List<List<String>> {
        val csvContents = mutableListOf<List<String>>()
        try {
            BufferedReader(
                InputStreamReader(
                    inputCsv, Charset.forName(encoding)
                )
            ).use { fileReader ->
                var lineNumber: Int = 1
                fileReader.forEachLine { line ->
                    if (line.isNotBlank()) {
                        if (lineNumber > skipLine) {
                            val contents =
                                line.split(delimiter).map { value ->
                                    value.trim()
                                }
                            csvContents.add(contents)
                        }
                    }
                    lineNumber++;
                }
            }
        } catch (ex: IOException) {
            ex.printStackTrace()
        }
        return csvContents
    }
}