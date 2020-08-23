package com.dk.englishcards

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dk.englishcards.cards.EnglishCardDbHandler

open class BaseActivity : AppCompatActivity() {
    protected lateinit var dbHandler: EnglishCardDbHandler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        this.dbHandler = EnglishCardDbHandler(this)
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