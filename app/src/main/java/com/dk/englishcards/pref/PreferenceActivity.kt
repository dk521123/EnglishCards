package com.dk.englishcards.pref

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.dk.englishcards.commons.BaseSubPageActivity
import com.dk.englishcards.R
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.activity_preference.*

class PreferenceActivity : BaseSubPageActivity() {
    private val indexTabItems = listOf(
        IndexItem.ExamItem,
        IndexItem.OthersItem)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_preference)

        prefViewPager.adapter = object : FragmentStateAdapter(this) {
            override fun getItemCount(): Int = indexTabItems.size
            override fun createFragment(position: Int): Fragment {
                return indexTabItems[position].newInstance()
            }
        }
        TabLayoutMediator(prefTabLayout, prefViewPager) { tab, position ->
            tab.text = when (position) {
                0 -> "Exam"
                else -> "Others"
            }
        }.attach()
    }

    private sealed class IndexItem {
        abstract fun newInstance(): Fragment

        object ExamItem : IndexItem() {
            override fun newInstance() =
                PrefExamFragment.newInstance()
        }
        object OthersItem : IndexItem() {
            override fun newInstance() =
                PrefOthersFragment.newInstance()
        }
    }
}