package com.dk.englishcards.importwords

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