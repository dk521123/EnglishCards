package com.dk.englishcards

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dk.englishcards.cards.EnglishCard
import kotlinx.android.synthetic.main.main_list_item.view.*

class MainListRecyclerViewAdapter(
    private val targetList: Array<EnglishCard>) :
    RecyclerView.Adapter<MainListRecyclerViewAdapter.MainListViewHolder>() {

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
        holder.view.itemImageView.setImageResource(
            R.mipmap.ic_launcher_round
        )
        holder.view.titleTextView.text = targetList[position].english
        holder.view.subtitleTextView.text = targetList[position].motherLanguage
        // For Click event
        holder.view.setOnClickListener {
            listener.onItemClickListener(it, position, targetList[position])
        }
    }

    fun setOnItemClickListener(listener: OnItemClickListener){
        this.listener = listener
    }
}