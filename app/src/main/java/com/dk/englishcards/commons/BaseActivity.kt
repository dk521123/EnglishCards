package com.dk.englishcards.commons

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dk.englishcards.edit.EditActivity
import com.dk.englishcards.main.MainActivity
import com.dk.englishcards.cards.EnglishCardDbHandler
import com.dk.englishcards.pref.Preference

abstract class BaseActivity : AppCompatActivity() {
    protected lateinit var dbHandler: EnglishCardDbHandler
    protected lateinit var preference: Preference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        this.dbHandler = EnglishCardDbHandler(this)
        this.preference = Preference(this)
    }

    protected fun moveToMain() {
        this.moveTo(MainActivity::class.java)
    }

    protected fun moveToEdit() {
        this.moveTo(EditActivity::class.java)
    }

    protected fun <T> moveTo(
        targetClass: Class<T>, key: String? = null, value: String? = null) {
        val intentToMove = Intent(this, targetClass)
        if (key != null && value != null) {
            intentToMove.putExtra(key, value)
        }
        this.startActivity(intentToMove)
    }
}