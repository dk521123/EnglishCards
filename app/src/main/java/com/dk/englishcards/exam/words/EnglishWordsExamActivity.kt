package com.dk.englishcards.exam.words

import android.os.Bundle
import androidx.viewpager2.widget.ViewPager2
import com.dk.englishcards.commons.BaseSubPageActivity
import com.dk.englishcards.R
import com.dk.englishcards.cards.EnglishCard
import com.dk.englishcards.exam.grammars.GrammarExamQuestionFragment
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.activity_english_words_exam.*

class EnglishWordsExamActivity : BaseSubPageActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_english_words_exam)

        val englishCards = super.dbHandler.readAll()
        val exams = EnglishCard.toExams(
            englishCards.shuffled(),
            super.preference.isEnglishQuestion(),
            super.preference.getMaxNumberQuestion()
        )
        englishWordsExamViewPager.adapter = EnglishWordsExamAdapter(this, exams)
        englishWordsExamViewPager.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        TabLayoutMediator(englishWordsExamIndicator, englishWordsExamViewPager) { _, _ -> }.attach()
    }
}