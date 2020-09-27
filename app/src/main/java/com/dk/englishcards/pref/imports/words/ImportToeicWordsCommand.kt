package com.dk.englishcards.pref.imports.words

import android.content.res.AssetManager
import android.util.Log
import com.dk.englishcards.cards.EnglishCard

const val MIN_SIZE_FOR_TOEIC_WORD_ITEM = 2

class ImportToeicWordsCommand(assertManager: AssetManager) :
    BaseWordsCommand(assertManager, "imports/toeic_jp.tsv") {

    override fun execute() : Boolean {
        try {
            val csvReader = super.csvReader ?: return false
            val targetEnglishCards = mutableListOf<EnglishCard>()
            val csvContents = csvReader.readCsv(
                1, "UTF-8")
            for ((index, csvItems) in csvContents.withIndex()) {
                Log.i("execute", "Starting [$index]")
                if (csvItems.size < MIN_SIZE_FOR_TOEIC_WORD_ITEM) {
                    Log.w("execute", "Invalid data size ${csvItems.size} [$index]")
                    continue
                }
                val english = csvItems[0]
                val motherLanguage = csvItems[1]
                val memo = if (csvItems.size >= MIN_SIZE_FOR_TOEIC_WORD_ITEM + 1)
                    csvItems[2] else ""
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