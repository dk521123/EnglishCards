package com.dk.englishcards.exam.grammars

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dk.englishcards.R
import kotlinx.android.synthetic.main.fragment_grammar_exam_question.*

private const val ARG_NO = "no"
private const val ARG_QUESTION = "question"
private const val ARG_CANDIDATE_A = "candidateA"
private const val ARG_CANDIDATE_B = "candidateB"
private const val ARG_CANDIDATE_C = "candidateC"
private const val ARG_CANDIDATE_D = "candidateD"
private const val ARG_ANSWER = "answer"

/**
 * A simple [Fragment] subclass.
 * Use the [GrammarExamQuestionFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class GrammarExamQuestionFragment : Fragment() {
    private var no: Int = 1
    private var question: String? = ""
    private var candidateA: String? = ""
    private var candidateB: String? = ""
    private var candidateC: String? = ""
    private var candidateD: String? = ""
    private var answer: String? = ""
    private var listener: OnSelectedAnswer? = null

    interface OnSelectedAnswer {
        fun onSelectedAnswer(isCorrectAnswer :Boolean)
    }

    companion object {
        @JvmStatic
        fun newInstance(
            no: Int,
            question: String,
            candidateA: String,
            candidateB: String,
            candidateC: String,
            candidateD: String,
            answer: String) =
            GrammarExamQuestionFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_NO, no)
                    putString(ARG_QUESTION, question)
                    putString(ARG_CANDIDATE_A, candidateA)
                    putString(ARG_CANDIDATE_B, candidateB)
                    putString(ARG_CANDIDATE_C, candidateC)
                    putString(ARG_CANDIDATE_D, candidateD)
                    putString(ARG_ANSWER, answer)
                }
            }
    }

    fun setSelectedAnswerListener(listener: OnSelectedAnswer) {
        this.listener = listener
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments.let {
            no = it!!.getInt(ARG_NO)
            question = it.getString(ARG_QUESTION)
            candidateA = it.getString(ARG_CANDIDATE_A)
            candidateB = it.getString(ARG_CANDIDATE_B)
            candidateC = it.getString(ARG_CANDIDATE_C)
            candidateD = it.getString(ARG_CANDIDATE_D)
            answer = it.getString(ARG_ANSWER)
        }
        Log.d("Grammar", "${this.no}, ${this.question}")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_grammar_exam_question, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        grammarQuestionTextView.text = "Q${this.no}. ${this.question}"
        candidateTextView.text =
            "(A) ${this.candidateA}\n" +
                    "(B) ${this.candidateB}\n" +
                    "(C) ${this.candidateC}\n" +
                    "(D) ${this.candidateD}"

        selectAButton.setOnClickListener {
            val isCorrectAnswer = this.isCorrectAnswer(this.answer, "A")
            this.listener?.onSelectedAnswer(isCorrectAnswer)
        }
        selectBButton.setOnClickListener {
            val isCorrectAnswer = this.isCorrectAnswer(this.answer, "B")
            this.listener?.onSelectedAnswer(isCorrectAnswer)
        }
        selectCButton.setOnClickListener {
            val isCorrectAnswer = this.isCorrectAnswer(this.answer, "C")
            this.listener?.onSelectedAnswer(isCorrectAnswer)
        }
        selectDButton.setOnClickListener {
            val isCorrectAnswer = this.isCorrectAnswer(this.answer, "D")
            this.listener?.onSelectedAnswer(isCorrectAnswer)
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    private fun isCorrectAnswer(answer: String?, selectedAnswer: String): Boolean {
        return answer!!.toUpperCase() == selectedAnswer
    }
}