package com.dk.englishcards.main

import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dk.englishcards.R
import com.dk.englishcards.cards.EnglishCard
import com.dk.englishcards.cards.EnglishCardDbHandler
import kotlinx.android.synthetic.main.main_list_item.view.*
import java.io.File

class MainListRecyclerViewAdapter(
    private val targetList: MutableList<EnglishCard>) :
    RecyclerView.Adapter<MainListRecyclerViewAdapter.MainListViewHolder>() {
    private val dbHandler = EnglishCardDbHandler()
    private lateinit var listener: OnItemClickListener

    class MainListViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val image: ImageView = view.itemImageView
        val title: TextView = view.titleTextView
        val subtitle: TextView  = view.subtitleTextView
    }
    interface OnItemClickListener{
        fun onItemClickListener(
            view: View,
            position: Int,
            targetItem: EnglishCard)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainListViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val item = layoutInflater.inflate(R.layout.main_list_item, parent, false)
        return MainListViewHolder(item)
    }

    override fun getItemCount(): Int {
        return targetList.size
    }

    override fun onBindViewHolder(holder: MainListViewHolder, position: Int) {
        val englishCard = targetList[position]

        if (englishCard.imagePath.isNullOrEmpty()) {
            holder.view.itemImageView.setImageResource(
                R.mipmap.ic_launcher_round
            )
        } else {
            val imageFile = File(englishCard.imagePath)
            val bitmap = BitmapFactory.decodeFile(imageFile.absolutePath)
            holder.view.itemImageView.setImageBitmap(bitmap)
        }

        holder.view.titleTextView.text = englishCard.english
        holder.view.subtitleTextView.text = englishCard.motherLanguage
        // For Click event
        holder.view.setOnClickListener {
            listener.onItemClickListener(it, position, targetList[position])
        }
    }

    fun setOnItemClickListener(listener: OnItemClickListener){
        this.listener = listener
    }

    fun removeItem(removingIndex: Int) {
        if (targetList.size <= 0) {
            return
        }

        val targetEnglishCard = targetList[removingIndex]
        dbHandler.delete(targetEnglishCard.englishCardId)

        this.targetList.removeAt(removingIndex)
        this.notifyItemRemoved(removingIndex)
    }
}