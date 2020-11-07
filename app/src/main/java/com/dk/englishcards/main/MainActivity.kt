package com.dk.englishcards.main

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dk.englishcards.R
import com.dk.englishcards.cards.EnglishCard
import com.dk.englishcards.commons.BaseActivity
import com.dk.englishcards.detail.DetailActivity
import com.dk.englishcards.edit.EditActivity
import com.dk.englishcards.exam.grammars.GrammarExam
import com.dk.englishcards.exam.grammars.GrammarExamActivity
import com.dk.englishcards.exam.words.EnglishWordsExamActivity
import com.dk.englishcards.pref.PreferenceActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        addFloatingActionButton.setOnClickListener {
            super.moveToEdit()
        }
    }

    // For menu
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        super.onCreateOptionsMenu(menu)

        val inflater = menuInflater
        inflater.inflate(R.menu.menu_for_home, menu)
        return true
    }

    // Click events For menu
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.preferenceItem -> {
                this.moveTo(PreferenceActivity::class.java)
                true
            }
            R.id.englishWordsExamItem -> {
                this.moveTo(EnglishWordsExamActivity::class.java)
                true
            }
            R.id.grammarExamItem -> {
                this.moveTo(GrammarExamActivity::class.java)
                true
            }
            R.id.detailItem -> {
                this.moveTo(DetailActivity::class.java)
                true
            }
            R.id.addItem -> {
                this.moveToEdit()
                true
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        this.showUpdatedView()
    }

    private fun showUpdatedView() {
        val englishCards = super.dbHandler.readAll()
        val adapter = MainListRecyclerViewAdapter(englishCards.toMutableList())
        mainRecyclerView.layoutManager = LinearLayoutManager(this)
        mainRecyclerView.adapter = adapter
        mainRecyclerView.setHasFixedSize(true)
        adapter.setOnItemClickListener(
            object: MainListRecyclerViewAdapter.OnItemClickListener {
                override fun onItemClickListener(
                    view: View,
                    position: Int,
                    targerItem: EnglishCard) {
                    moveTo(
                        EditActivity::class.java,
                        EnglishCard.ID_FIELD,
                        targerItem.englishCardId)
                }
            })

        val helper = ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(
            0, (ItemTouchHelper.RIGHT or ItemTouchHelper.LEFT)
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                adapter.removeItem(viewHolder.adapterPosition)
            }

            override fun onChildDraw(
                canvas: Canvas,
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                dx: Float,
                dy: Float,
                actionState: Int,
                isCurrentlyActive: Boolean
            ) {
                super.onChildDraw(
                    canvas,
                    recyclerView,
                    viewHolder,
                    dx,
                    dy,
                    actionState,
                    isCurrentlyActive
                )
                val isFromLeftDirection = dx < 0
                val itemView = viewHolder.itemView

                val background = ColorDrawable(Color.RED)
                if (isFromLeftDirection) {
                    background.setBounds(
                        itemView.right + dx.toInt(),
                        itemView.top,
                        itemView.right,
                        itemView.bottom
                    )
                } else {
                    background.setBounds(
                        itemView.left,
                        itemView.top,
                        itemView.left + dx.toInt(),
                        itemView.bottom
                    )
                }
                background.draw(canvas)

                val deleteIcon = AppCompatResources.getDrawable(
                    this@MainActivity,
                    R.drawable.ic_baseline_delete_forever_24
                )
                val iconMarginVertical =
                    (viewHolder.itemView.height - deleteIcon!!.intrinsicHeight) / 2
                val itemHeight = itemView.bottom - itemView.top
                val deleteIconIntrinsicWidth = deleteIcon?.intrinsicWidth
                val deleteIconIntrinsicHeight = deleteIcon?.intrinsicHeight
                if (isFromLeftDirection) {
                    val deleteIconTop =
                        itemView.top + (itemHeight - deleteIconIntrinsicHeight) / 2
                    val deleteIconMargin =
                        (itemHeight - deleteIconIntrinsicHeight) / 2
                    deleteIcon.setBounds(
                        itemView.right - deleteIconMargin - deleteIconIntrinsicWidth,
                        deleteIconTop,
                        itemView.right - deleteIconMargin,
                        deleteIconTop + deleteIconIntrinsicHeight)
                } else {
                    deleteIcon.setBounds(
                        itemView.left + iconMarginVertical,
                        itemView.top + iconMarginVertical,
                        itemView.left + iconMarginVertical + deleteIcon.intrinsicWidth,
                        itemView.bottom - iconMarginVertical
                    )
                }
                deleteIcon.draw(canvas)
            }
        })
        helper.attachToRecyclerView(mainRecyclerView)
    }
}