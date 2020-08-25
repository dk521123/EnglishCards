package com.dk.englishcards.cards

import android.util.Log
import com.dk.englishcards.exam.Exam
import io.realm.RealmObject
import io.realm.RealmResults
import io.realm.annotations.PrimaryKey
import io.realm.annotations.Required
import java.util.*

open class EnglishCard : RealmObject() {
    @PrimaryKey
    var englishCardId: String = UUID.randomUUID().toString()
    @Required
    var english: String = ""
    var motherLanguage: String = ""
    var memo: String = ""
    var createdAt: Date = Date()
    var updatedAt: Date = Date()

    companion object {
        const val ID_FIELD = "englishCardId"
        const val ENGLISH_FIELD = "english"

        @JvmStatic
        fun toExams(englishCards: RealmResults<EnglishCard>,
                    isEnglishQuestion: Boolean = true,
                    maxNumberQuestion: Int? = null
        ): List<Exam> {
            val returnValues = mutableListOf<Exam>()
            for ((index, englishCard) in englishCards.withIndex()) {
                if (maxNumberQuestion != null &&
                    maxNumberQuestion < index + 1) {
                    break
                }
                returnValues.add(toExam(englishCard, isEnglishQuestion))
            }
            return returnValues
        }

        @JvmStatic
        fun toExam(
            englishCard: EnglishCard,
            isEnglishQuestion: Boolean = true) : Exam {
            return if (isEnglishQuestion) {
                Exam(
                    englishCard.englishCardId,
                    englishCard.english,
                    englishCard.motherLanguage)
            } else {
                Exam(
                    englishCard.englishCardId,
                    englishCard.motherLanguage,
                    englishCard.english)
            }
        }
    }
}