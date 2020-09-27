package com.dk.englishcards.pref.imports.grammers

class ClearAllGrammarExamsCommand : BaseGrammarExamsCommand() {
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