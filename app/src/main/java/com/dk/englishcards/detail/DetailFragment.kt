package com.dk.englishcards.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dk.englishcards.R
import com.dk.englishcards.cards.EnglishCard
import kotlinx.android.synthetic.main.fragment_detail.*

private const val ARG_PARAM_ENGLISH_CARD = "englishCard"

class DetailFragment : Fragment() {
    private var englishCard: EnglishCard? = null

    companion object {
        fun newInstance(englishCard: EnglishCard) =
            DetailFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(ARG_PARAM_ENGLISH_CARD, englishCard)
                }
            }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            englishCard = it.getSerializable(ARG_PARAM_ENGLISH_CARD) as EnglishCard?
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

        englishValueTextView.text = this.englishCard?.english
        motherLanguageValueTextView.text = this.englishCard?.motherLanguage
        memoValueTextView.text = this.englishCard?.memo
        checkRequiredValueRatingBar.rating = this.englishCard?.checkRequired!!
        if (!this.englishCard?.imagePath.isNullOrEmpty()) {
            val bitmap = this.englishCard?.toImageBitmap()
            englishWordValueImageView.setImageBitmap(bitmap)
        }
        browseFloatingActionButton.setOnClickListener {
            //TODO
        }
    }
}