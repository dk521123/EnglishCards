package com.dk.englishcards.exam.grammars

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.dk.englishcards.R
import com.dk.englishcards.commons.BaseSubPageActivity

class GrammarExamActivity :
    BaseSubPageActivity(),
    GrammarExamQuestionFragment.OnSelectedAnswer,
    GrammarExamAnswerFragment.OnClickNextButton {

    private var questionNo: Int = 1
    private lateinit var grammarExams: List<GrammarExam>
    private lateinit var grammarExamDbHandler: GrammarExamDbHandler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_grammar_exam)

        this.grammarExamDbHandler = GrammarExamDbHandler()
        this.grammarExams = this.grammarExamDbHandler.readAll().toList().shuffled()
        this.showQuestion()
    }

    override fun onSelectedAnswer(isCorrectAnswer: Boolean) {
        Log.d("Grammar", "isCorrectAnswer = $isCorrectAnswer")
        val grammarExam = this.getTargetGrammarExam()
        val collectAnswer = when (grammarExam.answer) {
            "A" -> grammarExam.candidateA
            "B" -> grammarExam.candidateB
            "C" -> grammarExam.candidateC
            "D" -> grammarExam.candidateD
            else -> ""
        }
        val grammarExamAnswerFragment =
            GrammarExamAnswerFragment.newInstance(
                this.questionNo,
                isCorrectAnswer,
                collectAnswer,
                grammarExam.question,
                grammarExam.questionTranslation,
                grammarExam.commentary)
        grammarExamAnswerFragment.setClickNextButtonListener(this)
        this.replaceFragment(grammarExamAnswerFragment)
    }

    override fun onClickNextButton() {
        Log.d("Grammar", "Called onClickNextButton")
        if (this.grammarExams.size <= this.questionNo) {
            Toast.makeText(this, "Try again...", Toast.LENGTH_SHORT).show()
            this.questionNo = 1
            this.grammarExams = this.grammarExams.shuffled()
        } else {
            this.questionNo++
        }
        this.showQuestion()
    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.grammarsExamFrameLayout, fragment)
        fragmentTransaction.commit()
    }

    private fun getTargetGrammarExam(): GrammarExam {
        val index = this.questionNo - 1
        return this.grammarExams[index]
    }

    private fun showQuestion() {
        val grammarExam = this.getTargetGrammarExam()
        val grammarExamQuestionFragment =
            GrammarExamQuestionFragment.newInstance(
                this.questionNo,
                grammarExam.question,
                grammarExam.candidateA,
                grammarExam.candidateB,
                grammarExam.candidateC,
                grammarExam.candidateD,
                grammarExam.answer)
        grammarExamQuestionFragment.setSelectedAnswerListener(this)
        this.replaceFragment(grammarExamQuestionFragment)
    }
}