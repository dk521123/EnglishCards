package com.dk.englishcards.exam

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.viewpager2.widget.ViewPager2
import com.dk.englishcards.BaseActivity
import com.dk.englishcards.R
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.activity_exam.*

class ExamActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exam)

        val exams = arrayOf(
            Exam("streamline", "(仕事を)合理化する"),
            Exam("attribute", "…に原因があると考える"),
            Exam("mandatory", "義務的な"),
            Exam("apprenticeship", "見習い期間"),
            Exam("applause", "拍手／賞賛"),
            Exam("overwhelming", "圧倒的な")
        )
        examViewPager.adapter = ExamAdapter(this, exams)
        examViewPager.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        TabLayoutMediator(examIndicator, examViewPager) { _, _ -> }.attach()
    }

    // For menu
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        super.onCreateOptionsMenu(menu)

        val inflater = menuInflater
        inflater.inflate(R.menu.menu_for_sub, menu)
        return true
    }

    // Click events For menu
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.homeItem -> {
                super.moveToMain()
                true
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }
}