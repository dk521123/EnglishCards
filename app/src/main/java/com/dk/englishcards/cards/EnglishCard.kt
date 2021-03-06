package com.dk.englishcards.cards

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.dk.englishcards.exam.words.EnglishWordsExam
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.Required
import java.io.File
import java.util.*

open class EnglishCard() : RealmObject() {
    @PrimaryKey
    var englishCardId: String = UUID.randomUUID().toString()
    @Required
    var english: String = ""
    var motherLanguage: String = ""
    var memo: String = ""
    var checkRequired: Float = 0.0F
    var url: String = ""
    var imagePath: String = ""
    var createdAt: Date = Date()
    var updatedAt: Date = Date()

    fun toImageBitmap() : Bitmap? {
        return toBitmap(this.imagePath)
    }

    companion object {
        const val ID_FIELD = "englishCardId"
        const val ENGLISH_FIELD = "english"

        @JvmStatic
        fun newInstance(
            english: String,
            motherLanguage: String,
            memo: String,
            url: String
        ) = EnglishCard().apply {
            this.english = english
            this.motherLanguage = motherLanguage
            this.memo = memo
            this.url = url
        }

        @JvmStatic
        fun toBitmap(imagePath: String): Bitmap? {
            val imageFile = File(imagePath)
            return BitmapFactory.decodeFile(imageFile.absolutePath)
        }

        @JvmStatic
        fun toExams(englishCards: List<EnglishCard>,
                    isEnglishQuestion: Boolean = true,
                    maxNumberQuestion: Int? = null
        ): List<EnglishWordsExam> {
            val returnValues = mutableListOf<EnglishWordsExam>()
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
            isEnglishQuestion: Boolean = true) : EnglishWordsExam {
            return if (isEnglishQuestion) {
                EnglishWordsExam(
                    englishCard.englishCardId,
                    englishCard.english,
                    englishCard.motherLanguage)
            } else {
                EnglishWordsExam(
                    englishCard.englishCardId,
                    englishCard.motherLanguage,
                    englishCard.english)
            }
        }
    }
}