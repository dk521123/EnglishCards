package com.dk.englishcards.exam.grammars

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dk.englishcards.R
import kotlinx.android.synthetic.main.fragment_grammar_exam_answer.*

private const val ARG_NO = "no"
private const val ARG_IS_COLLECT_ANSWER = "isCollectAnswer"
private const val ARG_ANSWER = "answer"
private const val ARG_QUESTION = "question"
private const val ARG_QUESTION_TRANSLATIONS = "questionTranslation"
private const val ARG_COMMENTARY = "commentary"

class GrammarExamAnswerFragment : Fragment() {
    private var no: Int = 1
    private var isCollectAnswer: Boolean = false
    private var answer: String? = ""
    private var question: String? = ""
    private var questionTranslation: String? = ""
    private var commentary: String? = ""
    private var listener: OnClickNextButton? = null

    interface OnClickNextButton {
        fun onClickNextButton()
    }

    companion object {
        @JvmStatic
        fun newInstance(
            no: Int,
            isCollectAnswer: Boolean,
            answer: String,
            question: String,
            questionTranslation: String,
            commentary: String
        ) =
            GrammarExamAnswerFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_NO, no)
                    putBoolean(ARG_IS_COLLECT_ANSWER, isCollectAnswer)
                    putString(ARG_ANSWER, answer)
                    putString(ARG_QUESTION, question)
                    putString(ARG_QUESTION_TRANSLATIONS, questionTranslation)
                    putString(ARG_COMMENTARY, commentary)
                }
            }
    }

    fun setClickNextButtonListener(listener: OnClickNextButton) {
        this.listener = listener
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            no = it.getInt(ARG_NO)
            isCollectAnswer = it.getBoolean(ARG_IS_COLLECT_ANSWER)
            answer = it.getString(ARG_ANSWER)
            question = it.getString(ARG_QUESTION)
            questionTranslation = it.getString(ARG_QUESTION_TRANSLATIONS)
            commentary = it.getString(ARG_COMMENTARY)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_grammar_exam_answer, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        correctOrNoTextView.text =
            "Q${this.no} " + if (this.isCollectAnswer) "Collect!!!" else "Incorrect..."
        questionTextView.text = this.question.toString().replace(
            "-------", "( " + this.answer.toString() + " )")
        questionTranslationTextView.text = this.questionTranslation
        commentaryTextView.text = this.commentary

        nextQuestionButton.setOnClickListener {
            this.listener?.onClickNextButton()
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }
}