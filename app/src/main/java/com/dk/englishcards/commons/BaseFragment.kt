package com.dk.englishcards.commons

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import com.dk.englishcards.cards.EnglishCardDbHandler
import com.dk.englishcards.pref.Preference

abstract class BaseFragment: Fragment() {
    protected lateinit var preference: Preference
    protected lateinit var englishCardDbHandler: EnglishCardDbHandler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val context = activity as Activity
        this.preference = Preference(context)
        this.englishCardDbHandler = EnglishCardDbHandler(context)
    }

    protected fun <T> moveTo(
        targetClass: Class<T>, key: String? = null, value: String? = null) {
        val context = activity as Activity
        val intentToMove = Intent(context, targetClass)
        if (key != null && value != null) {
            intentToMove.putExtra(key, value)
        }
        this.startActivity(intentToMove)
    }

    protected fun launchByUrl(targetUrl: String) {
        val url = Url(targetUrl)
        if (!url.isUrl()) {
            return
        } else if (url.isYouTube()) {
            this.launchYoutube(url)
        } else {
            this.launchBrowser(targetUrl)
        }
    }

    private fun launchBrowser(url: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        this.startActivity(intent)
    }

    private fun launchYoutube(targetUrl: Url) {
        try {
            val intent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse("vnd.youtube:" + targetUrl.getVideoId()))
            this.startActivity(intent)
        } catch (ex: Exception) {
            // error
            Log.w(ex.message, ex)

            // Retry
            this.launchBrowser(targetUrl.url)
        }
    }
}