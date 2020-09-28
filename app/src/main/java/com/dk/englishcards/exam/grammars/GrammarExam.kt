package com.dk.englishcards.exam.grammars

import com.dk.englishcards.cards.EnglishCard
import com.dk.englishcards.exam.words.EnglishWordsExam
import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.Required
import java.util.*

open class GrammarExam : RealmObject() {
    @PrimaryKey
    var grammarExamId: String = UUID.randomUUID().toString()
    @Required
    var question: String = ""
    @Required
    var candidateA: String = ""
    @Required
    var candidateB: String = ""
    @Required
    var candidateC: String = ""
    @Required
    var candidateD: String = ""
    @Required
    var answer: String = ""
    var commentary: String = ""
    var questionTranslation: String = ""

    companion object {
        const val ID_FIELD = "grammarExamId"

        @JvmStatic
        fun newInstance(
            question: String,
            candidateA: String,
            candidateB: String,
            candidateC: String,
            candidateD: String,
            answer: String,
            commentary: String = "",
            questionTranslation: String = ""
        ) = GrammarExam().apply {
            this.question = question
            this.candidateA = candidateA
            this.candidateB = candidateB
            this.candidateC = candidateC
            this.candidateD = candidateD
            this.answer = answer
            this.commentary = commentary
            this.questionTranslation = questionTranslation
        }
    }
}