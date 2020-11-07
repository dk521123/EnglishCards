package com.dk.englishcards.exam.words

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.dk.englishcards.commons.BaseFragment
import com.dk.englishcards.R
import kotlinx.android.synthetic.main.fragment_english_words_exam.*

private const val ARG_PARAM_POSITION = "position"
private const val ARG_PARAM_LABEL = "label"
private const val ARG_PARAM_ID = "englishCardId"

class EnglishWordsExamFragment : BaseFragment() {
    private var position: Int? = null
    private var label: String? = null
    private var englishCardId: String? = null

    companion object {
        fun newInstance(position: Int, label: String, englishCardId: String) =
            EnglishWordsExamFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_PARAM_POSITION, position)
                    putString(ARG_PARAM_LABEL, label)
                    putString(ARG_PARAM_ID, englishCardId)
                }
            }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            position = it.getInt(ARG_PARAM_POSITION)
            label = it.getString(ARG_PARAM_LABEL)
            englishCardId = it.getString(ARG_PARAM_ID)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_english_words_exam, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val textView = view.findViewById<TextView>(
            R.id.englishWordExamTextView)

        val no = (position!! / 2) + 1
        val qOrA = if ((position!! % 2) == 0) "Q" else "A"
        if (((position!! % 2) == 0)) {
            // Question
            textView.text = "Q-$no.\n\n $label"
            changeCheckRequiredTextView.visibility = View.INVISIBLE
            changeCheckRequiredRatingBar.visibility = View.INVISIBLE
        } else {
            // Answer
            textView.text = "A-$no.\n\n $label"
            changeCheckRequiredTextView.visibility = View.VISIBLE
            changeCheckRequiredRatingBar.visibility = View.VISIBLE

            val englishCard = englishCardId?.let {
                super.englishCardDbHandler.readById(it)
            }
            if (englishCard != null) {
                changeCheckRequiredRatingBar.rating = englishCard.checkRequired
                changeCheckRequiredRatingBar.setOnRatingBarChangeListener { _, rating, _ ->
                    super.englishCardDbHandler.update(
                        englishCard.englishCardId,
                        englishCard?.english.toString(),
                        englishCard?.motherLanguage.toString(),
                        englishCard?.memo.toString(),
                        englishCard?.url.toString(),
                        rating
                    )
                }
            }
        }
    }
}