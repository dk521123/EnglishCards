package com.dk.englishcards.pref

import android.os.Bundle
import android.text.Editable
import android.text.SpannableStringBuilder
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dk.englishcards.commons.BaseFragment
import com.dk.englishcards.R
import kotlinx.android.synthetic.main.fragment_pref_exam.*

class PrefExamFragment : BaseFragment() {
    companion object {
        @JvmStatic
        fun newInstance() = PrefExamFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_pref_exam, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        this.numverQuestionEditText.text =
            SpannableStringBuilder(super.preference.getMaxNumberQuestion().toString())
        if (super.preference.isEnglishQuestion()) {
            this.englishToMotherLanguageRadioButton.isChecked = true
        } else {
            this.motherLanguageToEnglishRadioButton.isChecked = true
        }

        numverQuestionEditText.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // Do nothing
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // Do nothing
            }
            override fun afterTextChanged(s: Editable?) {
                try {
                    val numberQuestion = numverQuestionEditText.text.toString().toIntOrNull()
                    if (numberQuestion != null) {
                        preference.saveMaxNumberQuestion(numberQuestion)
                    }
                } catch (ex: Exception) {
                    Log.e("PrefExamFragment",
                        "Error in afterTextChanged", ex)
                } finally {
                    Log.i("PrefExamFragment", "Done")
                }
            }
        })

        englishMotherLanguageRadioGroup.setOnCheckedChangeListener { _, _ ->
            val isEnglishQuestion = this.englishToMotherLanguageRadioButton.isChecked
            preference.saveIsEnglishQuestion(isEnglishQuestion)
        }
    }
}