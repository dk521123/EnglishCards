package com.dk.englishcards.commons

import android.app.Activity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.dk.englishcards.cards.EnglishCardDbHandler
import com.dk.englishcards.pref.Preference

abstract class BaseFragment: Fragment() {
    protected lateinit var preference: Preference
    protected lateinit var englishCardDbHandler: EnglishCardDbHandler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val context = activity as Activity
        this.preference = Preference(context)
        this.englishCardDbHandler = EnglishCardDbHandler(context)
    }
}