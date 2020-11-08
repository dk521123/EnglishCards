package com.dk.englishcards.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dk.englishcards.R
import com.dk.englishcards.cards.EnglishCard
import com.dk.englishcards.commons.BaseFragment
import com.dk.englishcards.commons.Url
import com.dk.englishcards.edit.EditActivity
import kotlinx.android.synthetic.main.fragment_detail.*

private const val ARG_PARAM_ENGLISH_CARD_ID = "englishCardId"
private const val ARG_PARAM_ENGLISH = "english"
private const val ARG_PARAM_MOTHER_LANGUAGE = "motherLanguage"
private const val ARG_PARAM_MEMO = "memo"
private const val ARG_PARAM_URL = "url"
private const val ARG_PARAM_IMAGE_PATH = "imagePath"
private const val ARG_PARAM_CHECK_REQUIRED = "checkRequired"

class DetailFragment : BaseFragment() {
    private lateinit var englishCardId: String
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
                    putString(ARG_PARAM_ENGLISH_CARD_ID, englishCard.englishCardId)
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
            englishCardId = it.getString(ARG_PARAM_ENGLISH_CARD_ID).toString()
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
        checkRequiredValueRatingBar.setIsIndicator(true)

        if (!this.imagePath.isNullOrEmpty()) {
            val bitmap = EnglishCard.toBitmap(this.imagePath)
            englishWordValueImageView.setImageBitmap(bitmap)
        }

        if (this.url == null || !Url(this.url).isUrl()) {
            browseFloatingActionButton.hide()
        } else {
            browseFloatingActionButton.show()
            // Event
            browseFloatingActionButton.setOnClickListener {
                super.launchByUrl(this.url)
            }
        }

        goToEditButton.setOnClickListener {
            super.moveTo(EditActivity::class.java,
                EnglishCard.ID_FIELD,
                this.englishCardId)
        }
    }
}