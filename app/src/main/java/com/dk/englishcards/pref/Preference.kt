package com.dk.englishcards.pref

import android.content.Context
import android.content.Context.MODE_PRIVATE

class Preference(context: Context) {
    companion object {
        const val PREFERENCE_KEY = "com.dk.english-cards"
        // numberQuestion
        const val KEY_MAX_NUMBER_QUESTION = "numberQuestion"
        const val DEFAULT_MAX_NUMBER_QUESTION = 5
        // isEnglishQuestion
        const val KEY_IS_ENGLISH_QUESTION = "isEnglishQuestion"
        const val DEFAULT_IS_ENGLISH_QUESTION = true
    }

    private val preference =
        context.getSharedPreferences(PREFERENCE_KEY, MODE_PRIVATE)

    init {
        val numberQuestion = this.getMaxNumberQuestion()
        val isEnglishQuestion = this.isEnglishQuestion()
        this.preference.edit().apply {
            putInt(KEY_MAX_NUMBER_QUESTION, numberQuestion)
            putBoolean(KEY_IS_ENGLISH_QUESTION, isEnglishQuestion)
            commit()
        }
    }

    fun getMaxNumberQuestion(): Int {
        return preference.getInt(
            KEY_MAX_NUMBER_QUESTION,
            DEFAULT_MAX_NUMBER_QUESTION)
    }

    fun saveMaxNumberQuestion(maxNumberQuestion: Int) {
        this.preference.edit().apply {
            putInt(KEY_MAX_NUMBER_QUESTION, maxNumberQuestion)
            commit()
        }
    }

    fun isEnglishQuestion(): Boolean {
        return preference.getBoolean(KEY_IS_ENGLISH_QUESTION, DEFAULT_IS_ENGLISH_QUESTION)
    }

    fun saveIsEnglishQuestion(isEnglishQuestion: Boolean) {
        this.preference.edit().apply {
            putBoolean(KEY_IS_ENGLISH_QUESTION, isEnglishQuestion)
            commit()
        }
    }
}