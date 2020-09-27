package com.dk.englishcards.pref.imports.words

class ClearAllWordsCommand : BaseWordsCommand() {
    override fun execute(): Boolean {
        return try {
            super.dbHandler.deleteAll()
            true
        } catch (ex: Exception) {
            ex.printStackTrace()
            false
        }
    }
}