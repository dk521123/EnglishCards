package com.dk.englishcards.exam

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.dk.englishcards.commons.BaseFragment
import com.dk.englishcards.R

private const val ARG_PARAM_POSITION = "position"
private const val ARG_PARAM_LABEL = "label"

class ExamFragment : BaseFragment() {
    private var position: Int? = null
    private var label: String? = null

    companion object {
        fun newInstance(position: Int, label: String) =
            ExamFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_PARAM_POSITION, position)
                    putString(ARG_PARAM_LABEL, label)
                }
            }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            position = it.getInt(ARG_PARAM_POSITION)
            label = it.getString(ARG_PARAM_LABEL)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_exam, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val textView = view.findViewById<TextView>(
            R.id.examTextView)
        val qOrA = if ((position!! % 2) == 0) "Q" else "A"
        val no = (position!! / 2)
        textView.text = "$qOrA-$no. $label"
    }
}