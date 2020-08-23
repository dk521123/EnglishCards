package com.dk.englishcards.exam

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class ExamAdapter(fragmentActivity: FragmentActivity, exams: Array<Exam>)
    : FragmentStateAdapter(fragmentActivity) {
    private val exams = exams

    override fun createFragment(position: Int): Fragment {
        val index = (position / 2)
        val exam = exams[index]
        val questionOrAnswer = if ((position % 2) == 0)
            exam.question else exam.answer
        return ExamFragment.newInstance(position, questionOrAnswer)
    }

    override fun getItemCount(): Int {
        return exams.size * 2
    }
}