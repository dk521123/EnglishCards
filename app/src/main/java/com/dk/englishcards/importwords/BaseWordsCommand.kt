package com.dk.englishcards.importwords

import android.content.res.AssetManager
import android.util.Log
import com.dk.englishcards.cards.EnglishCard
import com.dk.englishcards.cards.EnglishCardDbHandler
import com.dk.englishcards.commons.CsvReader

abstract class BaseWordsCommand(assertManager: AssetManager? = null, csvFilePath: String? = null) {
    protected val dbHandler: EnglishCardDbHandler = EnglishCardDbHandler()
    private var csvReader: CsvReader? = null

    init {
        if (assertManager != null && csvFilePath != null) {
            csvReader = CsvReader(assertManager, csvFilePath)
        }
    }

    open fun execute() : Boolean {
        try {
            val csvReader = this.csvReader ?: return false
            val targetEnglishCards = mutableListOf<EnglishCard>()
            val csvContents = csvReader.readCsv(
                1, "UTF-8")
            for ((index, csvItems) in csvContents.withIndex()) {
                Log.i("execute", "Starting [$index]")
                if (csvItems.size < 2) {
                    Log.w("execute", "Invalid data size ${csvItems.size} [$index]")
                    continue
                }
                val english = csvItems[0]
                val motherLanguage = csvItems[1]
                val memo = if (csvItems.size >= 3) csvItems[2] else ""
                val englishCard = EnglishCard.newInstance(
                    english, motherLanguage, memo)
                targetEnglishCards.add(englishCard)
            }

            // Save
            this.dbHandler.insertAll(targetEnglishCards)

            return true
        } catch (ex: Exception) {
            ex.printStackTrace()
            return false
        }
    }
}