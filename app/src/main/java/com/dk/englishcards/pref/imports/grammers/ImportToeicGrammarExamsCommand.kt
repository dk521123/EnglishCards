package com.dk.englishcards.pref.imports.grammers

import android.content.res.AssetManager
import android.util.Log
import com.dk.englishcards.exam.grammars.GrammarExam

const val MIN_SIZE_FOR_TOEIC_GRAMMAR_ITEM = 6

class ImportToeicGrammarExamsCommand(assertManager: AssetManager) :
    BaseGrammarExamsCommand(assertManager, "imports/grammar_toeic_jp.tsv") {
    override fun execute(): Boolean {
        try {
            val csvReader = super.csvReader ?: return false
            val targetGrammarExams = mutableListOf<GrammarExam>()
            val csvContents = csvReader.readCsv(
                1, "UTF-8")
            for ((index, csvItems) in csvContents.withIndex()) {
                Log.i("execute", "Starting [$index]")
                if (csvItems.size < MIN_SIZE_FOR_TOEIC_GRAMMAR_ITEM) {
                    Log.w("execute", "Invalid data size ${csvItems.size} [$index]")
                    continue
                }
                val question = csvItems[0]
                val candidateA = csvItems[1]
                val candidateB = csvItems[2]
                val candidateC = csvItems[3]
                val candidateD = csvItems[4]
                val answer = csvItems[5]
                val commentary = if (csvItems.size >= MIN_SIZE_FOR_TOEIC_GRAMMAR_ITEM + 1)
                    csvItems[6] else ""
                val questionTranslation = if (csvItems.size >= MIN_SIZE_FOR_TOEIC_GRAMMAR_ITEM + 2)
                    csvItems[7] else ""
                val grammarExam = GrammarExam.newInstance(
                    question,
                    candidateA,
                    candidateB,
                    candidateC,
                    candidateD,
                    answer,
                    commentary,
                    questionTranslation)
                targetGrammarExams.add(grammarExam)
            }

            // Save
            this.dbHandler.insertAll(targetGrammarExams)

            return true
        } catch (ex: Exception) {
            ex.printStackTrace()
            return false
        }
    }
}