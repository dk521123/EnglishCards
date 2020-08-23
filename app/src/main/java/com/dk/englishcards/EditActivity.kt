package com.dk.englishcards

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.dk.englishcards.BaseActivity
import com.dk.englishcards.R
import com.dk.englishcards.cards.EnglishCard
import kotlinx.android.synthetic.main.activity_edit.*

class EditActivity : BaseActivity() {
    private var englishCardId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)

        val englishCardId = intent.getStringExtra(EnglishCard.ID_FIELD)
        if (englishCardId != null) {
            this.englishCardId = englishCardId
            val englishCard = super.dbHandler.readById(englishCardId)
            englishEditText.setText(englishCard?.english)
            motherLanguageEditText.setText(englishCard?.motherLanguage)
            memoEditText.setText(englishCard?.memo)

            this.deleteButton.visibility = View.VISIBLE
        } else {
            englishEditText.setText("")
            motherLanguageEditText.setText("")
            memoEditText.setText("")

            this.deleteButton.visibility = View.INVISIBLE
        }

        saveButton.setOnClickListener {
            when(val id = this.englishCardId) {
                null -> {
                    super.dbHandler.insert(
                        englishEditText.text.toString(),
                        motherLanguageEditText.text.toString(),
                        memoEditText.text.toString())
                }
                else -> {
                    super.dbHandler.update(
                        id,
                        englishEditText.text.toString(),
                        motherLanguageEditText.text.toString(),
                        memoEditText.text.toString())
                }
            }
            super.moveToMain()
        }

        cancelButton.setOnClickListener {
            super.moveToMain()
        }

        deleteButton.setOnClickListener {
            val id = this.englishCardId
            if (id != null) {
                super.dbHandler.delete(id)
            }
            super.moveToMain()
        }
    }

    // For menu
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        super.onCreateOptionsMenu(menu)

        val inflater = menuInflater
        inflater.inflate(R.menu.menu_for_sub, menu)
        return true
    }

    // Click events For menu
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.homeItem -> {
                super.moveToMain()
                true
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }
}