package com.dk.englishcards.pref

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.dk.englishcards.commons.BaseFragment
import com.dk.englishcards.R
import com.dk.englishcards.pref.imports.grammers.ClearAllGrammarExamsCommand
import com.dk.englishcards.pref.imports.grammers.ImportToeicGrammarExamsCommand
import com.dk.englishcards.pref.imports.words.ClearAllWordsCommand
import com.dk.englishcards.pref.imports.words.ImportToeicWordsCommand
import kotlinx.android.synthetic.main.fragment_pref_import.*

class PrefImportFragment : BaseFragment() {

    companion object {
        @JvmStatic
        fun newInstance() = PrefImportFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_pref_import, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val clearToeicWordsCommand = ClearAllWordsCommand()
        val importToeicWordsCommand = ImportToeicWordsCommand(context!!.assets)
        clearAndImportEnglishWordToeicButton.setOnClickListener {
            val isSuccessful = clearToeicWordsCommand.execute() &&
                importToeicWordsCommand.execute()
            this.showDialog(isSuccessful)
            true
        }

        val clearToeicGrammarExamsCommand = ClearAllGrammarExamsCommand()
        val importToeicGrammarExamsCommand = ImportToeicGrammarExamsCommand(context!!.assets)
        clearAndImportToeicGrammarExamButton.setOnClickListener {
            val isSuccessful = clearToeicGrammarExamsCommand.execute() &&
                importToeicGrammarExamsCommand.execute()
            this.showDialog(isSuccessful)
            true
        }
    }

    private fun showDialog(isSuccessful: Boolean) {
        val message = if (isSuccessful) {
            "Import is successful..."
        } else {
            "Import is failed..."
        }
        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
    }
}