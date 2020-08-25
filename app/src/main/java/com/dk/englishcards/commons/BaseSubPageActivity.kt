package com.dk.englishcards.commons

import android.view.Menu
import android.view.MenuItem
import com.dk.englishcards.R

open class BaseSubPageActivity : BaseActivity() {

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