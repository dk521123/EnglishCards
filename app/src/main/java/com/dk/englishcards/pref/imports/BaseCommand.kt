package com.dk.englishcards.pref.imports

import android.content.res.AssetManager
import com.dk.englishcards.cards.EnglishCardDbHandler
import com.dk.englishcards.commons.CsvReader

abstract class BaseCommand(assertManager: AssetManager? = null, csvFilePath: String? = null) {
    protected var csvReader: CsvReader? = null

    init {
        if (assertManager != null && csvFilePath != null) {
            csvReader = CsvReader(assertManager, csvFilePath)
        }
    }

    abstract fun execute() : Boolean
}