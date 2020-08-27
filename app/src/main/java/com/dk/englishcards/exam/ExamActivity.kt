package com.dk.englishcards.exam

import android.os.Bundle
import androidx.viewpager2.widget.ViewPager2
import com.dk.englishcards.commons.BaseSubPageActivity
import com.dk.englishcards.R
import com.dk.englishcards.cards.EnglishCard
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.activity_exam.*

class ExamActivity : BaseSubPageActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exam)

        val englishCards = super.dbHandler.readAll()
        val exams = EnglishCard.toExams(
            englishCards.shuffled(),
            super.preference.isEnglishQuestion(),
            super.preference.getMaxNumberQuestion()
        )
        examViewPager.adapter = ExamAdapter(this, exams)
        examViewPager.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        TabLayoutMediator(examIndicator, examViewPager) { _, _ -> }.attach()
    }
}