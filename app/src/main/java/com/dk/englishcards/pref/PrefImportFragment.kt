package com.dk.englishcards.pref

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.dk.englishcards.commons.BaseFragment
import com.dk.englishcards.R
import com.dk.englishcards.importwords.ClearAllWordsCommand
import com.dk.englishcards.importwords.ImportToeicWordsCommand
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

        val clearCommand = ClearAllWordsCommand()
        val importToeicCommand = ImportToeicWordsCommand(context!!.assets)

        clearAndImportToeicButton.setOnClickListener {
            val message = if (clearCommand.execute() &&
                importToeicCommand.execute()) {
                "Import is successful..."
            } else {
                "Import is failed..."
            }
            Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
            true
        }
    }
}