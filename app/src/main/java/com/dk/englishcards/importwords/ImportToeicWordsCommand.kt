package com.dk.englishcards.importwords

import android.content.res.AssetManager
import com.dk.englishcards.cards.EnglishCard

class ImportToeicWordsCommand(assertManager: AssetManager) :
    BaseWordsCommand(assertManager, "imports/toeic_jp.tsv") {
}