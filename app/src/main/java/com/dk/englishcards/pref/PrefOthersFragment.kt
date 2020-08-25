package com.dk.englishcards.pref

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dk.englishcards.commons.BaseFragment
import com.dk.englishcards.R

class PrefOthersFragment : BaseFragment() {

    companion object {
        @JvmStatic
        fun newInstance() = PrefOthersFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_pref_others, container, false)
    }
}