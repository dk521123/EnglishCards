package com.dk.englishcards.detail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dk.englishcards.R
import com.dk.englishcards.cards.EnglishCard
import com.dk.englishcards.commons.Url
import kotlinx.android.synthetic.main.fragment_detail.*

private const val ARG_PARAM_ENGLISH = "english"
private const val ARG_PARAM_MOTHER_LANGUAGE = "motherLanguage"
private const val ARG_PARAM_MEMO = "memo"
private const val ARG_PARAM_URL = "url"
private const val ARG_PARAM_IMAGE_PATH = "imagePath"
private const val ARG_PARAM_CHECK_REQUIRED = "checkRequired"

class DetailFragment : Fragment() {
    private lateinit var english: String
    private lateinit var motherLanguage: String
    private lateinit var memo: String
    private lateinit var url: String
    private lateinit var imagePath: String
    private var checkRequired: Float = 0.0F

    companion object {
        fun newInstance(englishCard: EnglishCard) =
            DetailFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM_ENGLISH, englishCard.english)
                    putString(ARG_PARAM_MOTHER_LANGUAGE, englishCard.motherLanguage)
                    putString(ARG_PARAM_MEMO, englishCard.memo)
                    putString(ARG_PARAM_URL, englishCard.url)
                    putString(ARG_PARAM_IMAGE_PATH, englishCard.imagePath)
                    putFloat(ARG_PARAM_CHECK_REQUIRED, englishCard.checkRequired)
                }
            }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            english = it.getString(ARG_PARAM_ENGLISH).toString()
            motherLanguage = it.getString(ARG_PARAM_MOTHER_LANGUAGE).toString()
            memo = it.getString(ARG_PARAM_MEMO).toString()
            url = it.getString(ARG_PARAM_URL).toString()
            imagePath = it.getString(ARG_PARAM_IMAGE_PATH).toString()
            checkRequired = it.getFloat(ARG_PARAM_CHECK_REQUIRED)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        englishValueTextView.text = this.english
        motherLanguageValueTextView.text = this.motherLanguage
        memoValueTextView.text = this.memo
        checkRequiredValueRatingBar.rating = this.checkRequired
        if (!this.imagePath.isNullOrEmpty()) {
            val bitmap = EnglishCard.toBitmap(this.imagePath)
            englishWordValueImageView.setImageBitmap(bitmap)
        }

        if (this.url == null || !Url(this.url).isUrl()) {
            browseFloatingActionButton.hide()
        } else {
            browseFloatingActionButton.show()
            browseFloatingActionButton.setOnClickListener {
                this.launchByUrl(this.url)
            }
        }
    }

    private fun launchByUrl(targetUrl: String) {
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