package com.dk.englishcards.exam.grammars

import io.realm.Realm
import io.realm.RealmConfiguration
import io.realm.RealmResults

class GrammarExamDbHandler() {
    private var realm: Realm

    init {
        val realmConfiguration =
            RealmConfiguration.Builder()
                .deleteRealmIfMigrationNeeded()
                .build()
        this.realm = Realm.getInstance(realmConfiguration)
    }

    fun readAll(): RealmResults<GrammarExam> {
        return this.realm.where(GrammarExam::class.java)
            .findAll()
    }

    fun readById(grammarExamId: String): GrammarExam? {
        return this.realm.where(GrammarExam::class.java)
            .equalTo(GrammarExam.ID_FIELD, grammarExamId)
            .findFirst()
    }

    fun insertAll(grammarExams: List<GrammarExam>) = try {
        this.realm.executeTransaction {
            this.realm.insert(grammarExams)
        }
    } catch (ex: Exception) {
        ex.printStackTrace()
    }

    fun deleteAll() = try {
        this.realm.executeTransaction {
            val grammarExams = this.readAll()
            grammarExams?.deleteAllFromRealm()
        }
    } catch (ex: Exception) {
        ex.printStackTrace()
    }
}