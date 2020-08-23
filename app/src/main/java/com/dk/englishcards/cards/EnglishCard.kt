package com.dk.englishcards.cards

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.Required
import java.util.*

open class EnglishCard : RealmObject() {
    @PrimaryKey
    var englishCardId: String = UUID.randomUUID().toString()
    @Required
    var english: String = ""
    var motherLanguage: String = ""
    var memo: String = ""
    var createdAt: Date = Date()
    var updatedAt: Date = Date()

    companion object {
        const val ID_FIELD = "englishCardId"
        const val ENGLISH_FIELD = "english"
    }
}