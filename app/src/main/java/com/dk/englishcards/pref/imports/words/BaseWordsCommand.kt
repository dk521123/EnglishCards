package com.dk.englishcards.pref.imports.words

import android.content.res.AssetManager
import com.dk.englishcards.cards.EnglishCardDbHandler
import com.dk.englishcards.pref.imports.BaseCommand

abstract class BaseWordsCommand(assertManager: AssetManager? = null, csvFilePath: String? = null):
    BaseCommand(assertManager, csvFilePath) {
    protected val dbHandler: EnglishCardDbHandler = EnglishCardDbHandler()
}