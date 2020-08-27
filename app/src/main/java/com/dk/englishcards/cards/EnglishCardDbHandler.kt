package com.dk.englishcards.cards

import android.content.Context
import android.view.textclassifier.TextLanguage
import io.realm.Realm
import io.realm.RealmConfiguration
import io.realm.RealmResults
import io.realm.Sort
import java.util.*

class EnglishCardDbHandler(context: Context? = null) {
    private var realm: Realm

    init {
        if (context != null) {
            Realm.init(context)
        }

        val realmConfiguration =
            RealmConfiguration.Builder()
                .deleteRealmIfMigrationNeeded()
                .build()
        this.realm = Realm.getInstance(realmConfiguration)
    }

    fun readAll(): RealmResults<EnglishCard> {
        return this.realm.where(EnglishCard::class.java)
            .findAll()
            .sort(EnglishCard.ENGLISH_FIELD, Sort.ASCENDING)
    }

    fun readById(englishCardId: String): EnglishCard? {
        return this.realm.where(EnglishCard::class.java)
            .equalTo(EnglishCard.ID_FIELD, englishCardId)
            .findFirst()
    }

    fun insertAll(englishCards: List<EnglishCard>)  = try {
        this.realm.executeTransaction {
            this.realm.insert(englishCards)
        }
    } catch (ex: Exception) {
        ex.printStackTrace()
    }

    fun insert(english: String, motherLanguage: String, memo: String) = try {
        this.realm.executeTransaction {
            val englishCard =
                this.realm.createObject(EnglishCard::class.java!!, UUID.randomUUID().toString())
            englishCard.english = english
            englishCard.motherLanguage = motherLanguage
            englishCard.memo = memo
            englishCard.createdAt = Date()
            englishCard.updatedAt = Date()
        }
    } catch (ex: Exception) {
        ex.printStackTrace()
    }

    fun update(englishCardId: String, english: String, motherLanguage: String, memo: String) = try {
        this.realm.executeTransaction {
            val englishCard = this.readById(englishCardId)
            englishCard?.english = english
            englishCard?.motherLanguage = motherLanguage
            englishCard?.memo = memo
            englishCard?.updatedAt = Date()
        }
    } catch (ex: Exception) {
        ex.printStackTrace()
    }

    fun deleteAll() = try {
        this.realm.executeTransaction {
            val englishCards = this.readAll()
            englishCards?.deleteAllFromRealm()
        }
    } catch (ex: Exception) {
        ex.printStackTrace()
    }

    fun delete(englishCardId: String) = try {
        this.realm.executeTransaction {
            val englishCard = this.readById(englishCardId)
            englishCard?.deleteFromRealm()
        }
    } catch (ex: Exception) {
        ex.printStackTrace()
    }
}