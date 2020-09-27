package com.dk.englishcards.pref.imports.grammers

import android.content.res.AssetManager
import com.dk.englishcards.exam.grammars.GrammarExamDbHandler
import com.dk.englishcards.pref.imports.BaseCommand

abstract class BaseGrammarExamsCommand(assertManager: AssetManager? = null, csvFilePath: String? = null):
    BaseCommand(assertManager, csvFilePath) {
    protected val dbHandler: GrammarExamDbHandler = GrammarExamDbHandler()
}