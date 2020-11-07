package com.dk.englishcards.detail

import android.os.Bundle
import androidx.viewpager2.widget.ViewPager2
import com.dk.englishcards.R
import com.dk.englishcards.commons.BaseSubPageActivity
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : BaseSubPageActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val englishCards = super.dbHandler.readAll()
        detailViewPager.adapter = DetailAdapter(this, englishCards)
        detailViewPager.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        TabLayoutMediator(detailIndicator, detailViewPager) { _, _ -> }.attach()
    }
}