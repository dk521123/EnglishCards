package com.dk.englishcards.detail

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.dk.englishcards.cards.EnglishCard

class DetailAdapter(fragmentActivity: FragmentActivity, englishCards: List<EnglishCard>)
    : FragmentStateAdapter(fragmentActivity) {
    private val englishCards = englishCards

    override fun getItemCount(): Int {
        return this.englishCards.size
    }

    override fun createFragment(position: Int): Fragment {
        val englishCard = englishCards[position]
        return DetailFragment.newInstance(englishCard)
    }
}