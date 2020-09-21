package com.dk.englishcards.edit

import android.view.*
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.dk.englishcards.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.image_list.view.*

class ImageListRecyclerViewAdapter(private val imageUrlList: List<String>) :
    RecyclerView.Adapter<ImageListRecyclerViewAdapter.ImageListViewHolder>() {
    class ImageListViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val image: ImageView = view.imageListImageView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageListViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val item = layoutInflater.inflate(R.layout.image_list, parent, false)
        return ImageListViewHolder(item)
    }

    override fun onBindViewHolder(holder: ImageListViewHolder, position: Int) {
        val url = this.imageUrlList[position]

        Picasso.get()
            .load(url)
            .resize(300, 300)
            .centerCrop()
            .into(holder.view.imageListImageView)
    }

    override fun getItemCount(): Int {
        return this.imageUrlList.size
    }
}