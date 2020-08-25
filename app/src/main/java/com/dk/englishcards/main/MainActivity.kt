package com.dk.englishcards.main

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.dk.englishcards.R
import com.dk.englishcards.cards.EnglishCard
import com.dk.englishcards.commons.BaseActivity
import com.dk.englishcards.edit.EditActivity
import com.dk.englishcards.exam.ExamActivity
import com.dk.englishcards.pref.PreferenceActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    // For menu
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        super.onCreateOptionsMenu(menu)

        val inflater = menuInflater
        inflater.inflate(R.menu.menu_for_home, menu)
        return true
    }

    // Click events For menu
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.preferenceItem -> {
                this.moveTo(PreferenceActivity::class.java)
                true
            }
            R.id.examItem -> {
                this.moveTo(ExamActivity::class.java)
                true
            }
            R.id.addItem -> {
                this.moveToEdit()
                true
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }

    override fun onResume() {
        super.onResume()

        val englishCards = super.dbHandler.readAll()
        val adapter = MainListRecyclerViewAdapter(englishCards.toTypedArray())
        mainRecyclerView.layoutManager = LinearLayoutManager(this)
        mainRecyclerView.adapter = adapter
        mainRecyclerView.setHasFixedSize(true)

        adapter.setOnItemClickListener(
            object: MainListRecyclerViewAdapter.OnItemClickListener {
                override fun onItemClickListener(
                    view: View,
                    position: Int,
                    targerItem: EnglishCard) {
                    moveTo(
                        EditActivity::class.java,
                        EnglishCard.ID_FIELD,
                        targerItem.englishCardId)
                }
            })

        addFloatingActionButton.setOnClickListener {
            super.moveToEdit()
        }
    }
}