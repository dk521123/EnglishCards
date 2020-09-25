package com.dk.englishcards.edit

import android.view.*
import android.widget.ImageView
import android.widget.RadioButton
import androidx.recyclerview.widget.RecyclerView
import android.util.Log
import com.dk.englishcards.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.image_list.view.*

const val DEFAULT_SELECTED_POSITION = -1

class ImageListRecyclerViewAdapter(private val imageUrlList: List<String>) :
    RecyclerView.Adapter<ImageListRecyclerViewAdapter.ImageListViewHolder>() {

    private lateinit var listener: OnSelectImageListener
    private var selectedItem:Int = DEFAULT_SELECTED_POSITION

    inner class ImageListViewHolder(val view: View) : RecyclerView.ViewHolder(view)  {
        val image: ImageView = view.imageListImageView
        private val radioButton: RadioButton = view.imageListIRadioButton
        init {
            radioButton.setOnClickListener {
                Log.d("OnClick", "selectedItem = $selectedItem")
                Log.d("OnClick", "adapterPosition = $adapterPosition")
                if (selectedItem == adapterPosition) {
                    selectedItem = DEFAULT_SELECTED_POSITION
                    view.imageListIRadioButton.isChecked = false
                    listener.onSelectImageListener(null, null)
                } else {
                    selectedItem = adapterPosition
                    view.imageListIRadioButton.isChecked = true
                    listener.onSelectImageListener(view.imageListImageView, view.imageListIRadioButton)
                }
            }
        }
    }

    interface OnSelectImageListener{
        fun onSelectImageListener(selectedImageView: ImageView?, selectedRadioButton: RadioButton?)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageListViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val item = layoutInflater.inflate(R.layout.image_list, parent, false)
        return ImageListViewHolder(item)
    }

    override fun onBindViewHolder(holder: ImageListViewHolder, position: Int) {
        Picasso.get()
            .load(this.imageUrlList[position])
            .resize(300, 300)
            .centerCrop()
            .into(holder.view.imageListImageView)
    }

    override fun getItemCount(): Int {
        return this.imageUrlList.size
    }

    fun setOnSelectImageListener(listener: OnSelectImageListener){
        this.listener = listener
    }
}